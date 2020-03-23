package com.ewe.parlae.ratestask.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @GET("latest")
    Call<String> getRatesFor(@Query("base") String currency);

}
