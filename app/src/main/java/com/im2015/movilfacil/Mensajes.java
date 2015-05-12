package com.im2015.movilfacil;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Carlos on 12/04/2015.
 */
public class Mensajes {
    Context cr;
    MensajeSQLOpen mso;
    //RecividorSMS rsms;
    public Mensajes(Context cr) {
        this.cr = cr;
        mso = null;
        if(android.os.Build.VERSION.SDK_INT < 19){
            mso = new MensajeSQLOpen(cr);
        }

    }
    public List<Mensaje> getMensajes(){
        List<Mensaje> l = new ArrayList<Mensaje>();
        //l.add(new Mensaje("Texto super de Prueb es largo y duro eso dijo ella","941123456", Mensaje.Tipo.recibido,new Date()));
        if(android.os.Build.VERSION.SDK_INT >= 19) {
            Uri uri = Telephony.Sms.CONTENT_URI;
            String[] projection = new String[]{Telephony.Sms.ADDRESS, Telephony.Sms.DATE, Telephony.Sms.BODY, Telephony.Sms.TYPE};

            Cursor cMensajes = cr.getContentResolver().query(uri, projection, null, null, Telephony.Sms.DATE + " DESC");
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
                if(type  == Telephony.Sms.MESSAGE_TYPE_INBOX)
                    l.add(new Mensaje(body,number,Mensaje.Tipo.recibido,date));
                if(type  == Telephony.Sms.MESSAGE_TYPE_OUTBOX)
                    l.add(new Mensaje(body,number,Mensaje.Tipo.enviado,date));
            } while (cMensajes.moveToNext());
        }else{
            String [] columns = {"numero","mensaje","fecha","tipo"};
            Cursor c = mso.getReadableDatabase().query("mensaje",columns,null,null,null,null,"fecha");
            while(c.moveToNext()){
                Mensaje.Tipo t = c.getString(c.getColumnIndex("tipo")).equals("enviado")?Mensaje.Tipo.enviado:Mensaje.Tipo.recibido;
                l.add(new Mensaje(c.getString(c.getColumnIndex("numero")),
                        c.getString(c.getColumnIndex("mensaje")),
                        t,
                        new Date(c.getLong(c.getColumnIndex("fecha"))))
                );
            }
        }
        return l;
    }

    public void enviar(String numero, String mensaje) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numero, null, mensaje, null, null);
        if(android.os.Build.VERSION.SDK_INT < 19) {
             ContentValues cv = new ContentValues();
             cv.put("numero", numero);
             cv.put("mensaje", mensaje);
             cv.put("tipo", Mensaje.Tipo.enviado.name());
             cv.put("fecha", Calendar.getInstance().getTimeInMillis());
             mso.getWritableDatabase().insert("mensaje", null, cv);
        }
    }
    /*Api < 19*/
    public void recibir(Mensaje m){
        ContentValues cv = new ContentValues();
        cv.put("numero", m.getTelefono());
        cv.put("mensaje", m.getTexto());
        cv.put("tipo", m.getTipo().name());
        cv.put("fecha", m.getDate().getTime());
        mso.getWritableDatabase().insert("mensaje", null, cv);
    }
}
