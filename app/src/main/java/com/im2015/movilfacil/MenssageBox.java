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


public class MenssageBox extends ActionBarActivity {
    private FragmentManager fm = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menssage_box);
        ContentResolver cr = getContentResolver();
        ListView listView = (ListView) findViewById(R.id.listViewMensajes);
        Mensajes Mensajes = new Mensajes(this.getBaseContext());
        List<Mensaje> l = Mensajes.getMensajes();
        listView.setAdapter(new SmsAdapter(this,l));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
                Bundle b = new Bundle();
                Mensaje c = (Mensaje) adapter.getItemAtPosition(position);
                b.putString("numero",c.getTelefono());
                PopupLlamadas pop = new PopupLlamadas();
                pop.setArguments(b);
                pop.show(fm, "Dialog Fragment");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menssage_box, menu);
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
            b.putString("lugar","#mensajes");
            Intent i = new Intent(getBaseContext(),ActivityAyuda.class);
            i.putExtras(b);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
