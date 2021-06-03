package edu.pe.idat.apprecyclerviewpokemon.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.pe.idat.apprecyclerviewpokemon.R;
import edu.pe.idat.apprecyclerviewpokemon.common.Constantes;
import edu.pe.idat.apprecyclerviewpokemon.databinding.ActivityMainBinding;
import edu.pe.idat.apprecyclerviewpokemon.model.Pokemon;
import edu.pe.idat.apprecyclerviewpokemon.view.adapter.PokemonAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PokemonAdapter adapter;
    private boolean puedeCargar = false;
    private String siguienteUrl= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new PokemonAdapter(this);
        binding.rvPokemon.setLayoutManager(
                new GridLayoutManager(MainActivity.this, 3)
        );
        binding.rvPokemon.setAdapter(adapter);
        binding.rvPokemon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    int itemsVisibles = binding.rvPokemon.getLayoutManager().getChildCount();
                    int itemTotales = binding.rvPokemon.getLayoutManager().getItemCount();
                    int posicionPrimerItem = ((GridLayoutManager)
                            binding.rvPokemon.getLayoutManager()).findFirstVisibleItemPosition();
                    if(puedeCargar){
                        if(itemsVisibles + posicionPrimerItem >= itemTotales){
                            puedeCargar = false;
                            obtenerPokemones(siguienteUrl);
                        }
                    }

                }
            }
        });
        obtenerPokemones(new Constantes().URL_BASE_API);
    }

    private void obtenerPokemones(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonArrayPokemon =
                                    response.getJSONArray("results");
                            siguienteUrl = response.getString("next");
                            if(jsonArrayPokemon.length() > 0){
                                puedeCargar = true;
                                ArrayList<Pokemon> miListaPokemon =
                                        new ArrayList<>();
                                for(int i=0; i<jsonArrayPokemon.length(); i++){
                                    JSONObject jsonPokemon =
                                            jsonArrayPokemon.getJSONObject(i);
                                    Pokemon nuevoPokemon = new Pokemon(
                                            jsonPokemon.getString("name"),
                                            jsonPokemon.getString("url")
                                    );
                                    miListaPokemon.add(nuevoPokemon);
                                }
                                adapter.agregarLista(miListaPokemon);
                            }
                        }catch (JSONException je){
                            puedeCargar = false;
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        puedeCargar = false;
                    }
        });
        requestQueue.add(jsonObjectRequest);
    }
}