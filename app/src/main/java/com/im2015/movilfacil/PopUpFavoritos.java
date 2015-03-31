package com.im2015.movilfacil;

import android.app.*;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by PC on 31/03/2015.
 */

public class PopUpFavoritos extends DialogFragment {
    ConfiguracionFavoritos conf= new ConfiguracionFavoritos(this.getActivity());
    static PopUpFavoritos newInstance(String idContacto){
        PopUpFavoritos vEmergente = new PopUpFavoritos();
        Bundle args = new Bundle();
        args.putString("idContacto", idContacto);
        vEmergente.setArguments(args);
        return vEmergente;
    }

    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Selecciona qué favorito quieres modificar")
                .setItem
                .setItems(titulos, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.layout_popup_favoritos,container);
        //Buscamos el primer favorito y si está lo rellenamos
        TextView tvf1= (TextView)vista.findViewById(R.id.textViewFav1);
        if(conf.getfav1()!=null){
            tvf1.setText("id fav1:" + conf.getfav1());
        }else{tvf1.setText("Favorito1 vacio");}
        TextView tvf2= (TextView)vista.findViewById(R.id.textViewFav2);
        if(conf.getfav2()!=null){
            tvf2.setText("id fav2:" + conf.getfav2());
        }else{tvf2.setText("Favorito2 vacio");}
        TextView tvf3= (TextView)vista.findViewById(R.id.textViewFav3);
        if(conf.getfav3()!=null){
            tvf3.setText("id fav3:" + conf.getfav3());
        }else{tvf3.setText("Favorito3 vacio");}
        return vista;
    }
}
