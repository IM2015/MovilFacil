package com.im2015.movilfacil;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 08/03/2015.
 */
public class Contactos {
    ContentResolver cr;
    public Contactos(ContentResolver contentResolver){
        cr=contentResolver;
    }
    public  void nuevoContacto(String nombre, String numero) {
        ArrayList<ContentProviderOperation> comandosAgregar = new ArrayList<ContentProviderOperation>();
        comandosAgregar.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build()
        );
        comandosAgregar.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, nombre )
                        .build()
        );
        comandosAgregar.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, numero)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                        .build()
        );

        try {
            cr.applyBatch(ContactsContract.AUTHORITY, comandosAgregar);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }
    public  void eliminarContacto(Contacto c) {
        String condicion = ContactsContract.CommonDataKinds.Phone._ID + " = ?";
        String [] arg = new String[1];
        arg[0]=c.getId();
        Log.v("eliminados", cr.delete(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                condicion,
                arg)+"");

    }
    public List<Contacto> getContactos(){
        List<Contacto> l = new ArrayList<Contacto>();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone._ID};

        Cursor people = cr.query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int indexId=people.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
        people.moveToFirst();
        do {
            l.add(new Contacto(people.getString(indexId),people.getString(indexName),people.getString(indexNumber)));
            // Do work...
        } while (people.moveToNext());
        return l;
    }
}
