package edu.pe.idat.apprecyclerviewpokemon.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;

import edu.pe.idat.apprecyclerviewpokemon.R;
import edu.pe.idat.apprecyclerviewpokemon.common.Constantes;
import edu.pe.idat.apprecyclerviewpokemon.databinding.ActivityPokemonBinding;
import edu.pe.idat.apprecyclerviewpokemon.model.Pokemon;

public class PokemonActivity extends AppCompatActivity {

    private ActivityPokemonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPokemonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(getIntent().hasExtra("pokemon")){
            Pokemon objPokemon = getIntent().getParcelableExtra("pokemon");
            binding.tvNomPokemonDetalle.setText(objPokemon.getNombre());
            Glide.with(getApplicationContext())
                    .load(new Constantes().URL_IMG_API+objPokemon.getId()+".png")
                    .into(binding.ivPokemonDetalle);
        }
    }
}