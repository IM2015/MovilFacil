package com.im2015.movilfacil;

/**
 * Created by Carlos on 31/03/2015.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SmsAdapter extends BaseAdapter {
    private Context context;
    private List<Mensaje> items;
    private Contactos contactos;
    //private RecividorSMS recibidorSMS;
    public SmsAdapter (Context context, List<Mensaje> items) {
        this.context = context;
        this.items = items;
        contactos = new Contactos(context.getContentResolver());
        //recibidorSMS = null;
        /*if(android.os.Build.VERSION.SDK_INT < 19){

        }*/
    }
    @Override
    public int getCount() {
        return this.items.size();
    }
    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (convertView == null) {
// Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.llamada_item, parent, false);
        }
// Set data into the view.
        ImageView ivItem = (ImageView) rowView.findViewById(R.id.ivImagen);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvNombre);
        TextView tvTfno= (TextView) rowView.findViewById(R.id.tvTfno);
        TextView tvDate = (TextView) rowView.findViewById(R.id.tvDate);
        Mensaje item = this.items.get(position);
        Contacto c = contactos.getContactoPorTelefono(item.getTelefono());//contactos.getContactoPorTelefono(item.getTelefono());
        if(c == null){
            tvTitle.setText(item.getTelefono());
        }else{
            tvTitle.setText(c.getNombre());
            tvTfno.setText(item.getTelefono());
        }
        if(item.getTipo() == Mensaje.Tipo.recibido){

            ivItem.setImageResource(R.drawable.incoming3);
        }
        if(item.getTipo() == Mensaje.Tipo.enviado){
            ivItem.setImageResource(R.drawable.outgoing3);
        }
        Calendar ahora = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(item.getDate());
        int dia = calendar.get(Calendar.DATE);
        int hora = calendar.get(Calendar.HOUR);
        int minuto = calendar.get(Calendar.MINUTE);
        int mes = calendar.get(Calendar.MONTH);
        int año = calendar.get(Calendar.YEAR);


        tvDate.setText( dia + "/" + mes + "/" + año + " " + hora + ":" +minuto);
        //tvTfno.setText(item.getNumero());
        //ivItem.setImageResource(item.getImagen()); //Cuando añadamos imagenes a los contactos descomentar y quitar src del imageview
        return rowView;
    }
}
