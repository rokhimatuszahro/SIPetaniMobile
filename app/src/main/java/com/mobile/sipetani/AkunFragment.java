package com.mobile.sipetani;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobile.sipetani.SharedPreference.SharedPreferenceHelper;

public class AkunFragment extends Fragment {
    TextView profil, detailpemesanan, keluar, tiket;
    ProgressDialog pd;
    SharedPreferenceHelper sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewroot = inflater.inflate(R.layout.fragment_akun, container, false);

        pd = new ProgressDialog(getActivity());
        sp = new SharedPreferenceHelper(getActivity());

        profil = (TextView) viewroot.findViewById(R.id.akunProfil);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inProfil = new Intent(getActivity(), Profil.class);
                startActivity(inProfil);

                Toast.makeText(getActivity(), "Profil Anda", Toast.LENGTH_LONG).show();
            }
        });

        detailpemesanan = (TextView) viewroot.findViewById(R.id.akunDetailPemesanan);
        detailpemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailPemesanan = new Intent(getActivity(), DetailPemesanan.class);
                startActivity(detailPemesanan);

                Toast.makeText(getActivity(), "Detail Pemesanan", Toast.LENGTH_LONG).show();
            }
        });

        keluar = (TextView) viewroot.findViewById(R.id.logout);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Proses Logout...");
                pd.setCancelable(false);
                pd.show();
                sp.logout();
                pd.cancel();
                startActivity(new Intent(getActivity(), MainActivity.class));
                Toast.makeText(getActivity(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });

        tiket = (TextView) viewroot.findViewById(R.id.akunTiket);
        tiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tiketAkun = new Intent(getActivity(), Tiket.class);
                startActivity(tiketAkun);

                Toast.makeText(getActivity(), "Tiket Anda", Toast.LENGTH_LONG).show();
            }
        });

        return viewroot;
    }
}
