package com.im2015.movilfacil;

import java.util.Date;

/**
 * Created by Carlos on 30/03/2015.
 */
public class Llamada {


    private Date dateLlamada;
    private String telefono;
    private int duracionLlamada;
    private int tipoLlamada;

    public Llamada(String tel, Date date, int duration, int type) {
        telefono = tel;
        setDateLlamada(date);
        setDuracionLlamada(duration);
        setTipoLlamada(type);
    }
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getDateLlamada() {
        return dateLlamada;
    }

    public void setDateLlamada(Date dateLlamada) {
        this.dateLlamada = dateLlamada;
    }

    public int getDuracionLlamada() {
        return duracionLlamada;
    }

    public void setDuracionLlamada(int duracionLlamada) {
        this.duracionLlamada = duracionLlamada;
    }

    public int getTipoLlamada() {
        return tipoLlamada;
    }

    public void setTipoLlamada(int tipoLlamada) {
        this.tipoLlamada = tipoLlamada;
    }
}
