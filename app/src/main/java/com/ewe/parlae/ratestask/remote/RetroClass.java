package com.ewe.parlae.ratestask.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetroClass {

    private static final String BASE_URL="https://hiring.revolut.codes/api/android/";

    private static Retrofit getRetroInstance(){

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setLenient().create();

        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    public static APIService getApiService(){

        return getRetroInstance().create(APIService.class);
    }

    private static Retrofit getStringRetroInstance(){

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").setLenient().create();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static APIService getStringApiService(){

        return getStringRetroInstance().create(APIService.class);
    }
}
