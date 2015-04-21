package com.im2015.movilfacil;

import java.util.Date;

/**
 * Created by Carlos on 12/04/2015.
 */
public class Mensaje {
    String texto;
    String telefono;
    int tipo;
    Date date;

    public Mensaje(String texto, String telefono, int tipo, Date date) {
        this.texto = texto;
        this.telefono = telefono;
        this.tipo = tipo;
        this.date = date;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
