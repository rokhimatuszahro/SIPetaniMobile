package com.mobile.sipetani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LupaPassword extends AppCompatActivity {
    TextView buatAkun, punyaAkun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        buatAkun = (TextView)findViewById(R.id.akun);
        buatAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BuatAkun = new Intent(LupaPassword.this, BuatAkun.class);
                startActivity(BuatAkun);

                Toast.makeText(LupaPassword.this, "Buat Akun Baru", Toast.LENGTH_LONG).show();
            }
        });

        punyaAkun = (TextView)findViewById(R.id.punyaAkun);
        punyaAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PunyaAkun = new Intent(LupaPassword.this, MainActivity.class);
                startActivity(PunyaAkun);

                Toast.makeText(LupaPassword.this, "Login!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
