package com.sistempakarkulitwajah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.request.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DiagnosaForwardActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static final String url = "https://sistempakarkulitwajah77.000webhostapp.com/get_daftar_gejala.php";
    private ArrayList<HashMap<String, String>> list;
    private ArrayList<String> hasil;
    private TextView text_pertanyaan;
    private int counter;
    private final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosa_forward);
        setTitle("Diagnosa Forward Chaining");

        getData();

        text_pertanyaan = findViewById(R.id.text_pertanyaan);

        Button btn_ya = findViewById(R.id.btn_ya);
        Button btn_tidak = findViewById(R.id.btn_tidak);

        btn_ya.setOnClickListener(view -> {
            HashMap<String, String> item = list.get(counter);
            hasil.add(item.get("id_gejala"));
            showPertanyaan(++counter);
        });

        btn_tidak.setOnClickListener(view -> {
            showPertanyaan(++counter);
        });

        if (list != null && list.isEmpty()) {
            btn_ya.setVisibility(View.GONE);
            btn_tidak.setVisibility(View.GONE);
        }
    }

    private void showPertanyaan(int index) {
        if (index >= list.size()) {
            Intent intent = new Intent(getApplicationContext(), DiagnosaForwardHasilActivity.class);
            intent.putExtra("hasil", android.text.TextUtils.join("#", hasil));
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            HashMap<String, String> item = list.get(index);
            text_pertanyaan.setText("Apakah " + item.get("nama_gejala").toLowerCase() + "?");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE) {
            getData();
        }
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(DiagnosaForwardActivity.this);
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
                            JSONArray jsonArray = response.getJSONArray("gejala");
                            list = new ArrayList<>();
                            boolean kosong = true;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<>();
                                map.put("id_gejala", jsonObject.getString("id_gejala"));
                                map.put("nama_gejala", jsonObject.getString("nama_gejala"));
                                list.add(map);
                                kosong = false;
                            }

                            if (kosong) {
                                Toast.makeText(DiagnosaForwardActivity.this, "Tidak ada data gejala", Toast.LENGTH_SHORT).show();
                            } else {
                                hasil = new ArrayList<>();
                                counter = 0;
                                showPertanyaan(counter);
                            }

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
            exitByBackKey();
            return true;
        }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Anda yakin mau kembali ?")
                .setPositiveButton("Ya", (arg0, arg1) -> finish())
                .setNegativeButton("Tidak", (arg0, arg1) -> {
                })
                .show();
    }
}