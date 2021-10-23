package com.apollo.consulta_correios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ActivityInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Intent intent = new Intent(ActivityInicio.this, ActivityLoadPackage.class);
        intent.putExtra("package_code", "QH534792157BR");
        startActivity(intent);
        finish();
    }
}