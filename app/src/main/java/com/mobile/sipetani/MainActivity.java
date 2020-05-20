package com.mobile.sipetani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView lupaPass, buatAkun;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lupaPass = (TextView)findViewById(R.id.lpPass);
        lupaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lpPass = new Intent(MainActivity.this, LupaPassword.class);
                startActivity(lpPass);

                Toast.makeText(MainActivity.this, "Lupa Password?", Toast.LENGTH_LONG).show();
            }
        });

        buatAkun = (TextView)findViewById(R.id.akun);
        buatAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BuatAkun = new Intent(MainActivity.this, BuatAkun.class);
                startActivity(BuatAkun);


                Toast.makeText(MainActivity.this, "Buat Akun Baru!", Toast.LENGTH_LONG).show();
            }
        });

        btnlogin = (Button) findViewById(R.id.btnLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BtnLogin = new Intent(MainActivity.this, Navigation.class);
                startActivity(BtnLogin);

                Toast.makeText(MainActivity.this, "Beranda!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
