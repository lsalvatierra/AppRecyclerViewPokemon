package edu.pe.idat.apprecyclerviewpokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.pe.idat.apprecyclerviewpokemon.common.Constantes;
import edu.pe.idat.apprecyclerviewpokemon.common.SharedPreferencesManager;
import edu.pe.idat.apprecyclerviewpokemon.databinding.ActivityLoginBinding;
import edu.pe.idat.apprecyclerviewpokemon.view.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        validarDatos();

        binding.btnlogin.setOnClickListener(view ->{
            if(binding.etusuario.getText().toString().equals("") &&
                    binding.etpassword.getText().toString().equals("")){
                mensajeUsuario(getString(R.string.validusuariopass));
            }else{
                autenticacion();
            }
        });

    }

    private void validarDatos(){
        if(!SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID).equals("")){
            startActivity(new Intent(LoginActivity.this,
                    MainActivity.class));
        }
    }


    private void autenticacion() {
        RequestQueue colaPeticiones= Volley.newRequestQueue(this);
        binding.btnlogin.setVisibility(View.GONE);
        binding.pblogin.setVisibility(View.VISIBLE);
        Map<String, String> parametros = new HashMap<>();
        parametros.put("usuario", binding.etusuario.getText().toString());
        parametros.put("password", binding.etpassword.getText().toString());
        JSONObject jsonObjectParametro = new JSONObject(parametros);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Constantes.URL_LOGIN_API,
                jsonObjectParametro,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if(response.getBoolean("rpta")){
                                SharedPreferencesManager.setSomeStringValue(
                                        Constantes.PREF_ID, response.getString("nombres"));
                                startActivity(new Intent(LoginActivity.this,
                                        MainActivity.class));
                            }else{
                                mensajeUsuario(response.getString("mensaje"));
                            }
                            binding.btnlogin.setVisibility(View.VISIBLE);
                            binding.pblogin.setVisibility(View.GONE);
                        }catch (JSONException ex){
                            mensajeUsuario(getString(R.string.errapirest));
                            binding.btnlogin.setVisibility(View.VISIBLE);
                            binding.pblogin.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mensajeUsuario(getString(R.string.errapirest));
                        binding.btnlogin.setVisibility(View.VISIBLE);
                        binding.pblogin.setVisibility(View.GONE);
                    }
                }
        );
        colaPeticiones.add(request);
    }

    private void mensajeUsuario(String mensaje){
        Toast.makeText(getApplicationContext(),
                mensaje, Toast.LENGTH_LONG).show();
    }
}