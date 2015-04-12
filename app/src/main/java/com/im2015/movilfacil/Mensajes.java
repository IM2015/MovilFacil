package com.im2015.movilfacil;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Carlos on 12/04/2015.
 */
public class Mensajes {
    ContentResolver cr;

    public Mensajes(ContentResolver cr) {
        this.cr = cr;
    }
    public List<Mensaje> getMensajes(){
        List<Mensaje> l = new ArrayList<Mensaje>();
        if(android.os.Build.VERSION.SDK_INT >= 19) {
            Uri uri = Telephony.Sms.CONTENT_URI;
            String[] projection = new String[]{Telephony.Sms.ADDRESS, Telephony.Sms.DATE, Telephony.Sms.BODY, Telephony.Sms.TYPE};

            Cursor cMensajes = cr.query(uri, projection, null, null, Telephony.Sms.DATE + " DESC");
            int indexNumber = cMensajes.getColumnIndex(Telephony.Sms.ADDRESS);
            int indexDate = cMensajes.getColumnIndex(Telephony.Sms.DATE);
            int indexBody = cMensajes.getColumnIndex(Telephony.Sms.BODY);
            int indexType= cMensajes.getColumnIndex(Telephony.Sms.TYPE);

            cMensajes.moveToFirst();
            do {
                String number = cMensajes.getString(indexNumber);
                Date date = new Date(cMensajes.getLong(indexDate));
                String body = cMensajes.getString(indexBody);
                int type = cMensajes.getInt(indexType);
                l.add(new Mensaje(body,number,type,date));
            } while (cMensajes.moveToNext());
        }else{

        }
        return l;
    }
}
