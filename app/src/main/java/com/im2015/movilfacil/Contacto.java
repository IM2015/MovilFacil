package com.im2015.movilfacil;

/**
 * Created by Alumno on 12/03/2015.
 */
public class Contacto {


    private String nombre;



    private String numero;


    public Contacto(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public Contacto() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
