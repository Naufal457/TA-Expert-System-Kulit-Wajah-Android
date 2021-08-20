package com.sistempakarkulitwajah.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.request.JsonObjectRequest;
import com.sistempakarkulitwajah.MySingleton;
import com.sistempakarkulitwajah.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PenyakitTambahActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static final String url = "https://sistempakarkulitwajah77.000webhostapp.com/tambah_penyakit.php";
    private EditText et_kode_penyakit;
    private EditText et_nama_penyakit;
    private EditText et_deskripsi;
    private EditText et_solusi;
    private String kode_penyakit;
    private String nama_penyakit;
    private String deskripsi;
    private String solusi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyakit_tambah);
        setTitle("Tambah Penyakit");

        et_kode_penyakit = findViewById(R.id.et_kode_penyakit);
        et_nama_penyakit = findViewById(R.id.et_nama_penyakit);
        et_deskripsi = findViewById(R.id.et_deskripsi);
        et_solusi = findViewById(R.id.et_solusi);

        Button btn_simpan = findViewById(R.id.btn_simpan);

        btn_simpan.setOnClickListener(view -> {
            kode_penyakit = et_kode_penyakit.getText().toString().trim();
            nama_penyakit = et_nama_penyakit.getText().toString().trim();
            deskripsi = et_deskripsi.getText().toString();
            solusi = et_solusi.getText().toString();
            if (validateInputs()) {
                tambahPenyakit();
            }
        });
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(PenyakitTambahActivity.this);
        pDialog.setMessage("Sedang diproses...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void tambahPenyakit() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            request.put("kode_penyakit", kode_penyakit);
            request.put("nama_penyakit", nama_penyakit);
            request.put("deskripsi", deskripsi);
            request.put("solusi", solusi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url, request, response -> {
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

    private boolean validateInputs() {
        if (kode_penyakit.equals("")) {
            et_kode_penyakit.setError("Kode Penyakit tidak boleh kosong");
            et_kode_penyakit.requestFocus();
            return false;
        }
        if (nama_penyakit.equals("")) {
            et_nama_penyakit.setError("Nama Penyakit tidak boleh kosong");
            et_nama_penyakit.requestFocus();
            return false;
        }
        if (deskripsi.equals("")) {
            et_deskripsi.setError("Deskripsi tidak boleh kosong");
            et_deskripsi.requestFocus();
            return false;
        }
        if (solusi.equals("")) {
            et_solusi.setError("Solusi tidak boleh kosong");
            et_solusi.requestFocus();
            return false;
        }
        return true;
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