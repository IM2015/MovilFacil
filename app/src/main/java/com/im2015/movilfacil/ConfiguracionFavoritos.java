package com.im2015.movilfacil;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alnalda on 26/03/2015.
 */
public class ConfiguracionFavoritos {
    private final String SHARED_PREFS_FILE = "PrefsFavs";
    private final String KEY_FAV1 = "fav1";
    private final String KEY_FAV2 = "fav2";
    private final String KEY_FAV3 = "fav3";
    private Context mContext;


    public ConfiguracionFavoritos(Context context) {
        mContext = context;
    }

    //método privado que nos devuelve un objeto tipo SharedPreferences el cual abrimos
    // en modo PRIVATE con el segundo parámetro, lo que significa que solo será accesible por nuestra aplicación.
    private SharedPreferences getSettings() {
        return mContext.getSharedPreferences(SHARED_PREFS_FILE, 0);
    }
    public String getfav1(){
        return getSettings().getString(KEY_FAV1, null);
    }
    public String getfav2(){
        return getSettings().getString(KEY_FAV2, null);
    }
    public String getfav3(){
        return getSettings().getString(KEY_FAV3, null);
    }
    public void setFav1(String id){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_FAV1, id );
        editor.commit();
    }
    public void setFav2(String id){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_FAV2, id );
        editor.commit();
    }
    public void setFav3(String id){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_FAV3, id );
        editor.commit();
    }

}
