 package com.sistempakarkulitwajah.admin;

import android.app.ProgressDialog;
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

public class PenggunaEditActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static final String url = "https://sistempakarkulitwajah77.000webhostapp.com/get_pengguna.php";
    private static final String url_update = "https://sistempakarkulitwajah77.000webhostapp.com/update_pengguna.php";
    private static final String url_delete = "https://sistempakarkulitwajah77.000webhostapp.com/delete_pengguna.php";
    private EditText et_id_pengguna;
    private EditText et_nama_lengkap;
    private EditText et_username;
    private EditText et_password;
    private String id_pengguna;
    private String nama_lengkap;
    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengguna_edit);
        setTitle("Ubah Data Pengguna");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_pengguna = extras.getString("id_pengguna");
        }

        et_id_pengguna = findViewById(R.id.et_id_pengguna);
        et_nama_lengkap = findViewById(R.id.et_nama_lengkap);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        Button btn_simpan = findViewById(R.id.btn_simpan);

        btn_simpan.setOnClickListener(view -> {
            id_pengguna = et_id_pengguna.getText().toString().trim();
            nama_lengkap = et_nama_lengkap.getText().toString().trim();
            username = et_username.getText().toString();
            password = et_password.getText().toString();
            if (validateInputs()) {
                ubahPengguna();
            }
        });

        getPengguna();
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
            new AlertDialog.Builder(PenggunaEditActivity.this)
                    .setTitle("Konfirmasi")
                    .setMessage("Anda yakin pengguna akan dihapus ?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Ya, Hapus", (dialog, whichButton) -> {
                        hapusPengguna();
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

    private void hapusPengguna() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            request.put("id_pengguna", id_pengguna);
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

    private void getPengguna() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            request.put("id_pengguna", id_pengguna);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url, request, response -> {
                    pDialog.dismiss();
                    try {
                        if (response.getInt("status") == 0) {
                            et_id_pengguna.setText(response.getString("id_pengguna"));
                            et_nama_lengkap.setText(response.getString("nama_lengkap"));
                            et_username.setText(response.getString("username"));
                            et_password.setText(response.getString("password"));
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
        pDialog = new ProgressDialog(PenggunaEditActivity.this);
        pDialog.setMessage("Sedang diproses...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void ubahPengguna() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            request.put("id_pengguna", id_pengguna);
            request.put("nama_lengkap", nama_lengkap);
            request.put("username", username);
            request.put("password", password);
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
        if (id_pengguna.equals("")) {
            et_id_pengguna.setError("id Pengguna tidak boleh kosong");
            et_id_pengguna.requestFocus();
            return false;
        }
        if (nama_lengkap.equals("")) {
            et_nama_lengkap.setError("Nama Lengkap tidak boleh kosong");
            et_nama_lengkap.requestFocus();
            return false;
        }
        if (username.equals("")) {
            et_username.setError("username tidak boleh kosong");
            et_username.requestFocus();
            return false;
        }

        return true;
    }
}