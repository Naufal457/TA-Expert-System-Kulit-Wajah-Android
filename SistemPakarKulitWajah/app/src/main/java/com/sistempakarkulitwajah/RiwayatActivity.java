package com.sistempakarkulitwajah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.request.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RiwayatActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static final String url = "https://sistempakarkulitwajah77.000webhostapp.com/get_daftar_riwayat.php";
    private static final String url_delete = "https://sistempakarkulitwajah77.000webhostapp.com/hapus_daftar_riwayat.php";
    private ListView lv;
    private TextView tv_tidak_ada;
    private SimpleAdapter adapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);
        setTitle("Riwayat Diagnosa");

        SessionHandler session = new SessionHandler(this);
        user = session.getUserDetails();

        lv = findViewById(R.id.list_riwayat);
        tv_tidak_ada = findViewById(R.id.tv_tidak_ada);

        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            new AlertDialog.Builder(RiwayatActivity.this)
                    .setTitle("Konfirmasi")
                    .setMessage("Anda yakin akan menghapus semua data riwayat ?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Ya, Hapus", (dialog, whichButton) -> {
                        hapusRiwayat();
                        finish();
                    })
                    .setNegativeButton("Tidak", null).show();
            return true;
        }

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return false;
    }

    private void hapusRiwayat() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            request.put("id_pengguna", user.getIdPengguna());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url_delete, request, response -> {
                    pDialog.dismiss();
                    try {
                        Toast.makeText(getApplicationContext(),
                                response.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    pDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(RiwayatActivity.this);
        pDialog.setMessage("Sedang diproses...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void getData() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            request.put("id_pengguna", user.getIdPengguna());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url, request, response -> {
                    pDialog.dismiss();
                    try {
                        if (response.getInt("status") == 0) {
                            JSONArray jsonArray = response.getJSONArray("riwayat");
                            ArrayList<HashMap<String, String>> list = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                String nama_penyakit = "";
                                if (jsonObject.getString("nilai").equals("null")) {
                                    nama_penyakit = jsonObject.getString("nama_penyakit");
                                } else {
                                    nama_penyakit = jsonObject.getString("nama_penyakit") +
                                            " (" + jsonObject.getString("nilai") + "%)";
                                }
                                map.put("nama_penyakit", nama_penyakit);
                                map.put("tanggal", jsonObject.getString("tanggal"));
                                map.put("metode", jsonObject.getString("metode"));
                                map.put("id_penyakit", jsonObject.getString("id_penyakit"));
                                list.add(map);
                            }
                            adapter = new SimpleAdapter(
                                    RiwayatActivity.this,
                                    list,
                                    R.layout.riwayat_list,
                                    new String[]{"nama_penyakit", "tanggal", "id_penyakit", "metode"},
                                    new int[]{R.id.tv_hasil, R.id.tv_tanggal, R.id.id_penyakit, R.id.tv_metode});
                            lv.setAdapter(adapter);

                            AdapterView.OnItemClickListener itemClickListener = (parent, container, position, id) -> {
                                RelativeLayout relativeLayout = (RelativeLayout) container;
                                TextView tv_id = (TextView) relativeLayout.getChildAt(0);
                                if (!tv_id.getText().toString().equals("")) {
                                    Intent intent = new Intent(RiwayatActivity.this, PenyakitDetailActivity.class);
                                    intent.putExtra("id_penyakit", tv_id.getText().toString());
                                    startActivity(intent);
                                }
                            };

                            lv.setOnItemClickListener(itemClickListener);

                            adapter.notifyDataSetChanged();
                        } else {
                            lv.setVisibility(View.GONE);
                            tv_tidak_ada.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    pDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }
}