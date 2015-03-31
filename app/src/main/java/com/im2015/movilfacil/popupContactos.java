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
            "Eliminar",
            "Añadir a favoritos"
    };
    private Contacto contacto;

    static popupContactos newInstance(String idContacto){
        popupContactos vEmergente = new popupContactos();
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
         String nombre = args.getString("nombre");
         String numero = args.getString("numero");
        this.contacto = new Contacto(id,nombre,numero);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_contacts_title)
                .setItems(titulos, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                new IntentManager(getActivity()).llamar(contacto.getNumero());
                                break;
                            case 3:
                                Context context = getActivity().getApplicationContext();
                                Contactos contactos = new Contactos(context.getContentResolver());
                                contactos.eliminarContacto(contacto);
                                break;
                            case 4:
                                PopUpFavoritos pfavs = new PopUpFavoritos();
                                pfavs.show(getFragmentManager(), "PopUpFavs");
                                break;
                        }

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
