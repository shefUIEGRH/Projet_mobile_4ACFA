package com.example.projetmobile_4acfa.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.projetmobile_4acfa.R;
import com.example.projetmobile_4acfa.controller.MainController;
import com.example.projetmobile_4acfa.model.Cooking;
import com.example.projetmobile_4acfa.views.adapter.MyAdapter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private MainController myController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.my_recycler_view);

        myController = new MainController(this, getSharedPreferences("key", Context.MODE_PRIVATE));
        myController.onStart();
    }

    public void showList(List<Cooking> input){

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(input, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Cooking item) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("nom",item.getName());
                intent.putExtra("categorie", item.getCategory());
                intent.putExtra("temps", item.getTime());
                intent.putExtra("difficult", item.getDiff());
                intent.putExtra("personnes", item.getPerson());
                intent.putExtra("photo", item.getImg());
                MainActivity.this.startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);

    }
}
