package com.example.projetmobile_4acfa.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetmobile_4acfa.R;
import com.example.projetmobile_4acfa.model.Cooking;
import com.example.projetmobile_4acfa.views.adapter.MyAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EntreeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entree, container, false);

        recyclerView = v.findViewById(R.id.recyclerview_cooking);

        Type list = new TypeToken<List<Cooking>>(){}.getType();
        String jsonList =  getArguments().getString("gson_list");
        List<Cooking> recette = new Gson().fromJson(jsonList, list);
        showList(recette);
        return v;
        }


    public void showList(List<Cooking> input){
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(input, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Cooking item) {

            }
        });
        recyclerView.setAdapter(mAdapter);
    }
}


