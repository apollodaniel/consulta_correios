package com.apollo.consulta_correios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityAbout extends AppCompatActivity {

    @BindView(R.id.activity_about_txt_apollo)
    TextView txtApollo;
    @BindView(R.id.activity_about_txt_chipytux)
    TextView txtChipytux;
    @BindView(R.id.activity_about_btn_voltar)
    Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);

        txtApollo.setOnClickListener(View -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/apollodaniel"));
            startActivity(browserIntent);
        });

        txtChipytux.setOnClickListener(View -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/chipytux/correiosApi"));
            startActivity(browserIntent);
        });

        btnVoltar.setOnClickListener(View -> {
            Intent intent = new Intent(ActivityAbout.this, ActivityInicio.class);
            startActivity(intent);
            finish();
        });


    }
}