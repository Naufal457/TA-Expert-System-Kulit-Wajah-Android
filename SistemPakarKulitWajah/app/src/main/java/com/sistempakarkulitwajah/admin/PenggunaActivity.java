package com.sistempakarkulitwajah.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.request.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sistempakarkulitwajah.MySingleton;
import com.sistempakarkulitwajah.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PenggunaActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static final String url = "https://sistempakarkulitwajah77.000webhostapp.com/get_daftar_pengguna.php";
    private ListView lv;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengguna_admin);
        setTitle("Data Pengguna");

        lv = findViewById(R.id.list_pengguna);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(PenggunaActivity.this, PenggunaTambahActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(PenggunaActivity.this);
        pDialog.setMessage("Sedang diproses...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void getData() {
        displayLoader();
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, response -> {
                    pDialog.dismiss();
                    try {
                        if (response.getInt("status") == 0) {
                            JSONArray jsonArray = response.getJSONArray("pengguna");
                            ArrayList<HashMap<String, String>> list = new ArrayList<>();
                            boolean kosong = true;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("id_pengguna", jsonObject.getString("id_pengguna"));
                                map.put("nama_lengkap", jsonObject.getString("nama_lengkap"));
                                list.add(map);
                                kosong = false;
                            }
                            adapter = new SimpleAdapter(
                                    PenggunaActivity.this,
                                    list,
                                    R.layout.pengguna_list,
                                    new String[]{"id_pengguna", "nama_lengkap"},
                                    new int[]{R.id.id_pengguna, R.id.nama_lengkap});
                            lv.setAdapter(adapter);

                            AdapterView.OnItemClickListener itemClickListener = (parent, container, position, id) -> {
                                LinearLayout linearLayout = (LinearLayout) container;
                                TextView tv_id = (TextView) linearLayout.getChildAt(0);
                                Intent intent = new Intent(PenggunaActivity.this, PenggunaEditActivity.class);
                                intent.putExtra("id_pengguna", tv_id.getText().toString());
                                startActivity(intent);
                            };

                            lv.setOnItemClickListener(itemClickListener);

                            if (kosong) {
                                Toast.makeText(PenggunaActivity.this, "Tidak ada data pengguna",
                                        Toast.LENGTH_SHORT).show();
                            }

                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    response.getString("message"), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}