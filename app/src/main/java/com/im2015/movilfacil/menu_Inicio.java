package com.im2015.movilfacil;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.BatteryManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class menu_Inicio extends ActionBarActivity {
    private RecividorSMS recividorSMS;
    private FragmentManager fm = getSupportFragmentManager();
    TextView tvBateria;
    ProgressBar pbBateria;
    private ContentResolver cr;
    private Contactos c;
    private ConfiguracionFavoritos cf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.recividorSMS = new RecividorSMS();
        setContentView(R.layout.activity_menu__inicio);
        //Favoritos
        cr = getContentResolver();
        c = new Contactos(cr);
        cf= new ConfiguracionFavoritos(getApplicationContext());

        //Botones favoritos
        ImageButton ib=null;
        ib= (ImageButton)menu_Inicio.this.findViewById(R.id.ibFav1);
        ib.setAdjustViewBounds(true);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(cf.getfav1()!=null) {
                    PopUpFavoritos Popup1 = new PopUpFavoritos();
                    Bundle b = new Bundle();
                    b.putString("id", cf.getfav1());
                    Popup1.setArguments(b);
                    Popup1.show(fm, "Dialog Fragment");
                }else{

                AlertDialog ad= new AlertDialog.Builder(menu_Inicio.this).create();

                ad.setButton("Gestionar Favoritos", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i =  new Intent(menu_Inicio.this,GestorFavoritos.class);
                        startActivity(i);
                    }
                });

                ad.show();

            }
            }
        });
        ib= (ImageButton)menu_Inicio.this.findViewById(R.id.ibFav2);
        ib.setAdjustViewBounds(true);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(cf.getfav2()!=null) {
                    PopUpFavoritos Popup1 = new PopUpFavoritos();
                    Bundle b = new Bundle();
                    b.putString("id", cf.getfav2());
                    Popup1.setArguments(b);
                    Popup1.show(fm, "Dialog Fragment");
                }else{

                    AlertDialog ad= new AlertDialog.Builder(menu_Inicio.this).create();
                    ad.setButton("Gestionar Favoritos", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i =  new Intent(menu_Inicio.this,GestorFavoritos.class);
                            startActivity(i);
                        }
                    });
                    ad.show();

                }

            }
        });
        ib= (ImageButton)menu_Inicio.this.findViewById(R.id.ibFav3);
        ib.setAdjustViewBounds(true);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(cf.getfav3()!=null) {
                    PopUpFavoritos Popup1 = new PopUpFavoritos();
                    Bundle b = new Bundle();
                    b.putString("id", cf.getfav3());
                    Popup1.setArguments(b);
                    Popup1.show(fm, "Dialog Fragment");
                }else{

                    AlertDialog ad= new AlertDialog.Builder(menu_Inicio.this).create();

                    ad.setButton(getString(R.string.GestionarFavoritos), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        Intent i =  new Intent(menu_Inicio.this,GestorFavoritos.class);
                        startActivity(i);
                        }
                    });
                    ad.show();
                    }
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
                Intent intent = new Intent(v.getContext(),MenssageBox.class);
                startActivityForResult(intent,0);
            }
        });

        //Favoritos
        cargarDatosFavoritos();

        //BATERIA
        tvBateria= (TextView) findViewById(R.id.tvBateria);
        pbBateria= (ProgressBar) findViewById(R.id.pbBateria);
        int carga= cargaBateria();
        tvBateria.setText(getString(R.string.carga) +
                String.valueOf(carga) + "%");
        pbBateria.setProgress((int)(carga * 100 / 100));

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosFavoritos();
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
//            return true;
//
//            int i;
//            i=0;
            Intent mainIntent =new Intent(android.provider.Settings.ACTION_SETTINGS);

            startActivity(mainIntent);
        }
        if (id == R.id.action_Apps){
            //final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            //mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
           // startActivity(mainIntent);
            Intent i = new Intent(getBaseContext(),AppListActivity.class);
            startActivity(i);
//            List<ResolveInfo> ril = getPackageManager().queryIntentActivities(mainIntent, 0);
//            List<String> componentList = new ArrayList<String>();
//            String name = null;
//
//            for (ResolveInfo ri : ril) {
//                if (ri.activityInfo != null) {
//                    Resources res = null;
//                    try {
//                        res = getPackageManager().getResourcesForApplication(ri.activityInfo.applicationInfo);
//                    } catch (PackageManager.NameNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    if (ri.activityInfo.labelRes != 0) {
//                        //ActivityInfo ai = ri.activityInfo;
//                       // name = res.getString(ri.activityInfo.labelRes)+" ";
//                        //name = ai.processName;
//                        //Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(name);
//                        //startActivity( LaunchIntent );
//                    } else {
//                        name = ri.activityInfo.applicationInfo.loadLabel(
//                                getPackageManager()).toString();
//                    }
//                    componentList.add(name);
//                }
//            }
//            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(name);
//            startActivity( LaunchIntent );
        }
        if(id == R.id.ayuda){
            Bundle b = new Bundle();
            b.putString("lugar","#inicio");
            Intent i = new Intent(getBaseContext(),ActivityAyuda.class);
            i.putExtras(b);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public RecividorSMS getRecividorSMS() {
        return recividorSMS;
    }
    public int cargaBateria ()    {
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
    private void cargarDatosFavoritos(){
        Contacto con=null;
        ImageButton ib=null;
        TextView tv=null;
        //Fav1
        tv= (TextView) menu_Inicio.this.findViewById(R.id.tvFav1);
        ib= (ImageButton) menu_Inicio.this.findViewById(R.id.ibFav1);
        if(cf.getfav1()!=null){
            con=c.getContactoId(cf.getfav1());
            if(con==null){
                cf.deleteFav1();
                tv.setText("");
                ib.setImageResource(R.drawable.user168);
            }else{


            tv.setText(con.getNombre());

            Bitmap foto= con.getFoto();
            if(foto!=null){
                ib.setImageBitmap(foto);
            }else{
                ib.setImageResource(R.drawable.user168);
            }
            }
        }else{

            tv.setText("");
            ib.setImageResource(R.drawable.user168);
        }
        //Fav2
        tv= (TextView) menu_Inicio.this.findViewById(R.id.tvFav2);
        ib= (ImageButton) menu_Inicio.this.findViewById(R.id.ibFav2);
        if(cf.getfav2()!=null){
            con=c.getContactoId(cf.getfav2());
            if(con==null){
                cf.deleteFav2();
                tv.setText("");
                ib.setImageResource(R.drawable.user168);
            }else {
                tv.setText(con.getNombre());

                Bitmap foto = con.getFoto();
                if (foto != null) {
                    ib.setImageBitmap(foto);
                } else {
                    ib.setImageResource(R.drawable.user168);
                }
            }
        }else{

            tv.setText("");
            ib.setImageResource(R.drawable.user168);
        }
        //Fav3
        tv= (TextView) menu_Inicio.this.findViewById(R.id.tvFav3);
        ib= (ImageButton) menu_Inicio.this.findViewById(R.id.ibFav3);
        if(cf.getfav3()!=null){
            con=c.getContactoId(cf.getfav3());
            if(con==null){
                cf.deleteFav3();
                tv.setText("");
                ib.setImageResource(R.drawable.user168);
            }else {
                tv.setText(con.getNombre());

                Bitmap foto = con.getFoto();
                if (foto != null) {
                    ib.setImageBitmap(foto);
                } else {
                    ib.setImageResource(R.drawable.user168);
                }
            }
        }else{

            tv.setText("");
            ib.setImageResource(R.drawable.user168);
        }

    }

}
