package com.im2015.movilfacil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by Alumno on 12/03/2015.
 */
public class Contacto {


    private String nombre;
    private String numero;
    private String id;
    private Bitmap foto;

    public Contacto(String id, String nombre, String numero,Bitmap foto) {
        this.id = id;
        this.nombre = nombre;
        this.numero = numero;
        this.foto=foto;
    }


    public Contacto(String nombre, String numero, String id) {
        this.nombre = nombre;
        this.numero = numero;
        this.id = id;
        this.foto=null;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getFoto() {
        return foto;
    }
   /* public Bitmap getFotoBitmap() {

        if(foto!=null){
            return BitmapFactory.decodeByteArray(this.foto, 0, this.foto.length);
        }else{
            return null;
        }

    }*/

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

}
