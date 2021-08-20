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

public class GejalaTambahActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static final String url = "https://sistempakarkulitwajah77.000webhostapp.com/tambah_gejala.php";
    private EditText et_kode_gejala;
    private EditText et_nama_gejala;
    private EditText et_nilai_cf;
    private String kode_gejala;
    private String nama_gejala;
    private String nilai_cf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gejala_tambah);
        setTitle("Tambah Gejala");

        et_kode_gejala = findViewById(R.id.et_kode_gejala);
        et_nama_gejala = findViewById(R.id.et_nama_gejala);
        et_nilai_cf = findViewById(R.id.et_nilai_cf);

        Button btn_simpan = findViewById(R.id.btn_simpan);

        btn_simpan.setOnClickListener(view -> {
            kode_gejala = et_kode_gejala.getText().toString().trim();
            nama_gejala = et_nama_gejala.getText().toString().trim();
            nilai_cf = et_nilai_cf.getText().toString();
            if (validateInputs()) {
                tambahGejala();
            }
        });
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(GejalaTambahActivity.this);
        pDialog.setMessage("Sedang diproses...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void tambahGejala() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            request.put("kode_gejala", kode_gejala);
            request.put("nama_gejala", nama_gejala);
            request.put("nilai_cf", nilai_cf);
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
        if (kode_gejala.equals("")) {
            et_kode_gejala.setError("Kode Gejala tidak boleh kosong");
            et_kode_gejala.requestFocus();
            return false;
        }
        if (nama_gejala.equals("")) {
            et_nama_gejala.setError("Nama Gejala tidak boleh kosong");
            et_nama_gejala.requestFocus();
            return false;
        }
        if (nilai_cf.equals("")) {
            et_nilai_cf.setError("Nilai CF tidak boleh kosong");
            et_nilai_cf.requestFocus();
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