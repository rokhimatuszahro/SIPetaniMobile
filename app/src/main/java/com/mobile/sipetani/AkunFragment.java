package com.mobile.sipetani;

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

public class AkunFragment extends Fragment {
    TextView profil, detailpemesanan, keluar, tiket;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view2 = inflater.inflate(R.layout.fragment_akun, container, false);

        profil = (TextView) view2.findViewById(R.id.akunProfil);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inProfil = new Intent(getActivity(), Profil.class);
                startActivity(inProfil);

                Toast.makeText(getActivity(), "Profil Anda", Toast.LENGTH_LONG).show();
            }
        });

        detailpemesanan = (TextView) view2.findViewById(R.id.akunDetailPemesanan);
        detailpemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailPemesanan = new Intent(getActivity(), DetailPemesanan.class);
                startActivity(detailPemesanan);

                Toast.makeText(getActivity(), "Detail Pemesanan", Toast.LENGTH_LONG).show();
            }
        });

        keluar = (TextView) view2.findViewById(R.id.logout);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keluarAkun = new Intent(getActivity(), MainActivity.class);
                startActivity(keluarAkun);

                Toast.makeText(getActivity(), "Keluar", Toast.LENGTH_LONG).show();
            }
        });

        tiket = (TextView) view2.findViewById(R.id.akunTiket);
        tiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tiketAkun = new Intent(getActivity(), Tiket.class);
                startActivity(tiketAkun);

                Toast.makeText(getActivity(), "Tiket Anda", Toast.LENGTH_LONG).show();
            }
        });

        return view2;
    }
}
