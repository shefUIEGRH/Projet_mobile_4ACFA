package com.example.projetmobile_4acfa.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetmobile_4acfa.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DessertFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dessert, container, false);
    }
}


