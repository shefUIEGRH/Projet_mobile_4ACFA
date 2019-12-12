package com.example.projetmobile_4acfa.controller;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.projetmobile_4acfa.interfaceAPI.RestAPI;
import com.example.projetmobile_4acfa.model.Cooking;
import com.example.projetmobile_4acfa.views.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainController {

    private MainActivity myActivity;
    private SharedPreferences pref;
    private static final String KEY_ = "key";
    private static MainController ctr = null;
    private List<Cooking> listRecette;

    // constructeur
    public MainController(MainActivity myActivity, SharedPreferences preferences) {
        this.myActivity = myActivity;
        this.pref = preferences;

    }

    public static MainController getInstance(MainActivity myActivity, SharedPreferences pref){

        if(ctr == null){
            ctr = new MainController(myActivity, pref);
        }
        return ctr;
    }

    public void onStart(){
        // si mon cache contient qqch, je vais charger ce qu'il y a dedans pour l'afficher
        if(pref.contains(KEY_)){

            String list = pref.getString(KEY_, null);
            Type listType = new TypeToken<List<Cooking>>(){
            }.getType();
            listRecette = new Gson().fromJson(list, listType);
            onClickEntree();

            //myActivity.showList(listRecette);
        }
        else{

            Gson gson = new GsonBuilder().setLenient().create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://bridge.buddyweb.fr/api/recettes/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            RestAPI recetteApi = retrofit.create(RestAPI.class);

            Call<List<Cooking>> call = recetteApi.ListCooking();
            call.enqueue(new Callback<List<Cooking>>() {
                @Override
                public void onResponse(Call<List<Cooking>> call, Response<List<Cooking>> response){
                    listRecette = response.body();

                    Gson gson = new Gson();
                    String list = gson.toJson(listRecette);

                    pref.edit().putString(KEY_, list).apply();

                    myActivity.showList(listRecette);

                }

                @Override
                public void onFailure(Call<List<Cooking>> call, Throwable t) {
                    Log.d("ERROR", "Api Error");
                }
            });

        }
    }


    public List<Cooking> filterList(List<Cooking> list, String filtre){
        List<Cooking> filtered_list = new ArrayList<>();
        for(Cooking recette: list){
            if(recette.getCategory().equalsIgnoreCase(filtre)){
                filtered_list.add(recette);
            }
        }
        return filtered_list;
    }


    public void onClickEntree() {
        List<Cooking> listEntree = filterList(listRecette, "Entree");
        myActivity.goTo(MainNavigation.ENTREE, new Gson().toJson(listEntree));
    }

    public void onClickPlat() {
        List<Cooking> listPlat = filterList(listRecette, "Plat");
        myActivity.goTo(MainNavigation.PLAT, new Gson().toJson(listPlat));
    }

    public void onClickDessert() {
        List<Cooking> listDessert = filterList(listRecette, "Dessert");
        myActivity.goTo(MainNavigation.DESSERT, new Gson().toJson(listDessert));
    }
}
