package com.im2015.movilfacil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private List<Contacto> items;
    public ItemAdapter(Context context, List<Contacto> items) {
        this.context = context;
        this.items = items;
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
            rowView = inflater.inflate(R.layout.list_item, parent, false);
        }
// Set data into the view.
        ImageView ivItem = (ImageView) rowView.findViewById(R.id.ivImagen);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvNombre);
        TextView tvTfno= (TextView) rowView.findViewById(R.id.tvTfno);

        Contacto item = this.items.get(position);
        tvTitle.setText(item.getNombre());
        tvTfno.setText(item.getNumero());
        Bitmap foto= item.getFoto();
        if(foto!=null){
            ivItem.setImageBitmap(foto);
        }else{
            ivItem.setImageResource(R.drawable.user91);
        }
        //ivItem.setImageResource(item.getImagen()); //Cuando añadamos imagenes a los contactos descomentar y quitar src del imageview
        return rowView;
    }
}