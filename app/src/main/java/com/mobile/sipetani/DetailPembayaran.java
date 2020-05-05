package com.mobile.sipetani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DetailPembayaran extends AppCompatActivity {

    Button btnPesanTiket, btnBatalPesanTiket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembayaran);

        btnPesanTiket = (Button)findViewById(R.id.btnPesanTiket);
        btnPesanTiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnpesantiket = new Intent(DetailPembayaran.this, DetailPemesanan.class);
                startActivity(btnpesantiket);

                Toast.makeText(DetailPembayaran.this, "Detail Pemesanan.", Toast.LENGTH_LONG).show();
            }
        });

//        btnBatalPesanTiket = (Button)findViewById(R.id.btnBatalPesanTiket);
//        btnBatalPesanTiket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent btnbatalpesantiket = new Intent(DetailPembayaran.this, BerandaFragment.class);
//                startActivity(btnbatalpesantiket);
//
//                Toast.makeText(DetailPembayaran.this, "Beranda!", Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
