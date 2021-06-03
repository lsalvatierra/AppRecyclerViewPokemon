package edu.pe.idat.apprecyclerviewpokemon.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import edu.pe.idat.apprecyclerviewpokemon.common.Constantes;
import edu.pe.idat.apprecyclerviewpokemon.databinding.ItemPokemonBinding;
import edu.pe.idat.apprecyclerviewpokemon.model.Pokemon;
import edu.pe.idat.apprecyclerviewpokemon.view.PokemonActivity;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    private ArrayList<Pokemon> dataPokemon;
    private Context context;

    public PokemonAdapter(Context context) {
        this.context = context;
        dataPokemon = new ArrayList<>();
    }

    @NonNull
    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPokemonBinding recyclerBinding = ItemPokemonBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(recyclerBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.ViewHolder holder, int position) {
        final Pokemon objPokemon = dataPokemon.get(position);
        holder.reclyclerBinding.tvNomPokemonItem.setText(objPokemon.getNombre());
        Glide.with(context)
                .load(new Constantes().URL_IMG_API+
                        String.valueOf(objPokemon.getId())+".png")
                .into(holder.reclyclerBinding.ivPokemonItem);
        holder.reclyclerBinding.cvContenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAndroid =
                        new Intent(context, PokemonActivity.class);
                intentAndroid.putExtra("pokemon", objPokemon);
                context.startActivity(intentAndroid);
            }
        });
    }
    public void agregarLista(ArrayList<Pokemon> listaPokemon){
        dataPokemon.addAll(listaPokemon);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return dataPokemon.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemPokemonBinding reclyclerBinding;

        public ViewHolder(@NonNull ItemPokemonBinding itemView) {
            super(itemView.getRoot());
            reclyclerBinding = itemView;
        }
    }
}
