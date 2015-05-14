package com.im2015.movilfacil;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private List<Contacto> lc;

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
    /*
    Eimina contacto de la lista en memoria y de la BD
     */
    public  void eliminarContacto(Contacto c) {
        String condicion = ContactsContract.Data._ID + "=?";
        String [] arg = new String[1];
        arg[0]=c.getId();
        ArrayList<ContentProviderOperation> ops =
                new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
                .withSelection(condicion, arg)
                .build());
        try {
            cr.applyBatch(ContactsContract.AUTHORITY,ops);
            //if(lc != null)lc.remove(getContactoPosicion(c.getId()));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }

    }
    public List<Contacto> getContactos(){
        List<Contacto> l = new ArrayList<Contacto>();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID};

        Cursor people = cr.query(uri, /* projection */null, null, null, null);
        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int indexId=people.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
        int indexPhoto=people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID);
            if (people.getCount() > 0) {
                people.moveToFirst();
                do {

                    if (Integer.valueOf(people.getInt(indexPhoto)) != null) {
                        l.add(new Contacto(people.getString(indexId), people.getString(indexName), people.getString(indexNumber), getFotoBitmap(people.getInt(indexPhoto))));
                    } else {
                        l.add(new Contacto(people.getString(indexId), people.getString(indexName), people.getString(indexNumber)));
                    }
                } while (people.moveToNext());
                people.close();
            }

        return l;
    }
    /*
    * input telefono
    * Contacto con ese telefono
    * null en caso de que no exista
     */
    public Contacto getContactoPorTelefono(String tel){
        if(lc == null) lc = this.getContactos();
        for(int i = 0;i<lc.size();i++){
            if(lc.get(i).getNumero().equals(tel)){
                return lc.get(i);
            }
        }
        return null;
    }
    /*
    * input telefono
    * Contacto con ese telefono
    * null en caso de que no exista
     */
    public int getContactoPosicion(String id){
        if(lc == null) lc = this.getContactos();
        for(int i = 0;i<lc.size();i++){
            if(lc.get(i).getId().equals(id)){
                return i;
            }
        }
        return -1;
    }

    public Contacto getContactoId(String id){
        if(lc == null) lc = this.getContactos();
        for(int i = 0;i<lc.size();i++){
            if(lc.get(i).getId().equals(id)){
                return lc.get(i);
            }
        }
        return null;
    }
    /*
    Editar Contacto
    input: Contacto viejo, Contacto nuevo
     */
    public void editarContacto(Contacto viejo,String nuevoNombre, String nuevoNumero){
        eliminarContacto(viejo);
        this.nuevoContacto(nuevoNombre,nuevoNumero);
    }
    private Bitmap getFotoBitmap(int imageDataRow) {
        Cursor c =  cr.query(ContactsContract.Data.CONTENT_URI, new String[] {
                ContactsContract.CommonDataKinds.Photo.PHOTO
        }, ContactsContract.Data._ID + "=?", new String[] {
                Integer.toString(imageDataRow)
        }, null);
        byte[] imageBytes = null;
        if (c != null) {
            if (c.moveToFirst()) {
                imageBytes = c.getBlob(0);
            }
            c.close();
        }

        if (imageBytes != null) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            return null;
        }
    }

}
