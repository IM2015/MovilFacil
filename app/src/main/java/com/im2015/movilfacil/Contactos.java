package com.im2015.movilfacil;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 08/03/2015.
 */
public class Contactos {
    ContentResolver cr;
    private static List<Contacto> lc;
    private static int  numContactos=0;

    public Contactos(ContentResolver contentResolver){
        cr=contentResolver;
    }
    public  void nuevoContacto(String nombre, String numero) {
        ArrayList<ContentProviderOperation> comandosAgregar = new ArrayList<ContentProviderOperation>();
        int rawContactID = comandosAgregar.size();
        comandosAgregar.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build()
        );
        comandosAgregar.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, nombre )
                        .build()
        );
        comandosAgregar.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, numero)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                        .build()
        );

        try {
            ContentProviderResult[] contentProviderResults = cr.applyBatch(ContactsContract.AUTHORITY, comandosAgregar);
            contentProviderResults[0].toString();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
        lc = null;
        lc = getContactos();
        numContactos = lc.size();
    }
    //Nuevo contacto con imagen
    public  void nuevoContacto(String nombre, String numero,Bitmap mBitmap) {
        ArrayList<ContentProviderOperation> comandosAgregar = new ArrayList<ContentProviderOperation>();
        int rawContactID = comandosAgregar.size();
        comandosAgregar.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build()
        );
        comandosAgregar.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, nombre )
                        .build()
        );
        comandosAgregar.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, numero)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                        .build()
        );
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if(mBitmap!=null){    // If an image is selected successfully
            mBitmap.compress(Bitmap.CompressFormat.PNG , 75, stream);

            // Adding insert operation to operations list
            // to insert Photo in the table ContactsContract.Data
            comandosAgregar.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                    .withValue(ContactsContract.Data.IS_SUPER_PRIMARY, 1)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO,stream.toByteArray())
                    .build());

            try {
                stream.flush();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            cr.applyBatch(ContactsContract.AUTHORITY, comandosAgregar);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
        lc = null;
        lc = getContactos();
        numContactos = lc.size();
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
        lc.remove(getContactoPosicion(c.getId()));
        numContactos = lc.size();

    }
    public List<Contacto> getContactos(){
        if(lc == null || lc.size()<numContactos) {
            List<Contacto> l = new ArrayList<Contacto>();

            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone._ID,
                    ContactsContract.CommonDataKinds.Phone.PHOTO_ID};

            Cursor people = cr.query(uri, /* projection */null, null, null, "upper(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");
            int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int indexId = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
            int indexPhoto = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID);
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
            lc=l;
            numContactos = lc.size();
        }
        return lc;

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
    /*
    Editar Contacto
    input: Contacto viejo, Contacto nuevo con imagen
     */
    public void editarContacto(Contacto viejo,String nuevoNombre, String nuevoNumero, Bitmap imagen){
        eliminarContacto(viejo);
        this.nuevoContacto(nuevoNombre,nuevoNumero,imagen);
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
