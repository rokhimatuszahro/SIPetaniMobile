package com.mobile.sipetani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BuatAkun extends AppCompatActivity {
    TextView lupaPass, punyaAkun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_akun);

        lupaPass = (TextView)findViewById(R.id.lpPass);
        lupaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lpPass = new Intent(BuatAkun.this, LupaPassword.class);
                startActivity(lpPass);

                Toast.makeText(BuatAkun.this, "Lupa Password!", Toast.LENGTH_LONG).show();
            }
        });

        punyaAkun = (TextView)findViewById(R.id.punyaAkun);
        punyaAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PunyaAkun = new Intent(BuatAkun.this, MainActivity.class);
                startActivity(PunyaAkun);

                Toast.makeText(BuatAkun.this, "Login!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
