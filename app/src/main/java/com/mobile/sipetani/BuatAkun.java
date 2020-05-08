package com.mobile.sipetani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mobile.sipetani.Until.AppController;
import com.mobile.sipetani.Until.ServerAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class BuatAkun extends AppCompatActivity {
    TextView lupaPass, punyaAkun;
    EditText email, username, password, pin;
    RadioGroup jenkel;
    Button btnRegis;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_akun);

        email = (EditText)findViewById(R.id.email);
        username = (EditText)findViewById(R.id.nama);
        password = (EditText)findViewById(R.id.pass);
        pin = (EditText)findViewById(R.id.pin);
        jenkel = (RadioGroup)findViewById(R.id.jenKel);

        lupaPass = (TextView)findViewById(R.id.lpPass);
        punyaAkun = (TextView)findViewById(R.id.punyaAkun);
        btnRegis = (Button) findViewById(R.id.btnRegis);

        pd = new ProgressDialog(BuatAkun.this);

        lupaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lpPass = new Intent(BuatAkun.this, LupaPassword.class);
                startActivity(lpPass);

                Toast.makeText(BuatAkun.this, "Lupa Password!", Toast.LENGTH_LONG).show();
            }
        });

        punyaAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PunyaAkun = new Intent(BuatAkun.this, MainActivity.class);
                startActivity(PunyaAkun);

                Toast.makeText(BuatAkun.this, "Login!", Toast.LENGTH_LONG).show();
            }
        });

        //Ketika tombol registrasi diklik
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Menjalankan fungsi privat registdata()
                regisData();

            }
        });
    }

    // Fungsi mengolah data dengan api
    private void regisData()
    {
        pd.setMessage("Registrasi Akun");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_REGISTRASI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(BuatAkun.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                          e.printStackTrace();
                        }

                        startActivity( new Intent(BuatAkun.this,MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(BuatAkun.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> map = new HashMap<>();
                map.put("nama", username.getText().toString());
                map.put("email", email.getText().toString());
                map.put("pin", pin.getText().toString());
                map.put("password", password.getText().toString());
                map.put("jenkel", String.valueOf(jenkel.getCheckedRadioButtonId()));
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sendData);
    }

}
