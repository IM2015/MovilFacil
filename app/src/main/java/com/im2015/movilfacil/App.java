package com.im2015.movilfacil;

import android.content.Intent;

/**
 * Created by Carlos on 26/05/2015.
 */
public class App {
    private String nombre;
    private Intent intent;

    public App(Intent intent, String nombre) {
        this.intent = intent;
        this.nombre = nombre;
    }

    public App(String name) {
        this.nombre=name;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
