package com.im2015.movilfacil;

import android.content.ContentResolver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NuevoContacto extends ActionBarActivity {
    ContentResolver cr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_contacto);
        cr = getContentResolver();
        Button b = (Button) findViewById(R.id.buttonGuardar);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               EditText etNombre = (EditText) v.findViewById(R.id.editTextNombre);
                EditText etNumero = (EditText) v.findViewById(R.id.editTextNumero);
                (new Contactos(cr)).nuevoContacto(
                        etNombre.getText().toString(),
                        etNumero.getText().toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nuevo_contacto, menu);
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
