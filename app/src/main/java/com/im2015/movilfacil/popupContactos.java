package com.im2015.movilfacil;

/**
 * Created by Fabiman on 3/12/2015.
 */

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class popupContactos extends DialogFragment {
    private static String[] titulos = null;
    private Contacto contacto;
    /**
     * Constantes para identificar la acci�n realizada (tomar una fotograf�a
     * o bien seleccionarla de la galer�a)
     */
    private static int TAKE_PICTURE = 1;
    private static int SELECT_PICTURE = 2;
    /**
     * Variable que define el nombre para el archivo donde escribiremos
     * la fotograf�a de tama�o completo al tomarla.
     */
    private String name = "";
    private Activity activ;
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
        if(titulos == null) {
            titulos = new String[6];
            titulos[0] = this.getString(R.string.llamar);
            titulos[1] = this.getString(R.string.mensaje);
            titulos[2] = this.getString(R.string.editar);
            titulos[3] = this.getString(R.string.eliminar);
            titulos[4]=this.getString(R.string.SeleccionarFoto);
            titulos[5]=this.getString(R.string.HacerFoto);
        }
        Bundle args = this.getArguments();
         String id = args.getString("id");
         String nombre = args.getString("nombre");
         String numero = args.getString("numero");
        this.contacto = new Contacto(id,nombre,numero);
        name = Environment.getExternalStorageDirectory() + "/test.jpg";
        activ=this.getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(titulos, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent i;
                        Bundle b;
                        int code = TAKE_PICTURE;
                        switch (which) {
                            case 0:
                                new IntentManager(getActivity()).llamar(contacto.getNumero());
                                break;
                            case 1:
                                i = new Intent(getActivity(),EnvioMensaje.class);

                                b = new Bundle();

                                b.putString("id",contacto.getId());
                                b.putString("nombre",contacto.getNombre());
                                b.putString("numero",contacto.getNumero());
                                //editarContacto ec = new editarContacto();
                                i.putExtras(b);
                                startActivity(i);
                                break;
                            case 2:
                                i = new Intent(getActivity(),editarContacto.class);

                                b = new Bundle();

                                b.putString("id",contacto.getId());
                                b.putString("nombre",contacto.getNombre());
                                b.putString("numero",contacto.getNumero());
                                //editarContacto ec = new editarContacto();
                                i.putExtras(b);
                                startActivity(i);
                                break;
                            case 3:
                                Context context = getActivity().getApplicationContext();
                                Contactos contactos = new Contactos(context.getContentResolver());
                                contactos.eliminarContacto(contacto);

                                break;
                            case 4:
                                i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                code = SELECT_PICTURE;
                                activ.startActivityForResult(i, code);
                                break;
                            case 5:
                                i =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                code = TAKE_PICTURE;
//                                Uri output = Uri.fromFile(new File(name));
//                                i.putExtra(MediaStore.EXTRA_OUTPUT, output);
                                activ.startActivityForResult(i, code);
                                break;
                        }

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * Funci�n que se ejecuta cuando concluye el intent en el que se solicita una imagen
     * ya sea de la c�mara o de la galer�a
     */
    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * Se revisa si la imagen viene de la c�mara (TAKE_PICTURE) o de la galer�a (SELECT_PICTURE)
         */

        if (requestCode == TAKE_PICTURE) {
            /**
             * Si se reciben datos en el intent tenemos una vista previa (thumbnail)
             */
            Bitmap imagen=null;
            if (data != null) {
                /**
                 * En el caso de una vista previa, obtenemos el extra �data� del intent y
                 * lo mostramos en el ImageView
                 */
                if (data.hasExtra("data")) {
                    imagen=(Bitmap) data.getParcelableExtra("data");
                }
                /**
                 * De lo contrario es una imagen completa
                 */
            } else {
                /**
                 * A partir del nombre del archivo ya definido lo buscamos y creamos el bitmap
                 * para el ImageView
                 */
                imagen=BitmapFactory.decodeFile(name);
                /**
                 * Para guardar la imagen en la galer�a, utilizamos una conexi�n a un MediaScanner
                 */
                new MediaScannerConnection.MediaScannerConnectionClient() {
                    private MediaScannerConnection msc = null; {
                        msc = new MediaScannerConnection(activ.getApplicationContext(), this); msc.connect();
                    }
                    public void onMediaScannerConnected() {
                        msc.scanFile(name, null);
                    }
                    public void onScanCompleted(String path, Uri uri) {
                        msc.disconnect();
                    }
                };
            }


            //Modificar el contacto
            Contactos c = new Contactos(activ.getContentResolver());
            c.editarContacto(
                    contacto,
                    contacto.getNombre(),
                    contacto.getNumero(),
                    imagen
            );
            /**
             * Recibimos el URI de la imagen y construimos un Bitmap a partir de un stream de Bytes
             */
        } else if (requestCode ==  SELECT_PICTURE){
            Uri selectedImage = data.getData();
            InputStream is;
            try {
                is = activ.getContentResolver().openInputStream(selectedImage);
                BufferedInputStream bis = new BufferedInputStream(is);
                Bitmap imagen = BitmapFactory.decodeStream(bis);
                //Modificar el contacto
                Contactos c = new Contactos(activ.getContentResolver());
                c.editarContacto(
                        contacto,
                        contacto.getNombre(),
                        contacto.getNumero(),
                        imagen
                );

            } catch (FileNotFoundException e) {}
        }
    }


}
