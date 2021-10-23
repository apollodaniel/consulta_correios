package com.apollo.consulta_correios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

public class activity_history extends AppCompatActivity {

    private final String SHARED_PREFS = "prefs";
    private final String PACKAGE_QUERY_KEY = "package";

    @BindView(R.id.activity_history_rv_show_history)
    RecyclerView rvShowHistory;
    @BindView(R.id.activity_history_btn_voltar)
    Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SharedPreferences sh = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String package_codes = sh.getString(PACKAGE_QUERY_KEY, "");

        btnVoltar.setOnClickListener(View -> {
            Intent intent = new Intent(activity_history.this, ActivityInicio.class);
            startActivity(intent);
            finish();
        });

        ArrayList<String> package_codes_array = new ArrayList<String>(Arrays.asList(package_codes.split("\\|")));

    }
}