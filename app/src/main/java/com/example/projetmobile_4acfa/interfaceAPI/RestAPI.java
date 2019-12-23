package com.example.projetmobile_4acfa.interfaceAPI;

import com.example.projetmobile_4acfa.model.Cooking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestAPI {

    @GET("recettes")
    Call<List<Cooking>> ListCooking();

}
