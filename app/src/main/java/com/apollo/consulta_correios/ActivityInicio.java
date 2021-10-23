package com.apollo.consulta_correios;

import androidx.appcompat.app.AppCompatActivity;

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



    private String codigo_produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        ButterKnife.bind(ActivityInicio.this);
        btnBuscar.setOnClickListener(View -> {
            codigo_produto = edtCodigoProduto.getText().toString();

            Intent intent = new Intent(ActivityInicio.this, ActivityLoadPackage.class);
            intent.putExtra("package_code", codigo_produto);
            startActivity(intent);
            finish();
        });
    }


}