package com.im2015.movilfacil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Carlos on 26/05/2015.
 */
public class AppAdapter extends BaseAdapter {
    private Context context;
    private List<App> items;
    public AppAdapter(Context context, List<App> items) {
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
            rowView = inflater.inflate(R.layout.list_item, parent, false);//TODO
        }
// Set data into the view.
        ImageView ivItem = (ImageView) rowView.findViewById(R.id.ivImagen);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvNombre);
        TextView tvTfno= (TextView) rowView.findViewById(R.id.tvTfno);
        App item = this.items.get(position);
        tvTitle.setText(item.getNombre());
        //tv.setText(item.getIntent());
        ivItem.setImageDrawable(item.getDrawable()); //Cuando añadamos imagenes a los contactos descomentar y quitar src del imageview
        return rowView;
    }
}
