package com.im2015.movilfacil;

import android.content.ContentResolver;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class editarContacto extends ActionBarActivity {
    ContentResolver cr;
    Contacto contacto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contacto);
        cr = getContentResolver();
        //cr.acquireContentProviderClient(""); Ni idea de esto
        Bundle args = getIntent().getExtras();
        String id = args.getString("id");
        String nombre = args.getString("nombre");
        String numero = args.getString("numero");
        contacto = new Contacto(id,nombre,numero);
        contacto=new Contactos(cr).getContactoId(id);
        EditText etNombre = (EditText) editarContacto.this.findViewById(R.id.editTextNombre);
        EditText etNumero = (EditText) editarContacto.this.findViewById(R.id.editTextNumero);
        etNombre.setText(nombre);
        etNumero.setText(numero);
        Button b = (Button) findViewById(R.id.buttonGuardar);
        View.OnClickListener ol = new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                EditText etNombre = (EditText) editarContacto.this.findViewById(R.id.editTextNombre);
                EditText etNumero = (EditText) editarContacto.this.findViewById(R.id.editTextNumero);
                Contactos c = (new Contactos(cr));
                c.editarContacto(
                        contacto,
                        etNombre.getText().toString(),
                        etNumero.getText().toString()
                );
                finish();
            }
        };
        b.setOnClickListener(ol);
        Button b1 = (Button) findViewById(R.id.buttonCancelar);
        View.OnClickListener ol1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        };
        b1.setOnClickListener(ol1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar_contacto, menu);
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
        return super.onOptionsItemSelected(item);
    }
}
