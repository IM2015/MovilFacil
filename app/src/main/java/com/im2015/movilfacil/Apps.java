package com.im2015.movilfacil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 26/05/2015.
 */
public class  Apps {
    public static List<App> getApps(PackageManager pkmgr){
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            List<ResolveInfo> ril = pkmgr.queryIntentActivities(mainIntent, 0);
            ArrayList<App> lapps= new ArrayList<App>();
            String name = null;
            String intent = null;
            for (ResolveInfo ri : ril) {
                ActivityInfo ai = ri.activityInfo;
                if (ri.activityInfo != null) {
                    Resources res = null;
                    try {
                        res = pkmgr.getResourcesForApplication(ri.activityInfo.applicationInfo);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (ri.activityInfo.labelRes != 0) {

                        name = res.getString(ri.activityInfo.labelRes);
                        intent = ai.processName;
                        //Intent LaunchIntent = pkmgr.getLaunchIntentForPackage(name);

                    } else {
                        name = ri.activityInfo.applicationInfo.loadLabel(
                                pkmgr).toString();
                        intent = ai.processName;
                    }
                    lapps.add(new App(name,intent));
                }
            }
            //Intent LaunchIntent = pkmgr.getLaunchIntentForPackage(name);
            return lapps;
        
    }
    
}
