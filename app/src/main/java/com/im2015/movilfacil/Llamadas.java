package com.im2015.movilfacil;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Carlos on 30/03/2015.
 */
public class Llamadas {
    private  ContentResolver cr;

    public Llamadas(ContentResolver cr){
        this.cr = cr;
    }
    public List<Llamada> getLlamadas(){
        List<Llamada> l = new ArrayList<Llamada>();

        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projection    = new String[] {CallLog.Calls.NUMBER,CallLog.Calls.DATE,CallLog.Calls.DURATION,CallLog.Calls.TYPE};

        Cursor cLlamadas = cr.query(uri, projection, null, null, CallLog.Calls.DATE + " DESC");
        int indexNumber = cLlamadas.getColumnIndex(CallLog.Calls.NUMBER);
        int indexDate = cLlamadas.getColumnIndex(CallLog.Calls.DATE);
        int indexDuration = cLlamadas.getColumnIndex(CallLog.Calls.DURATION);
        int indexType= cLlamadas.getColumnIndex(CallLog.Calls.TYPE);

        cLlamadas.moveToFirst();
        do {
            String number = cLlamadas.getString(indexNumber);
            Date date = new Date(cLlamadas.getLong(indexDate));
            int duration = cLlamadas.getInt(indexDuration);
            int type = cLlamadas.getInt(indexType);
            l.add(new Llamada(number,date,duration,type));
        } while (cLlamadas.moveToNext());
        return l;
    }
}
