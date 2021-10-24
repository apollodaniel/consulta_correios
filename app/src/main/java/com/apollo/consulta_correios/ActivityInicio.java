package com.apollo.consulta_correios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityInicio extends AppCompatActivity {

    @BindView(R.id.activity_inicio_btn_buscar)
    Button btnBuscar;
    @BindView(R.id.activity_inicio_edt_codigo_produto)
    EditText edtCodigoProduto;
    @BindView(R.id.activity_inicio_btn_historico)
    Button btnHistorico;
    @BindView(R.id.activity_inicio_imgbg)
    ImageView imgBg;

    private final String SHARED_PREFS = "prefs";
    private final String PACKAGE_QUERY_KEY = "package";

    private String codigo_produto;
    boolean notificacao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        ButterKnife.bind(ActivityInicio.this);

        Drawable drawable = getDrawable(R.mipmap.hands_bg);
        imgBg.setImageDrawable(drawable);
        imgBg.setAlpha(0.8f);

        SharedPreferences sh = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String package_codes = sh.getString(PACKAGE_QUERY_KEY, "");
        if(package_codes.isEmpty()){
            btnHistorico.setEnabled(false);
        }

        btnBuscar.setOnClickListener(View -> {
            codigo_produto = edtCodigoProduto.getText().toString();

            if(codigo_produto.isEmpty()){
                edtCodigoProduto.setError("Dígite um código antes de fazer a pesquisa!");
            }else if(codigo_produto.length() < 13){
                edtCodigoProduto.setError("Dígite um código válido!");
            }
            else{
                Intent intent = new Intent(ActivityInicio.this, ActivityLoadPackage.class);
                intent.putExtra("package_code", codigo_produto);
                startActivity(intent);
                finish();
            }
        });
        btnHistorico.setOnClickListener(View -> {
            Intent intent = new Intent(ActivityInicio.this, ActivityHistory.class);
            startActivity(intent);
            finish();
        });

    }


}