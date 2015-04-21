package com.im2015.movilfacil;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class menu_Inicio extends ActionBarActivity {

//public class menu_Inicio extends ActionBarActivity {
    //public FragmentManager fm = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new Contactos(getApplicationContext().getContentResolver()).eliminarContacto(new Contacto("1","1","1"));
        setContentView(R.layout.activity_menu__inicio);
        ImageButton b = (ImageButton) findViewById(R.id.btn_contacts_main);
        b.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v){
                                     Intent i = new Intent(v.getContext(),ListaContactosActivity.class);
                                     startActivity(i);
                                 }
                             });

        ImageButton btnLlamadas = (ImageButton) findViewById(R.id.btn_call_main);
        btnLlamadas.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(v.getContext(),RegistroDeLlamadas.class);
                startActivity(i);
            }
        });


        ImageButton btnMensajes = (ImageButton) findViewById(R.id.btn_message_main);
        btnMensajes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(),MenuMensajes.class);
                startActivityForResult(intent,0);
            }
        });


        /*Button btnContactos = (Button) findViewById(R.id.button);
        btnContactos.setOnClickListener(new View.OnClickListener(){
             @Override
            public void onClick(View v){
                popupContactos Popup1 = new popupContactos();
                Popup1.show(fm, "Dialog Fragment");

            }
        });*/



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu__inicio, menu);
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
