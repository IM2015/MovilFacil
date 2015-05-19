package com.im2015.movilfacil;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class EnvioMensaje extends ActionBarActivity {
    private String nombre;
    private String id;
    private String numero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_mensaje);
        Button enviar = (Button) this.findViewById(R.id.btnEnviar);
        Bundle args = this.getIntent().getExtras();
        id = args.getString("id");
        nombre = args.getString("nombre");
        numero = args.getString("numero");
        TextView lblSmsTitulo = (TextView) this.findViewById(R.id.lblSmsTitulo);
        if(nombre != null || numero != null) {
            if (nombre != null) {
                lblSmsTitulo.setText(lblSmsTitulo.getText()+ " " + nombre);
            } else if (numero != null) {
                lblSmsTitulo.setText(lblSmsTitulo.getText()+ " " + numero);
            }
            enviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mensaje = ((EditText) EnvioMensaje.this.findViewById(R.id.txtSmsText)).getText().toString();
                    new Mensajes(EnvioMensaje.this.getBaseContext()).enviar(numero,mensaje);

                    finish();
                }
            });
        }else{
            finish();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_envio_mensaje, menu);
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
