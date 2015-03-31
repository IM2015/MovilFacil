package com.im2015.movilfacil;

/**
 * Created by Fabiman on 3/12/2015.
 */

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class popupContactos extends DialogFragment {
    String[] titulos = {
            "Llamar",
            "Mensaje",
            "Editar",
            "Eliminar"
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_contacts_title);
        builder.setItems(titulos , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                new IntentManager(getActivity()).llamar("941226497");
                                break;
                            case 3:
                                Context context = getActivity().getApplicationContext();
                                Contactos contactos = new Contactos(context.getContentResolver());
                                contactos.eliminarContacto(new Contacto("2","a","111"));
                                break;
                        }

                    }});
        // Create the AlertDialog object and return it
       AlertDialog al = builder.create();
       return al;
    }

}
