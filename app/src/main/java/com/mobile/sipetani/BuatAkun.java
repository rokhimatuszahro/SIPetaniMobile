package com.mobile.sipetani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
    EditText email, username, password, pin, no, alamat;
    RadioGroup jenkel;
    RadioButton radioButton;
    Button btnRegis;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_akun);

        email = (EditText)findViewById(R.id.email);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.pass);
        pin = (EditText)findViewById(R.id.pin);
        no = (EditText)findViewById(R.id.noHp);
        alamat = (EditText)findViewById(R.id.alamat);
        jenkel = (RadioGroup)findViewById(R.id.jenKel);
        lupaPass = (TextView)findViewById(R.id.lpPass);
        punyaAkun = (TextView)findViewById(R.id.punyaAkun);
        btnRegis = (Button)findViewById(R.id.btnRegis);
        pd = new ProgressDialog(this);

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

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonId = jenkel.getCheckedRadioButtonId();
                radioButton = (RadioButton)findViewById(radioButtonId);
                regisData();
            }
        });
    }

    private void regisData() {
        pd.setMessage("Registrasi Akun");
        pd.setCancelable(false);
        pd.show();

        final String Email = email.getText().toString();
        final String Username = username.getText().toString();
        final String Password = password.getText().toString();
        final String PIN = pin.getText().toString();
        final String NO = no.getText().toString();
        final String Alamat = alamat.getText().toString();

        try {
            pd.cancel();
            if (Email.isEmpty()){
                email.setError("Email kosong, isikan Email Anda!");
                email.requestFocus();
                return;
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                email.setError("Email salah, isikan Email dengan benar!");
                email.requestFocus();
                return;
            }
            else if (Username.isEmpty()){
                username.setError("Username kosong, isikan Username Anda!");
                username.requestFocus();
                return;
            }
            else if (Password.isEmpty()){
                password.setError("Password kosong, isikan Password Anda!");
                password.requestFocus();
                return;
            }
            else if (Password.length()<4 || Password.length()>8){
                password.setError("Password harus berjumlah lebih dari 4 atau kurang dari 8 digit!");
                password.requestFocus();
                return;
            }
            else if (PIN.isEmpty()){
                pin.setError("PIN kosong, isikan PIN Anda!");
                pin.requestFocus();
                return;
            }
            else if (PIN.length() != 3){
                pin.setError("PIN harus berjumlah 3 digit!");
                pin.requestFocus();
                return;
            }
            else if (NO.isEmpty()){
                no.setError("No Telp kosong, isikan No Telp Anda!");
                no.requestFocus();
                return;
            }
            else if (NO.length()<11 || NO.length()>13){
                no.setError("No Telp harus berjumlah 11 atau 12 digit!");
                no.requestFocus();
                return;
            }
            else if (Alamat.isEmpty()){
                alamat.setError("Alamat kosong, isikan Alamat Anda!");
                alamat.requestFocus();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_REGISTRASI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if (res.optString("success").equals("1")) {
                                Toast.makeText(BuatAkun.this, "pesan : " + res.getString("message"),
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(BuatAkun.this, MainActivity.class));
                            } else if (res.optString("success").equals("0")) {
                                email.setError(res.getString("message"));
                                email.requestFocus();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(BuatAkun.this, "pesan : Periksa Koneksi Anda!", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> map = new HashMap<>();
                map.put("nama", username.getText().toString());
                map.put("email", email.getText().toString());
                map.put("pin", pin.getText().toString());
                map.put("password", password.getText().toString());
                map.put("jenkel", radioButton.getText().toString());
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sendData);
    }
}
