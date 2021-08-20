package com.sistempakarkulitwajah.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.request.JsonObjectRequest;
import com.sistempakarkulitwajah.MySingleton;
import com.sistempakarkulitwajah.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AturanEditActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static final String url = "https://sistempakarkulitwajah77.000webhostapp.com/get_daftar_gejala.php";
    private static final String url_update = "https://sistempakarkulitwajah77.000webhostapp.com/update_aturan.php";
    private MyCustomAdapter dataAdapter = null;
    private ArrayList<Gejala> gejalaList = new ArrayList<Gejala>();
    private Gejala gejala;
    private String id_penyakit;
    private String nama_penyakit = "";
    private StringBuffer responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aturan_edit);
        setTitle("Atur Ulang Gejala");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_penyakit = extras.getString("id_penyakit");
            nama_penyakit = extras.getString("nama_penyakit");
        }

        TextView tv_nama_penyakit = findViewById(R.id.tv_nama_penyakit);
        tv_nama_penyakit.setText(nama_penyakit);

        getDaftarGejala();

        Button btn_simpan = findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(v -> {
            responseText = new StringBuffer();
            if (!gejalaList.isEmpty()) {
                ArrayList<Gejala> gejalaList2 = dataAdapter.gejalaList;
                for (int i = 0; i < gejalaList2.size(); i++) {
                    Gejala gejala = gejalaList2.get(i);
                    if (gejala.isSelected()) {
                        responseText.append(gejala.getName()).append("#");
                    }
                }
            }

            if (responseText.toString().equals("")) {
                Toast.makeText(AturanEditActivity.this, "Pilih dulu gejala !", Toast.LENGTH_SHORT).show();
            } else {
                updateAturan();
            }
        });
    }

    private void getDaftarGejala() {
        displayLoader();
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, response -> {
                    pDialog.dismiss();
                    try {
                        if (response.getInt("status") == 0) {
                            gejalaList = new ArrayList<Gejala>();
                            JSONArray jsonArray = response.getJSONArray("gejala");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String name = jsonObject.getString("nama_gejala");
                                gejala = new Gejala(name, false);
                                gejalaList.add(gejala);
                            }
                            dataAdapter = new MyCustomAdapter(AturanEditActivity.this, R.layout.gejala_list_ceklis, gejalaList);
                            ListView listView = findViewById(R.id.lv_gejala);
                            listView.setAdapter(dataAdapter);
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

    private void displayLoader() {
        pDialog = new ProgressDialog(AturanEditActivity.this);
        pDialog.setMessage("Sedang diproses...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void updateAturan() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            request.put("id_penyakit", id_penyakit);
            request.put("daftar_gejala", responseText.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url_update, request, response -> {
                    pDialog.dismiss();
                    try {
                        if (response.getInt("status") == 0) {
                            Toast.makeText(getApplicationContext(),
                                    response.getString("message"), Toast.LENGTH_SHORT).show();
                            finish();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return false;
    }

    private class MyCustomAdapter extends ArrayAdapter<Gejala> {

        private ArrayList<Gejala> gejalaList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Gejala> gejalaList) {
            super(context, textViewResourceId, gejalaList);
            this.gejalaList = new ArrayList<Gejala>();
            this.gejalaList.addAll(gejalaList);
        }

        private class ViewHolder {
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.gejala_list_ceklis, null);

                holder = new ViewHolder();
                holder.name = convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener(v -> {
                    CheckBox cb = (CheckBox) v;
                    Gejala gejala = (Gejala) cb.getTag();
                    gejala.setSelected(cb.isChecked());
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Gejala gejala = gejalaList.get(position);
            holder.name.setText(gejala.getName());
            holder.name.setChecked(gejala.isSelected());
            holder.name.setTag(gejala);

            return convertView;
        }
    }
}