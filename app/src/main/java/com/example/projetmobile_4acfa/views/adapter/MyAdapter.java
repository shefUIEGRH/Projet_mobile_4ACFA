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
        public TextView txtHeader;
        public TextView txtFooter;
        public View layout;
        public ImageView image;


        //Constructeur
        public CelluleJava(View v) {
            super(v);
            layout = v;
            image = (ImageView) v.findViewById(R.id.img);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);

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
    public void onBindViewHolder(CelluleJava holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Cooking currentRecette = recettes.get(position);
        final String name = currentRecette.getName();
        final String categorie = currentRecette.getCategory();

        holder.itemView.getContext();
        Picasso.get().load(currentRecette.getImg()).into(holder.image); //.with(Context)

        holder.txtHeader.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(currentRecette);
            }

        });
        holder.txtFooter.setText("Catégorie : " + categorie);

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
