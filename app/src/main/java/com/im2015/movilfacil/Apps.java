package com.im2015.movilfacil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 26/05/2015.
 */
public class Apps {
    public static List<App> getApps(PackageManager pkmgr) {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> ril = pkmgr.queryIntentActivities(mainIntent, 0);
        List<App> componentList = new ArrayList<App>();
        String intent = null;
        String name = null;
        Drawable d = null;
        for (ResolveInfo ri : ril) {
            if (ri.activityInfo != null) {
                Resources res = null;
                try {
                    res = pkmgr.getResourcesForApplication(ri.activityInfo.applicationInfo);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (ri.activityInfo.labelRes != 0) {
                    name = res.getString(ri.activityInfo.labelRes);
                    intent = ri.activityInfo.processName;
                    int icon = ri.activityInfo.getIconResource();
                    try {
                        d = pkmgr.getActivityIcon(pkmgr.getLaunchIntentForPackage(intent));
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
//                    componentList.add(new App(intent,name,d));
                } else {

                    name = ri.activityInfo.applicationInfo.loadLabel(
                            pkmgr).toString();
                    intent = ri.activityInfo.processName;
                    d=ri.activityInfo.applicationInfo.loadIcon(pkmgr);
                    /*try{
                        d = pkmgr.getActivityIcon(pkmgr.getLaunchIntentForPackage(intent));
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }*/

                }
                componentList.add(new App(intent,name,d));
            }
        }
        return componentList;

    }

}
