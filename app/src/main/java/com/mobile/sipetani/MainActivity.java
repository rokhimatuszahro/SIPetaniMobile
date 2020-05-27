package com.mobile.sipetani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mobile.sipetani.SharedPreference.SharedPreferenceHelper;
import com.mobile.sipetani.Until.AppController;
import com.mobile.sipetani.Until.ServerAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mobile.sipetani.SharedPreference.SharedPreferenceHelper.login;

public class MainActivity extends AppCompatActivity {
    TextView lupaPass, buatAkun, errorMessage;
    EditText email, password;
    Button btnlogin;
    ProgressDialog pd;
    SharedPreferenceHelper sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.Log_email);
        password = findViewById(R.id.Log_pass);
        pd = new ProgressDialog(MainActivity.this);
        sp = new SharedPreferenceHelper(MainActivity.this);

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

        btnlogin = findViewById(R.id.btnLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                errorMessage.setVisibility(View.INVISIBLE);
            }
        });
    }

    // Cek apakah sudah login
    @Override
    protected void onStart() {
        super.onStart();
        if (sp.getLogin(true)){
            startActivity(new Intent(MainActivity.this,Navigation.class));
            finish();
        }
    }

    private void login()
    {
        // Validasi Form
        errorMessage = findViewById(R.id.error_message);
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        try {
            if (Email.isEmpty()){
                email.setError("Email kosong, isikan Email Anda!");
                email.requestFocus();
                pd.cancel();
                return;
            }else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                email.setError("Email salah, isikan Email dengan benar!");
                email.requestFocus();
                return;
            }else if (Password.isEmpty()){
                password.setError("Password kosong, isikan Password Anda!");
                password.requestFocus();
                pd.cancel();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pd.setMessage("Proses Login...");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if (res.optString("success").equals("1")) {
                                JSONObject data = res.getJSONObject("user_detail");
                                sp.setEmail(data.getString("email"));
                                sp.setId_akses(data.getInt("id_akses"));
                                sp.setId_user(data.getInt("id_user"));
                                sp.setLogin(true);
                                startActivity(new Intent(MainActivity.this, Navigation.class));
                            } else {
                                errorMessage.setText(res.getString("message"));
                                errorMessage.setVisibility(View.VISIBLE);
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
                        Toast.makeText(MainActivity.this, "pesan : Periksa Koneksi Anda", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> map = new HashMap<>();
                map.put("email", email.getText().toString());
                map.put("password", password.getText().toString());
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sendData);
    }
}
