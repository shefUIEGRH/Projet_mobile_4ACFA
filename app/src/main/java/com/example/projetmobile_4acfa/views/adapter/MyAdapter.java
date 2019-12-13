package com.example.projetmobile_4acfa.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetmobile_4acfa.R;
import com.example.projetmobile_4acfa.model.Cooking;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CelluleJava> implements Filterable {

    private List<Cooking> recettes;
    private final OnItemClickListener listener;
    private List<Cooking> listcookingfilter;
    private boolean showContent = false;



    public interface OnItemClickListener {
        void onItemClick(Cooking item);
    }

    //constructeur //
    public MyAdapter (List<Cooking> items, OnItemClickListener listener){
    this.recettes = items;
    this.listener = listener;
    this.listcookingfilter = new ArrayList<>(recettes);
    }

    @Override
    public CelluleJava onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        View vbis = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CelluleJava vh = new CelluleJava(v);
        return vh;
    }

    public class CelluleJava extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader, txtFooter, txtPers, txtDiff, txtTps;
        public View layout, divider;
        public ImageView image;


        //Constructeur
        public CelluleJava(View v) {
            super(v);
            layout = v;
            divider = (View) v.findViewById(R.id.divider);
            image = (ImageView) v.findViewById(R.id.img);
            txtHeader = (TextView) v.findViewById(R.id.title);
            txtFooter = (TextView) v.findViewById(R.id.categorie);
            txtPers = (TextView) v.findViewById(R.id.personne);
            txtDiff = (TextView) v.findViewById(R.id.diff);
            txtTps = (TextView) v.findViewById(R.id.tps);

        }

    }

    public void add(int position, Cooking item) {
        recettes.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(int position) {
        recettes.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(final CelluleJava holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Cooking currentRecette = recettes.get(position);
        final String name = currentRecette.getName();
        final String categorie = currentRecette.getCategory();
        final String personne = currentRecette.getPerson();
        final String difficulte = currentRecette.getDiff();
        final String temps = currentRecette.getTime();

        holder.itemView.getContext();
        Picasso.get().load(currentRecette.getImg()).into(holder.image); //.with(Context)

        holder.txtHeader.setText(name);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContent =!showContent;
                if(showContent == true) {
                    holder.txtFooter.setVisibility(View.VISIBLE);
                    holder.txtPers.setVisibility(View.VISIBLE);
                    holder.txtDiff.setVisibility(View.VISIBLE);
                    holder.txtTps.setVisibility(View.VISIBLE);
                    holder.divider.setVisibility(View.VISIBLE);
                }
                else{
                    holder.txtFooter.setVisibility(View.GONE);
                    holder.txtPers.setVisibility(View.GONE);
                    holder.txtDiff.setVisibility(View.GONE);
                    holder.txtTps.setVisibility(View.GONE);
                    holder.divider.setVisibility(View.GONE);
                }


            }
        });
        holder.txtFooter.setText("Catégorie : " + categorie);
        holder.txtPers.setText("Personnes : " + personne);
        holder.txtDiff.setText("Difficulté : " + difficulte);
        holder.txtTps.setText("Temps de préparation : " + temps);

    }

    @Override
    public int getItemCount() {
        return recettes.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Cooking> listFilter = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                listFilter.addAll(listcookingfilter);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Cooking recette : listcookingfilter){
                    if (recette.getName().toLowerCase().contains(filterPattern)){
                      listFilter.add((Cooking) recette);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = listFilter;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            recettes.clear();
            recettes.addAll((List)results.values);

            notifyDataSetChanged();

        }
    };

}
