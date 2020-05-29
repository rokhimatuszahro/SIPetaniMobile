package com.mobile.sipetani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mobile.sipetani.Helper.SharedPreferenceHelper;
import com.mobile.sipetani.Until.AppController;
import com.mobile.sipetani.Until.ServerAPI;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profil extends AppCompatActivity {

    private String gambar = "";
    TextView email, nama, pin, jenkel;
    EditText inpNama, inpEmail, inpPin, inpPassBaru, inpPassLama;
    RadioGroup groupJenkel;
    RadioButton Jenkel, Rb_Laki, Rb_Perempuan;
    CircleImageView ftprofile;
    ImageView newftprofile;
    Bitmap bitmap;
    Button btnEdit;
    SharedPreferenceHelper sp;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        email = (TextView)findViewById(R.id.isiEmailProfil);
        nama = (TextView)findViewById(R.id.isiNamaProfil);
        pin = (TextView)findViewById(R.id.isiPinProfil);
        jenkel = (TextView)findViewById(R.id.isiJenkelProfil);
        inpEmail = (EditText) findViewById(R.id.emailEdit);
        inpNama = (EditText) findViewById(R.id.namaEdit);
        inpPin = (EditText) findViewById(R.id.pinEdit);
        inpPassBaru = (EditText) findViewById(R.id.passBaruEdit);
        inpPassLama = (EditText) findViewById(R.id.passLamaEdit);
        Rb_Laki = (RadioButton) findViewById(R.id.rblakilaki);
        Rb_Perempuan = (RadioButton) findViewById(R.id.rbperempuan);
        ftprofile = (CircleImageView) findViewById(R.id.imgFotoProfil2);
        newftprofile = (ImageView) findViewById(R.id.imgFtProfil);
        sp = new SharedPreferenceHelper(Profil.this);
        pd = new ProgressDialog(Profil.this);

        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupJenkel = (RadioGroup) findViewById(R.id.jenKelEdit);
                int RadioButtonId = groupJenkel.getCheckedRadioButtonId();
                Jenkel = (RadioButton)findViewById(RadioButtonId);
                editprofile();
            }
        });

        newftprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        detail_profile();
    }

    private void detail_profile(){
        final String Email = sp.getEmail("email");

        pd.setMessage("Proses load Profile...");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if (res.optString("success").equals("1")) {
                                JSONObject data = new JSONObject(res.getString("profile"));
                                Picasso.get().load(res.getString("foto"))
                                        .placeholder(R.drawable.ftprofil)
                                        .into(ftprofile);
                                if (data.optString("jenkel").equals(Rb_Laki.getText().toString())) {
                                    Rb_Laki.setChecked(true);
                                } else {
                                    Rb_Perempuan.setChecked(true);
                                }
                                email.setText(data.getString("email"));
                                nama.setText(data.getString("nama"));
                                pin.setText(data.getString("pin"));
                                jenkel.setText(data.getString("jenkel"));
                                inpEmail.setText(data.getString("email"));
                                inpNama.setText(data.getString("nama"));
                                inpPin.setText(data.getString("pin"));
                            } else {
                                startActivity(new Intent(Profil.this, Navigation.class));
                                Toast.makeText(Profil.this, res.getString("message"), Toast.LENGTH_LONG).show();
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
                        startActivity(new Intent(Profil.this, MainActivity.class));
                        Toast.makeText(Profil.this, "pesan : Periksa Koneksi Anda", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> map = new HashMap<>();
                map.put("email", Email);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sendData);
    }

    private void editprofile(){

        final String ID_USER = String.valueOf(sp.getId_user(0));
        final String EMAIL = sp.getEmail("email");
        final String InpEMAIL = inpEmail.getText().toString();
        final String InpNAMA = inpNama.getText().toString();
        final String InpPIN = inpPin.getText().toString();
        final String InpJENKEL = Jenkel.getText().toString();
        final String InpPassLama = inpPassLama.getText().toString();
        final String InpPassBaru = inpPassBaru.getText().toString();

        try {
            if (InpEMAIL.isEmpty()){
                inpEmail.setError("Email kosong, isikan Email Anda!");
                inpEmail.requestFocus();
                return;
            }else if (!Patterns.EMAIL_ADDRESS.matcher(InpEMAIL).matches()){
                inpEmail.setError("Email salah, isikan Email dengan benar!");
                inpEmail.requestFocus();
                return;
            }else if (InpNAMA.isEmpty()){
                inpNama.setError("Nama tidak boleh kosong!");
                inpNama.requestFocus();
                return;
            }else if (InpPIN.isEmpty()){
                inpPin.setError("PIN tidak boleh kosong!");
                inpPin.requestFocus();
                return;
            }else if (InpPIN.length() != 3){
                inpPin.setError("PIN harus berjumlah 3 digit!");
                inpPin.requestFocus();
                return;
            }else if (!InpPassLama.isEmpty()){
                if (InpPassBaru.isEmpty()){
                    inpPassBaru.setError("Password baru tidak boleh kosong!", null);
                    inpPassBaru.requestFocus();
                    return;
                }else if (InpPassBaru.length() < 4 || InpPassBaru.length() > 8){
                    inpPassBaru.setError("Password baru harus 4 - 8 digit!", null);
                    inpPassBaru.requestFocus();
                    return;
                }
            }else if (!InpPassBaru.isEmpty()){
                if (InpPassBaru.isEmpty()){
                    inpPassBaru.setError("Password baru tidak boleh kosong!", null);
                    inpPassBaru.requestFocus();
                    return;
                }else if (InpPassBaru.length() < 4 || InpPassBaru.length() > 8){
                    inpPassBaru.setError("Password baru harus 4 - 8 digit!", null);
                    inpPassBaru.requestFocus();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pd.setMessage("Proses Edit Profile...");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_EDIT_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if (res.optString("success").equals("1")) {
                                Toast.makeText(Profil.this, res.getString("message"), Toast.LENGTH_SHORT).show();
                                detail_profile();
                            } else if (res.optString("success").equals("2")) {
                                inpPassLama.setError(res.getString("message"), null);
                                inpPassLama.requestFocus();
                            } else if (res.optString("success").equals("3")) {
                                sp.logout();
                                startActivity(new Intent(Profil.this, MainActivity.class));
                                Toast.makeText(Profil.this, res.getString("message"), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(Profil.this, res.getString("message"), Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(Profil.this, MainActivity.class));
                        Toast.makeText(Profil.this, "pesan : Periksa Koneksi Anda", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> map = new HashMap<>();
                map.put("id_user", ID_USER);
                map.put("email", EMAIL);
                map.put("passlama", InpPassLama);
                map.put("passbaru", InpPassBaru);
                map.put("inpemail", InpEMAIL);
                map.put("inpnama", InpNAMA);
                map.put("inppin", InpPIN);
                map.put("inpjenkel", InpJENKEL);
                map.put("foto", gambar);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sendData);
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){

            if (requestCode == 1){
                Uri selectedImage = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    bitmap = getResizedBitmap(bitmap, 400);
                    newftprofile.setImageBitmap(bitmap);
                    gambar = getStringImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getStringImage(Bitmap image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imgbytearray = baos.toByteArray();
        String img = Base64.encodeToString(imgbytearray, Base64.DEFAULT);
        return img;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize){
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float)height;
        if (bitmapRatio > 1){
            width = maxSize;
            height = (int) (width / bitmapRatio);
        }else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
