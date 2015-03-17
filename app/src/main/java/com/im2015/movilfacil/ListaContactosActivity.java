package com.im2015.movilfacil;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;



public class ListaContactosActivity extends ActionBarActivity {

    private ListView listView;
    private ProgressDialog progressDialog;
    private ContentResolver cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cr = getContentResolver();
        cr.acquireContentProviderClient("");
        // Display a indeterminate progress bar on title bar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_lista_contactos);
        this.listView = (ListView) findViewById(R.id.listViewContactos);
        Contactos c= new Contactos(cr);
        // Sets the data behind this ListView
        this.listView.setAdapter(new ItemAdapter(this,c.getContactos()));
/*
        // Register a callback to be invoked when an item in this AdapterView
// has been clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
// Sets the visibility of the indeterminate progress bar in the
// title
                setProgressBarIndeterminateVisibility(true);
// Show progress dialog
                progressDialog = ProgressDialog.show(ListaContactosActivity.this,
                        "ProgressDialog", "Loading!");

            }
        });*/
        //parte de fabian

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
