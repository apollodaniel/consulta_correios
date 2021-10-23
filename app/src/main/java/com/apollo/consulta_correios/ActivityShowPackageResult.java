package com.apollo.consulta_correios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.apollo.consulta_correios.models.CorreiosEncomenda;
import com.apollo.consulta_correios.models.Evento;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityShowPackageResult extends AppCompatActivity {

    @BindView(R.id.txt_codigo_encomenda)
    TextView txt_codigo_encomenda;
    @BindView(R.id.rv_mostra_status)
    RecyclerView rv_mostra_status;

    private final String user = "teste";
    private final String token = "1abcd00b2731640e886fb41a8a9671ad1434c599dbaa0a0de9a5aa619f29a83f";

    List<Evento> eventos_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_package_result);

        ButterKnife.bind(ActivityShowPackageResult.this);
        configureRetrofit();
    }

    private void configureRecyclerView() {
        CustomAdapter adapter = new CustomAdapter(eventos_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityShowPackageResult.this);

        rv_mostra_status.setLayoutManager(layoutManager);
        rv_mostra_status.setAdapter(adapter);
    }

    private void configureRetrofit(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CorreiosService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CorreiosService service = retrofit.create(CorreiosService.class);
        Call<CorreiosEncomenda> correiosEncomenda = service.showProduct(user, token ,"QH534792157BR");
        correiosEncomenda.enqueue(new Callback<CorreiosEncomenda>() {
            @Override
            public void onResponse(@NonNull Call<CorreiosEncomenda> call, @NonNull Response<CorreiosEncomenda> response) {
                if(response.code() == 401){
                    // unautorized error = not found product code
                    txt_codigo_encomenda.setText(R.string.product_doesnt_exists_error);
                }else if(response.code() == 200){
                    // sucess
                    CorreiosEncomenda encomenda = response.body();
                    if (encomenda != null) {
                        txt_codigo_encomenda.setText(encomenda.codigo);
                        eventos_list = encomenda.eventos;
                    }else{
                        txt_codigo_encomenda.setText(R.string.product_doesnt_exists_error);
                    }
                    configureRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<CorreiosEncomenda> call, Throwable t) {
                txt_codigo_encomenda.setText(String.format("Error: %s", t.toString()));
            }
        });
    }
}