package com.im2015.movilfacil;

/**
 * Created by Fabiman on 3/12/2015.
 */

import android.app.*;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_contacts_title)
                .setItems(titulos, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PopUpFavoritos pfavs= new PopUpFavoritos();
                        pfavs.show(getFragmentManager(),"PopUpFavs");
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


}