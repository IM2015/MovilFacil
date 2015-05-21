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
import android.widget.ImageButton;
import android.widget.TextView;


public class GestorFavoritos extends ActionBarActivity {
    private ContentResolver cr;
    private Contactos c;
    private ConfiguracionFavoritos cf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestor_favoritos);
        cr = getContentResolver();
        //cr.acquireContentProviderClient("");
        c = new Contactos(cr);
        cf= new ConfiguracionFavoritos(getApplicationContext());
        //EVENTOS botones eliminar
        ImageButton b = (ImageButton) findViewById(R.id.ibEliminar1);
        View.OnClickListener ol = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cf.getfav1()!=null){
                    cf.deleteFav1();
                    cargarDatos();
                }
            }
        };
        b.setOnClickListener(ol);
        b = (ImageButton) findViewById(R.id.ibEliminar2);
        ol = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cf.getfav2()!=null){
                    cf.deleteFav2();
                    cargarDatos();
                }
            }
        };
        b.setOnClickListener(ol);
        b = (ImageButton) findViewById(R.id.ibEliminar3);
        ol = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cf.getfav3()!=null){
                    cf.deleteFav3();
                    cargarDatos();
                }
            }
        };
        b.setOnClickListener(ol);
        //Eventos añadir
        b = (ImageButton) findViewById(R.id.ibAdd1);
        ol = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaContactosActivity.class);
                Bundle b= new Bundle();
                b.putString("NumFav","1");
                i.putExtras(b);
                startActivity(i);
            }
        };
        b.setOnClickListener(ol);
        b = (ImageButton) findViewById(R.id.ibAdd2);
        ol = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaContactosActivity.class);
                Bundle b= new Bundle();
                b.putString("NumFav","2");
                i.putExtras(b);
                startActivity(i);
            }
        };
        b.setOnClickListener(ol);
        b = (ImageButton) findViewById(R.id.ibAdd3);
        ol = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaContactosActivity.class);
                Bundle b= new Bundle();
                b.putString("NumFav","3");
                i.putExtras(b);
                startActivity(i);
            }
        };
        b.setOnClickListener(ol);
        //Eventos botones mover
        b = (ImageButton) findViewById(R.id.ibBajarFav1);
        ol = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String aux= cf.getfav1();
                cf.setFav1(cf.getfav2());
                cf.setFav2(aux);
                cargarDatos();
            }
        };
        b.setOnClickListener(ol);
        b = (ImageButton) findViewById(R.id.ibSubirFav2);
        ol = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aux= cf.getfav1();
                cf.setFav1(cf.getfav2());
                cf.setFav2(aux);
                cargarDatos();
            }
        };
        b.setOnClickListener(ol);
        b = (ImageButton) findViewById(R.id.ibBajarFav2);
        ol = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aux= cf.getfav2();
                cf.setFav2(cf.getfav3());
                cf.setFav3(aux);
                cargarDatos();
            }
        };
        b.setOnClickListener(ol);
        b = (ImageButton) findViewById(R.id.ibSubirFav3);
        ol = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aux= cf.getfav2();
                cf.setFav2(cf.getfav3());
                cf.setFav3(aux);
                cargarDatos();
            }
        };
        b.setOnClickListener(ol);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gestor_favoritos, menu);
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

    private void cargarDatos(){

        if(cf.getfav1()!=null){
            Contacto con=c.getContactoId(cf.getfav1());
            TextView tv= (TextView) GestorFavoritos.this.findViewById(R.id.tvFav1Nom);
            tv.setText(con.getNombre());
            TextView tv2= (TextView) GestorFavoritos.this.findViewById(R.id.tvFav1Tfno);
            tv2.setText(con.getNumero());
        }else{
            TextView tv= (TextView) GestorFavoritos.this.findViewById(R.id.tvFav1Nom);
            tv.setText("");
            TextView tv2= (TextView) GestorFavoritos.this.findViewById(R.id.tvFav1Tfno);
            tv2.setText("");
        }
        if(cf.getfav2()!=null){
            Contacto con=c.getContactoId(cf.getfav2());
            TextView tv= (TextView) GestorFavoritos.this.findViewById(R.id.tvFav2Nom);
            tv.setText(con.getNombre());
            TextView tv2= (TextView) GestorFavoritos.this.findViewById(R.id.tvFav2Tfno);
            tv2.setText(con.getNumero());
        }else{
            TextView tv= (TextView) GestorFavoritos.this.findViewById(R.id.tvFav2Nom);
            tv.setText("");
            TextView tv2= (TextView) GestorFavoritos.this.findViewById(R.id.tvFav2Tfno);
            tv2.setText("");
        }
        if(cf.getfav3()!=null){
            Contacto con=c.getContactoId(cf.getfav3());
            TextView tv= (TextView) GestorFavoritos.this.findViewById(R.id.tvFav3Nom);
            tv.setText(con.getNombre());
            TextView tv2= (TextView) GestorFavoritos.this.findViewById(R.id.tvFav3Tfno);
            tv2.setText(con.getNumero());
        }else{
            TextView tv= (TextView) GestorFavoritos.this.findViewById(R.id.tvFav3Nom);
            tv.setText("");
            TextView tv2= (TextView) GestorFavoritos.this.findViewById(R.id.tvFav3Tfno);
            tv2.setText("");
        }


    }
}
