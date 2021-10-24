package com.apollo.consulta_correios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.apollo.consulta_correios.models.CorreiosEncomenda;
import com.apollo.consulta_correios.models.PackageTemplate;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityLoadPackage extends AppCompatActivity {

    private final String user = "teste";
    private final String token = "1abcd00b2731640e886fb41a8a9671ad1434c599dbaa0a0de9a5aa619f29a83f";
    private String package_code;

    private final String SHARED_PREFS = "prefs";
    private final String PACKAGE_QUERY_KEY = "package";

    private boolean isHistory;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_package);

        package_code = getIntent().getStringExtra("package_code");
        isHistory = getIntent().getBooleanExtra("isHistory", false);
        
        configureRetrofit();
    }

    private void configureRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CorreiosService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CorreiosService service = retrofit.create(CorreiosService.class);
        Call<CorreiosEncomenda> correiosEncomenda = service.showProduct(user, token, package_code);
        correiosEncomenda.enqueue(new Callback<CorreiosEncomenda>() {
            @Override
            public void onResponse(@NonNull Call<CorreiosEncomenda> call, @NonNull Response<CorreiosEncomenda> response) {
                 if (response.isSuccessful()) {
                    // sucess
                    CorreiosEncomenda encomenda = response.body();
                    if (encomenda != null) {
                        Gson gson = new Gson();
                        String json_resultado = gson.toJson(encomenda);
                        Intent intent = new Intent(ActivityLoadPackage.this, ActivityShowPackageResult.class);
                        intent.putExtra("json_resultado", json_resultado);
                        startActivity(intent);
                        if (!isHistory) {
                            finishApp();
                        }
                        finish();
                    }
                }else if (response.code() == 429) {
                     android.os.SystemClock.sleep(4000);
                     configureRetrofit();
                } else {
                    finishAffinity();
                }
            }

            @Override
            public void onFailure(Call<CorreiosEncomenda> call, Throwable t) {
                finishAffinity();
            }
        });
    }
    private void retry(){

    }
    private void finishApp() {
        SharedPreferences sh = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String package_codes = sh.getString(PACKAGE_QUERY_KEY, "");

        ArrayList<String> packages_string = new ArrayList<String>(
            Arrays.asList(package_codes.split("\\|")));

        ArrayList<PackageTemplate> packages =  new ArrayList<>();

        for(String package_ : packages_string){
            packages.add(new Gson().fromJson(package_, PackageTemplate.class));
        }

        if (package_codes.equals("")){
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
            String formattedDate = df.format(c);

            package_codes = new Gson().toJson(new PackageTemplate(package_code, formattedDate)).toString();

        }else if(!verifyPackageCode(packages)){
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            PackageTemplate packageTemplate = new PackageTemplate(package_code, formattedDate);
            packages.add(packageTemplate);

            StringBuilder sb = new StringBuilder();
            for(PackageTemplate i:packages){
                sb.append(String.format("|%s",new Gson().toJson(i)));
            }
            package_codes = sb.toString();
        }

        sh.edit().putString(PACKAGE_QUERY_KEY, package_codes).apply();
    }

    private boolean verifyPackageCode(ArrayList<PackageTemplate> packages) {
        for (PackageTemplate package_template: packages){
            if(package_template.code.equals(package_code)){
                return true;
            }
        }
        return false;
    }
}