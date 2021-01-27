package com.example.covid_19.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyWebService {
    String BASE_URL="https://corona.lmao.ninja/v2/";
    String INDIA="countries/india";
    String COUNTRIES="countries";

    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET(INDIA)
    Call<IndiaModel> getObject();

    @GET(COUNTRIES)
    Call<List<data>> getCountryList();

    @GET("countries/{url}")
    Call<IndiaModel1> getStats(@Path("url") String url);



}

