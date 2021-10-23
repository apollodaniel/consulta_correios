package com.apollo.consulta_correios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.apollo.consulta_correios.models.CorreiosEncomenda;
import com.apollo.consulta_correios.models.Evento;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityShowPackageResult extends AppCompatActivity {

    @BindView(R.id.txt_codigo_encomenda)
    TextView txt_codigo_encomenda;
    @BindView(R.id.rv_mostra_status)
    RecyclerView rv_mostra_status;

    List<Evento> eventos_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_package_result);

        ButterKnife.bind(ActivityShowPackageResult.this);

        Gson gson = new Gson();
        String json_resultado = getIntent().getStringExtra("json_resultado");
        CorreiosEncomenda encomenda = gson.fromJson(json_resultado, CorreiosEncomenda.class);

        txt_codigo_encomenda.setText(encomenda.codigo);
        eventos_list = encomenda.eventos;
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        StatusAdapter adapter = new StatusAdapter(eventos_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityShowPackageResult.this);

        rv_mostra_status.setLayoutManager(layoutManager);
        rv_mostra_status.setAdapter(adapter);
    }


}