package com.im2015.movilfacil;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class menu_Inicio extends ActionBarActivity {
    private RecividorSMS recividorSMS;
    TextView tvBateria;
    ProgressBar pbBateria;
    SeekBar sbBateria;
//public class menu_Inicio extends ActionBarActivity {
    //public FragmentManager fm = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new Contactos(getApplicationContext().getContentResolver()).eliminarContacto(new Contacto("1","1","1"));
        this.recividorSMS = new RecividorSMS();
        setContentView(R.layout.activity_menu__inicio);
        ImageButton bFavs= (ImageButton) findViewById(R.id.ibGestionFavs);
        bFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(v.getContext(),GestorFavoritos.class);
                startActivity(i);
            }
        });
        Button b = (Button) findViewById(R.id.btnContactos);
        b.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v){
                                     Intent i = new Intent(v.getContext(),ListaContactosActivity.class);
                                     startActivity(i);
                                 }
                             });

        Button btnLlamadas = (Button) findViewById(R.id.button2);
        btnLlamadas.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(v.getContext(),RegistroDeLlamadas.class);
                startActivity(i);
            }
        });




        Button btnMensajes = (Button) findViewById(R.id.button3);
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
        //BATERIA
        tvBateria= (TextView) findViewById(R.id.tvBateria);
        pbBateria= (ProgressBar) findViewById(R.id.pbBateria);
        sbBateria=(SeekBar) findViewById(R.id.sbBateria);
        int carga= cargaBateria();
        tvBateria.setText("Carga batería: " +
                String.valueOf(carga) + "%");
        pbBateria.setProgress((int)(carga * 100 / 100));
        sbBateria.setProgress((int)(carga * 100 / 100));


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

    public RecividorSMS getRecividorSMS() {
        return recividorSMS;
    }
    public int cargaBateria ()
    {
        try
        {
            IntentFilter batIntentFilter =
                    new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent battery =
                    this.registerReceiver(null, batIntentFilter);
            int nivelBateria = battery.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            return nivelBateria;
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),
                    "Error al obtener estado de la batería",
                    Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
}
