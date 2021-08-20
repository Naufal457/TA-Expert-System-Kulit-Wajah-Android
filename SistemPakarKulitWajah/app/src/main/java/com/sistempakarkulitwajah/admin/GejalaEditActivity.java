package com.sistempakarkulitwajah.admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.request.JsonObjectRequest;
import com.sistempakarkulitwajah.MySingleton;
import com.sistempakarkulitwajah.R;

import org.json.JSONException;
import org.json.JSONObject;

public class GejalaEditActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static final String url = "https://sistempakarkulitwajah77.000webhostapp.com/get_gejala.php";
    private static final String url_update = "https://sistempakarkulitwajah77.000webhostapp.com/update_gejala.php";
    private static final String url_delete = "https://sistempakarkulitwajah77.000webhostapp.com/delete_gejala.php";
    private EditText et_kode_gejala;
    private EditText et_nama_gejala;
    private EditText et_nilai_cf;
    private String kode_gejala;
    private String nama_gejala;
    private String nilai_cf;
    private String id_gejala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gejala_edit);
        setTitle("Ubah Gejala");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_gejala = extras.getString("id_gejala");
        }

        et_kode_gejala = findViewById(R.id.et_kode_gejala);
        et_nama_gejala = findViewById(R.id.et_nama_gejala);
        et_nilai_cf = findViewById(R.id.et_nilai_cf);

        Button btn_simpan = findViewById(R.id.btn_simpan);

        btn_simpan.setOnClickListener(view -> {
            kode_gejala = et_kode_gejala.getText().toString().trim();
            nama_gejala = et_nama_gejala.getText().toString().trim();
            nilai_cf = et_nilai_cf.getText().toString();
            if (validateInputs()) {
                ubahGejala();
            }
        });

        getGejala();
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
            new AlertDialog.Builder(GejalaEditActivity.this)
                    .setTitle("Konfirmasi")
                    .setMessage("Anda yakin gejala akan dihapus ?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Ya, Hapus", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            hapusGejala();
                            finish();
                        }
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

    private void hapusGejala() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            request.put("id_gejala", id_gejala);
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

    private void getGejala() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            request.put("id_gejala", id_gejala);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url, request, response -> {
                    pDialog.dismiss();
                    try {
                        if (response.getInt("status") == 0) {
                            et_kode_gejala.setText(response.getString("kode_gejala"));
                            et_nama_gejala.setText(response.getString("nama_gejala"));
                            et_nilai_cf.setText(response.getString("nilai_cf"));
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
        pDialog = new ProgressDialog(GejalaEditActivity.this);
        pDialog.setMessage("Sedang diproses...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void ubahGejala() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            request.put("id_gejala", id_gejala);
            request.put("kode_gejala", kode_gejala);
            request.put("nama_gejala", nama_gejala);
            request.put("nilai_cf", nilai_cf);
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
}