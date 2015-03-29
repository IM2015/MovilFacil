package com.im2015.movilfacil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Carlos on 30/03/2015.
 */
public class ActionContacto {
    private Activity a;
    public ActionContacto(Activity act){
        a = act;
    }
    public void llamar(String telefono){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+telefono));
        a.startActivity(callIntent);
    }
}
