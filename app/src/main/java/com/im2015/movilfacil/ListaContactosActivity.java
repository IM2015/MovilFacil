package com.im2015.movilfacil;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class ListaContactosActivity extends ActionBarActivity {

    private ListView listView;
    private ProgressDialog progressDialog;
    private ContentResolver cr;
    private FragmentManager fm = getSupportFragmentManager();
    private List<Contacto> list;
    private Contactos c;
    Contacto contacto;
    private ConfiguracionFavoritos cf;
    popupContactos Popup1;
    @Override
    protected void onResume(){
        super.onResume();
        this.listView = (ListView) findViewById(R.id.listViewContactos);
        this.list = c.getContactos();
        this.listView.setAdapter(new ItemAdapter(this,list));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
                Contacto c = (Contacto) adapter.getItemAtPosition(position);
                contacto=c;
                //Comprobamos si estamos añadiendo un favorito
                Bundle args = getIntent().getExtras();
                if(args!=null){
                    String numFav = args.getString("NumFav");
                    Intent i= new Intent(getApplicationContext(),GestorFavoritos.class);
                    switch (numFav) {
                        case "1":
                            cf.setFav1(c.getId());
                            startActivity(i);
                            break;
                        case "2":
                            cf.setFav2(c.getId());
                            startActivity(i);
                            break;
                        case "3":
                            cf.setFav3(c.getId());
                            startActivity(i);
                            break;

                    }

                }else {
                    Popup1 = new popupContactos();
                    Bundle b = new Bundle();

                    b.putString("id", c.getId());
                    b.putString("nombre", c.getNombre());
                    b.putString("numero", c.getNumero());
                    Popup1.setArguments(b);
                    Popup1.show(fm, "Dialog Fragment");
                }
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cr = getContentResolver();
        cr.acquireContentProviderClient("");
        setContentView(R.layout.activity_lista_contactos);
        this.listView = (ListView) findViewById(R.id.listViewContactos);
        cf= new ConfiguracionFavoritos(getApplicationContext());
        c = new Contactos(cr);
        this.list = c.getContactos();
        this.listView.setAdapter(new ItemAdapter(this,list));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
                Contacto c = (Contacto) adapter.getItemAtPosition(position);
                //Comprobamos si estamos añadiendo un favorito
                Bundle args = getIntent().getExtras();
                String numFav = args.getString("NumFav");
                if(numFav!=null){
                    Intent i= new Intent(getApplicationContext(),GestorFavoritos.class);
                    switch (numFav) {
                        case "1":
                            cf.setFav1(c.getId());
                            startActivity(i);
                            break;
                        case "2":
                            cf.setFav2(c.getId());
                            startActivity(i);
                            break;
                        case "3":
                            cf.setFav3(c.getId());
                            startActivity(i);
                            break;

                    }

                }else {
                    Popup1 = new popupContactos();
                    Bundle b = new Bundle();

                    b.putString("id", c.getId());
                    b.putString("nombre", c.getNombre());
                    b.putString("numero", c.getNumero());
                    Popup1.setArguments(b);
                    Popup1.show(fm, "Dialog Fragment");
                }
            }
        });

        Button addConctact =(Button) findViewById(R.id.btnAgregarContacto);
        addConctact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),NuevoContacto.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_contactos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.ayuda){
            Bundle b = new Bundle();
            b.putString("lugar","#contactos");
            Intent i = new Intent(getBaseContext(),ActivityAyuda.class);
            i.putExtras(b);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Popup1.onActivityResult(requestCode,resultCode,data);
    }
}
