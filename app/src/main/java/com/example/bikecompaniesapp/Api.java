package com.example.bikecompaniesapp;

import com.example.bikecompaniesapp.model.BikeCompany;
import com.example.bikecompaniesapp.model.FinalClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
  //  String BASE_URL="https://api.citybik.es/v2/";

    @GET("networks")
    Call<FinalClass> getBikeCompanies();
}
