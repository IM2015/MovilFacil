package com.im2015.movilfacil;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;


public class ListaContactosActivity extends ActionBarActivity {

    private ListView listView;
    private ProgressDialog progressDialog;
    private ContentResolver cr;
    private FragmentManager fm = getSupportFragmentManager();
    private List<Contacto> list;
    private Contactos c;

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

                popupContactos Popup1 = new popupContactos();
                Bundle b = new Bundle();

                b.putString("id",c.getId());
                b.putString("nombre",c.getNombre());
                b.putString("numero",c.getNumero());
                Popup1.setArguments(b);
                Popup1.show(fm, "Dialog Fragment");
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

        c = new Contactos(cr);
        this.list = c.getContactos();
        this.listView.setAdapter(new ItemAdapter(this,list));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
                Contacto c = (Contacto) adapter.getItemAtPosition(position);

                popupContactos Popup1 = new popupContactos();
                Bundle b = new Bundle();

                b.putString("id",c.getId());
                b.putString("nombre",c.getNombre());
                b.putString("numero",c.getNumero());
                Popup1.setArguments(b);
                Popup1.show(fm, "Dialog Fragment");
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

        return super.onOptionsItemSelected(item);
    }
}
