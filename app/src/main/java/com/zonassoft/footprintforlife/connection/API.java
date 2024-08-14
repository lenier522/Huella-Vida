package com.zonassoft.footprintforlife.connection;

import com.zonassoft.footprintforlife.connection.response.ResponseIndicators;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface API {

    String CACHE = "Cache-Control: max-age=0";
    String AGENT = "User-Agent: huella";
    String TYPE = "Content-Type: application/json";
    String SECURITY = "Authorization: C2F7C15AB8FCCC3395F9E7475A955";


    @Headers({CACHE, AGENT, SECURITY})
    @GET("/")
    Call<ResponseIndicators> getIndicators(
            @Query("country") String country);


}
