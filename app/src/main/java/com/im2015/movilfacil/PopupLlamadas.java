package com.im2015.movilfacil;

/**
 * Created by Carlos on 20/04/2015.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;



/**
 * Created by carlos on 20/04/2015.
 */

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class PopupLlamadas extends DialogFragment {
    private static String[] titulos = null;
    //private Contacto contacto;
    private String numero;
    static popupContactos newInstance(String numero){
        titulos = null;
        popupContactos vEmergente = new popupContactos();
        Bundle args = new Bundle();
        args.putString("numero", numero);
        vEmergente.setArguments(args);
        return vEmergente;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        if(titulos == null){
            titulos = new String[2];
            titulos[0] = this.getString(R.string.llamar);
            titulos[1] = this.getString(R.string.mensaje);
                //"Editar"
                //"Eliminar",
                //"Añadir a favoritos"
        }
        Bundle args = this.getArguments();
        //String id = args.getString("id");
        //String nombre = args.getString("nombre");
        numero = args.getString("numero");
        //this.contacto = new Contacto(id,nombre,numero);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(titulos, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent i;
                        Bundle b;
                        switch (which) {
                            case 0:
                                new IntentManager(getActivity()).llamar(numero);
                                break;
                            case 1:
                                i = new Intent(getActivity(),EnvioMensaje.class);

                                b = new Bundle();
                                b.putString("numero",numero);
                                //editarContacto ec = new editarContacto();
                                i.putExtras(b);
                                startActivity(i);
                                break;
                        }

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}