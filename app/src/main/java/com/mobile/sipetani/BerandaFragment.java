package com.mobile.sipetani;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BerandaFragment extends Fragment {
    private static String view;
    Button btnPemesanan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);

        btnPemesanan = (Button) view.findViewById(R.id.btnpesan);
        btnPemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnpesan = new Intent(getActivity(), DetailPembayaran.class);
                startActivity(btnpesan);

                Toast.makeText(getActivity(), "Detail Pembayaran.", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
