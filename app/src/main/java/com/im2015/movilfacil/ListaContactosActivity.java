package com.im2015.movilfacil;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
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

    private EditText mSearchEt;
    private MenuItem mSearchAction;
    private boolean mSearchOpened;
    popupContactos Popup1;
    private List<Contacto> listaDeRepuesto;
    private final Context context = this;
    @Override
    protected void onResume(){
        super.onResume();

        c = new Contactos(getContentResolver());

        this.listView = (ListView) findViewById(R.id.listViewContactos);
        listaDeRepuesto = new ArrayList<Contacto>();
        this.list =c.getContactos() ;
        this.listView.setAdapter(new ItemAdapter(this,list));
        /*mSearchOpened=false;

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
        });*/



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cr = getContentResolver();
        c = new Contactos(cr);
        cr.acquireContentProviderClient("");
        setContentView(R.layout.activity_lista_contactos);
        listaDeRepuesto = new ArrayList<Contacto>();
        this.list = c.getContactos();
        this.listView = (ListView) findViewById(R.id.listViewContactos);
        cf= new ConfiguracionFavoritos(getApplicationContext());
        c = new Contactos(cr);
        this.listView.setAdapter(new ItemAdapter(this,list));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
                Contacto c = (Contacto) adapter.getItemAtPosition(position);
                //Comprobamos si estamos añadiendo un favorito
                Bundle args = getIntent().getExtras();
                String numFav=null;
                if(args!=null){  numFav = args.getString("NumFav");}

                if (numFav != null) {
                    Intent i = new Intent(getApplicationContext(), GestorFavoritos.class);
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

                } else {
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
    private void openSearchBar(String queryText) {

        // Set custom view on action bar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.buscar_contactos);

        // Search edit text field setup.
        final EditText mSearchEt = (EditText) actionBar.getCustomView().findViewById(R.id.etSearch);
        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                String nombreUpper = mSearchEt.getText().toString().toUpperCase();
                    int i = 0;
                    while(i < list.size()){
                        Contacto co = list.get(i);
                        if (!co.getNombre().toUpperCase().contains(nombreUpper) && !co.getNumero().contains(nombreUpper))
                            listaDeRepuesto.add(list.remove(i));
                        else
                            i++;
                    }
                    i = 0;
                    while(i < listaDeRepuesto.size()) {
                        Contacto co = listaDeRepuesto.get(i);
                        if (co.getNombre().toUpperCase().contains(nombreUpper) || co.getNumero().contains(nombreUpper))
                            list.add(listaDeRepuesto.remove(i));
                        else{
                            i++;
                        }
                    }
                    listView.setAdapter(new ItemAdapter(context, list));
                }

        });
        //mSearchEt.addTextChangedListener(new SearchWatcher());
        mSearchEt.setText(queryText);
        mSearchEt.requestFocus();

        // Change search icon accordingly.


    }

    private void closeSearchBar() {

        // Remove custom view.
        getSupportActionBar().setDisplayShowCustomEnabled(false);

        // Change search icon accordingly.


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_contactos, menu);
       // mSearchEt = (EditText) getSupportActionBar().getCustomView()
              //  .findViewById(R.id.search);

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
        if(id == R.id.actionHome ){
            Intent i = new Intent(getBaseContext(),menu_Inicio.class);
            startActivity(i);
        }

        if (id == R.id.search) {
            if (mSearchOpened) {
                closeSearchBar();
            } else {
                openSearchBar("");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            Popup1.onActivityResult(requestCode, resultCode, data);

    }
}
