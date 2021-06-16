package edu.pe.idat.apprecyclerviewpokemon.common;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static MyApp instancia;

    public static MyApp getInstance(){
        return instancia;
    }

    public static Context getContext(){
        return instancia;
    }

    @Override
    public void onCreate() {
        instancia = this;
        super.onCreate();
    }
}
