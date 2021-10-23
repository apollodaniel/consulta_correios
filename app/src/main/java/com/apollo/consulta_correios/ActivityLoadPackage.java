package com.apollo.consulta_correios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.apollo.consulta_correios.models.CorreiosEncomenda;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityLoadPackage extends AppCompatActivity {

    private final String user = "teste";
    private final String token = "1abcd00b2731640e886fb41a8a9671ad1434c599dbaa0a0de9a5aa619f29a83f";
    private String package_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_package);

        package_code = getIntent().getStringExtra("package_code");
        configureRetrofit();
    }
    private void configureRetrofit(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CorreiosService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CorreiosService service = retrofit.create(CorreiosService.class);
        Call<CorreiosEncomenda> correiosEncomenda = service.showProduct(user, token, package_code);
        correiosEncomenda.enqueue(new Callback<CorreiosEncomenda>() {
            @Override
            public void onResponse(@NonNull Call<CorreiosEncomenda> call, @NonNull Response<CorreiosEncomenda> response) {
                if(response.code() == 401){
                    // unautorized error = not found product code
                    finishAffinity();
                }else if(response.code() == 200){
                    // sucess
                    CorreiosEncomenda encomenda = response.body();
                    if (encomenda != null) {
                        Gson gson = new Gson();
                        String json_resultado = gson.toJson(CorreiosEncomenda.class);
                        Intent intent = new Intent(ActivityLoadPackage.this, ActivityShowPackageResult.class);
                        intent.putExtra("json_resultado", json_resultado);
                        startActivity(intent);
                        finish();
                    }else{
                        finishAffinity();
                    }
                }
            }

            @Override
            public void onFailure(Call<CorreiosEncomenda> call, Throwable t) {
                finishAffinity();
            }
        });
    }
}