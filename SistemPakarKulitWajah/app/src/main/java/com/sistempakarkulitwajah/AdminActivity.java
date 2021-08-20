package com.sistempakarkulitwajah;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.sistempakarkulitwajah.admin.AturanActivity;
import com.sistempakarkulitwajah.admin.PenggunaActivity;
import com.sistempakarkulitwajah.admin.GejalaActivity;
import com.sistempakarkulitwajah.admin.PenyakitActivity;
import com.sistempakarkulitwajah.admin.PanduanAdminActivity;

import static android.R.drawable.ic_dialog_alert;

//cuma menambahkan aja
public class AdminActivity extends AppCompatActivity {

    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("Menu Utama Admin");

        session = new SessionHandler(getApplicationContext());

        Button btn_gejala = findViewById(R.id.btn_gejala);
        Button btn_penyakit = findViewById(R.id.btn_penyakit);
        Button btn_data_pengguna = findViewById(R.id.btn_data_pengguna);
        Button btn_aturan = findViewById(R.id.btn_aturan);
        Button btn_panduan =  findViewById(R.id.btn_panduan);
        Button btn_logout = findViewById(R.id.btn_logout);


        btn_gejala.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, GejalaActivity.class);
            startActivity(intent);
        });

        btn_data_pengguna.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, PenggunaActivity.class);
            startActivity(intent);
        });


        btn_penyakit.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, PenyakitActivity.class);
            startActivity(intent);
        });

        btn_aturan.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, AturanActivity.class);
            startActivity(intent);

        });

        btn_panduan.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, PanduanAdminActivity.class);
            startActivity(intent);
        });

        btn_logout.setOnClickListener(view -> new AlertDialog.Builder(AdminActivity.this)
                .setTitle("Konfirmasi")
                .setMessage("Anda yakin mau logout ?")
                .setIcon(ic_dialog_alert)
                .setPositiveButton("Ya, Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        session.logoutUser();
                        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Tidak", null).show());

    }
}