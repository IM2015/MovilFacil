package com.im2015.movilfacil;

import android.graphics.drawable.Drawable;

/**
 * Created by Carlos on 26/05/2015.
 */
public class App {
    private final Drawable drawable;
    private String nombre;
    private String intent;

    public App(String intent, String nombre) {
        this.intent = intent;
        this.nombre = nombre;
        drawable=null;
    }


    public App(String intent, String name, Drawable d) {
        this.intent = intent;
        this.nombre = name;
        this.drawable = d;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
