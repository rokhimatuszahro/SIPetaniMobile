package com.mobile.sipetani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DetailPembayaran extends AppCompatActivity {

    TextView tglPemesanan, jumTiket, totPembayaran, namaPembeli, noHpPembeli, alamatPembeli;
    Button btnPesanTiket, btnBatalPesanTiket;
    SharedPreferenceHelper sp;
    ProgressDialog pd;
    private String hari;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembayaran);

        namaPembeli = (TextView)findViewById(R.id.namaPembeli);
        noHpPembeli = (TextView)findViewById(R.id.noHpDetailPemesanan);
        alamatPembeli = (TextView)findViewById(R.id.alamatDetailPemesanan);
        tglPemesanan = (TextView)findViewById(R.id.isiTglBerkunjung);
        jumTiket = (TextView)findViewById(R.id.isiJumTiket);
        totPembayaran = (TextView)findViewById(R.id.isiTotPembayaran);
        sp = new SharedPreferenceHelper(DetailPembayaran.this);
        pd = new ProgressDialog(DetailPembayaran.this);

        // Menerima Parsing data dari fragment beranda
        Intent intent = getIntent();
        final String tanggal = intent.getStringExtra("tanggal");
        final String jumlah = intent.getStringExtra("jumlah");

        // Mengambil nama hari saat ini
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = df.parse(tanggal);
            DateFormat formathari = new SimpleDateFormat("EEEE", Locale.ENGLISH);
            hari = formathari.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Menghitung total harga berdasarkan hari
        if (hari.equals("Saturday") || hari.equals("Sunday")){
            total = Integer.parseInt(jumlah) * 20000;
        }else if (hari.equals("Friday")){
            startActivity(new Intent(DetailPembayaran.this, Navigation.class));
            Toast.makeText(DetailPembayaran.this, "Hari Jumat Libur", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            total = Integer.parseInt(jumlah) * 12000;
        }

        // Menghitung data sesuai data yang dikirim dari beranda
        tglPemesanan.setText(tanggal);
        jumTiket.setText(jumlah);
        totPembayaran.setText("IDR "+ NumberFormat.getNumberInstance(Locale.US).format(total));

        btnPesanTiket = (Button)findViewById(R.id.btnPesanTiket);
        btnPesanTiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesantiket();
            }
        });

        btnBatalPesanTiket = (Button)findViewById(R.id.btnBatalPesanTiket);
        btnBatalPesanTiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailPembayaran.this, Navigation.class));
                finish();
            }
        });
    }

    private void pesantiket(){
        final String Nama = namaPembeli.getText().toString();
        final String Email = sp.getEmail("email");
        final String noHp = noHpPembeli.getText().toString();
        final String Alamat = alamatPembeli.getText().toString();
        final String Jumlah = jumTiket.getText().toString();
        final String Tanggal_Pemesanan = tglPemesanan.getText().toString();
        final String Total = String.valueOf(total);
        final String UserId = String.valueOf(sp.getId_user(0));

        // Validasi Form pemesanan
        try {
            if (Nama.isEmpty()){
                namaPembeli.setError("Nama pemesan tidak boleh kosong");
                namaPembeli.requestFocus();
                return;
            }
            if (noHp.isEmpty()){
                noHpPembeli.setError("No Hp tidak boleh kosong");
                noHpPembeli.requestFocus();
                return;
            }
            if (noHp.length()<11 || noHp.length()>13){
                noHpPembeli.setError("No Hp harus 11 atau 12 digit");
                noHpPembeli.requestFocus();
                return;
            }
            if (Alamat.isEmpty()){
                alamatPembeli.setError("Almat tidak boleh kosong");
                alamatPembeli.requestFocus();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pd.setMessage("Proses Pemesanan Tiket....");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_PEMESANAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if (!res.optString("success").equals("2")) {
                                if (res.optString("success").equals("1")) {
                                    startActivity(new Intent(DetailPembayaran.this, DetailPemesanan.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(DetailPembayaran.this, Navigation.class));
                                    Toast.makeText(DetailPembayaran.this, res.getString("message"), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } else {
                                startActivity(new Intent(DetailPembayaran.this, Navigation.class));
                                Toast.makeText(DetailPembayaran.this, res.getString("message"), Toast.LENGTH_SHORT).show();
                                finish();
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
                        sp.logout();
                        startActivity(new Intent(DetailPembayaran.this, MainActivity.class));
                        Toast.makeText(DetailPembayaran.this, "pesan: Periksa Koneksi Anda", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> map = new HashMap<>();
                map.put("nama", Nama);
                map.put("email", Email);
                map.put("no", noHp);
                map.put("alamat", Alamat);
                map.put("jumlah", Jumlah);
                map.put("tanggal_berkunjung", Tanggal_Pemesanan);
                map.put("total", Total);
                map.put("user_id", UserId);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sendData);

    }
}
