package com.example.projetmobile_4acfa.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.projetmobile_4acfa.R;
import com.example.projetmobile_4acfa.controller.MainController;
import com.example.projetmobile_4acfa.controller.MainNavigation;
import com.example.projetmobile_4acfa.model.Cooking;
import com.example.projetmobile_4acfa.views.adapter.MyAdapter;
import com.example.projetmobile_4acfa.views.fragments.EntreeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.projetmobile_4acfa.R.id;
import static com.example.projetmobile_4acfa.R.layout;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyAdapter mAdapter;
    private MainController myController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        recyclerView = findViewById(id.my_recycler_view);

        myController = new MainController(this, getSharedPreferences("key", Context.MODE_PRIVATE));
        myController.onStart();

        BottomNavigationView bottomNav = findViewById(id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EntreeFragment()).commit();

        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("l'envie de cuisiner");
       // toolbar.setLogo(R.drawable.logo);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
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

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                   Fragment fragment = null;

                    switch(menuItem.getItemId()){
                        case id.nav_entree:
                            myController.onClickEntree();
                            break;

                        case id.nav_plat:
                            myController.onClickPlat();
                            break;

                        case id.nav_dessert:
                            myController.onClickDessert();
                            break;
                    }

                    return true;
                }
            };

    public void goTo(MainNavigation mainNavigation, String listJson) {
        Fragment fragment = null;

        switch(mainNavigation) {
            case ENTREE:
            case PLAT:
            case DESSERT:
                fragment = new EntreeFragment();
                Bundle arg = new Bundle();
                arg.putString("gson_list", listJson);
                fragment.setArguments(arg);
                break;
        }
        assert fragment != null;
        getSupportFragmentManager().beginTransaction().replace(id.fragment_container, fragment).commit();
    }

    //Transmetre l'info au fragment
    // Fragment Bundle Arguments
    // Passe la liste sous le format Json car le format Json est un string
    // Utiliser Gson toJson et fromJson et vu que c'est une List il faut utiliser un TypeToken
}
