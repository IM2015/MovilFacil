package com.im2015.movilfacil;

/**
 * Created by Fabiman on 3/12/2015.
 */

import android.app.*;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;


public class PopUpFavoritos extends DialogFragment {
    String[] titulos = {
            "Llamar",
            "Enviar mensaje",
            "Gestionar Favoritos"
    };
    private Contacto contacto;
    private ContentResolver cr;
    private Contactos c;
    static PopUpFavoritos newInstance(String idContacto){
        PopUpFavoritos vEmergente = new PopUpFavoritos();
        Bundle args = new Bundle();
        args.putString("idContacto", idContacto);
        vEmergente.setArguments(args);
        return vEmergente;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Bundle args = this.getArguments();
        String id = args.getString("id");
        cr = this.getActivity().getContentResolver();
        c = new Contactos(cr);
        this.contacto = c.getContactoId(id);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Selecciona una opción");
        builder.setItems(titulos, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent i;
                        Bundle b;

                        switch (which) {
                            case 0:
                                new IntentManager(getActivity()).llamar(contacto.getNumero());
                                break;
                            case 1:
                                i = new Intent(getActivity(),EnvioMensaje.class);

                                b = new Bundle();

                                b.putString("id",contacto.getId());
                                b.putString("nombre",contacto.getNombre());
                                b.putString("numero",contacto.getNumero());
                                //editarContacto ec = new editarContacto();
                                i.putExtras(b);
                                startActivity(i);
                                break;
                            case 2:
                                i =  new Intent(getActivity(),GestorFavoritos.class);
                                startActivity(i);
                                break;


                        }

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
