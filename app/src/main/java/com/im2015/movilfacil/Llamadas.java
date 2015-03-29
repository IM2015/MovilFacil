package com.im2015.movilfacil;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 30/03/2015.
 */
public class Llamadas {
    private  ContentResolver cr;

    public Llamadas(ContentResolver cr){
        this.cr = cr;
    }
    public List<Llamada> getContactos(){
        List<Llamada> l = new ArrayList<Llamada>();

        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projection    = new String[] {CallLog.Calls.NUMBER};

        Cursor cLlamadas = cr.query(uri, projection, null, null, null);
        int indexNumber = cLlamadas.getColumnIndex(CallLog.Calls.NUMBER);
        cLlamadas.moveToFirst();
        do {
            l.add(new Llamada(cLlamadas.getString(indexNumber)));
        } while (cLlamadas.moveToNext());
        return l;
    }
}
