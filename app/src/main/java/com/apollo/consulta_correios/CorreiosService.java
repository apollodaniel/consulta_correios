package com.apollo.consulta_correios;

import com.apollo.consulta_correios.models.CorreiosEncomenda;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CorreiosService {

    public final static String BASE_URL="https://api.linketrack.com/track/";

    //user=teste&token=1abcd00b2731640e886fb41a8a9671ad1434c599dbaa0a0de9a5aa619f29a83f&codigo={codigo}
    @GET("json")
    Call<CorreiosEncomenda> showProduct(@Query("user") String user, @Query("token") String token,@Query("codigo") String codigo);
}
