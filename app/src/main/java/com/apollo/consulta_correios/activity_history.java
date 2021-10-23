package com.apollo.consulta_correios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.apollo.consulta_correios.models.PackageTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        ButterKnife.bind(activity_history.this);

        SharedPreferences sh = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String package_codes = sh.getString(PACKAGE_QUERY_KEY, "");

        btnVoltar.setOnClickListener(View -> {
            Intent intent = new Intent(activity_history.this, ActivityInicio.class);
            startActivity(intent);
            finish();
        });

        ArrayList<String> packages_string = new ArrayList<String>(Arrays.asList(package_codes.split("\\|")));
        List<PackageTemplate> packages_class = new ArrayList<>();
        for(String i:packages_string){
            packages_class.add(new Gson().fromJson(i,PackageTemplate.class));
        }
        configureRecyclerView(packages_class);
    }
    private void configureRecyclerView(List<PackageTemplate> j){
        HistoryAdapter adapter = new HistoryAdapter(j);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rvShowHistory.setLayoutManager(layoutManager);
        rvShowHistory.setAdapter(adapter);
    }

}