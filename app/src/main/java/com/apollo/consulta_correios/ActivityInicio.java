package com.apollo.consulta_correios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityInicio extends AppCompatActivity {

    @BindView(R.id.activity_inicio_btn_buscar)
    Button btnBuscar;
    @BindView(R.id.activity_inicio_edt_codigo_produto)
    EditText edtCodigoProduto;
    @BindView(R.id.activity_inicio_btn_historico)
    Button btnHistorico;

    private final String SHARED_PREFS = "prefs";
    private final String PACKAGE_QUERY_KEY = "package";

    private String codigo_produto;
    boolean notificacao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        ButterKnife.bind(ActivityInicio.this);

        SharedPreferences sh = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String package_codes = sh.getString(PACKAGE_QUERY_KEY, "");
        if(package_codes.isEmpty()){
            btnHistorico.setEnabled(false);
        }

        btnBuscar.setOnClickListener(View -> {
            codigo_produto = edtCodigoProduto.getText().toString();

            Intent intent = new Intent(ActivityInicio.this, ActivityLoadPackage.class);
            intent.putExtra("package_code", codigo_produto);
            startActivity(intent);
            finish();
        });
        btnHistorico.setOnClickListener(View -> {
            Intent intent = new Intent(ActivityInicio.this, activity_history.class);
            startActivity(intent);
            finish();
        });

    }
    private void notificationClick()
    {

    }

}