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
import java.util.List;

public class LlamadaAdapter extends BaseAdapter {
    private Context context;
    private List<Llamada> items;
    private Contactos contactos;
    public LlamadaAdapter (Context context, List<Llamada> items) {
        this.context = context;
        this.items = items;
        contactos = new Contactos(context.getContentResolver());
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
        Llamada item = this.items.get(position);
        Contacto c = contactos.getContactoPorTelefono(item.getTelefono());
        if(c == null){
            tvTitle.setText(item.getTelefono());
        }else{
            tvTitle.setText(c.getNombre());
            tvTfno.setText(item.getTelefono());
        }
        if(item.getTipoLlamada() == CallLog.Calls.INCOMING_TYPE){

            ivItem.setImageResource(R.drawable.incoming3);
        }
        if(item.getTipoLlamada() == CallLog.Calls.OUTGOING_TYPE){
            ivItem.setImageResource(R.drawable.outgoing3);
        }
        if(item.getTipoLlamada() == CallLog.Calls.MISSED_TYPE){
            ivItem.setImageResource(R.drawable.missed);
        }
        Calendar ahora = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(item.getDateLlamada());
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