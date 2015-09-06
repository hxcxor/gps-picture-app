package edu.wmich.lab5jtrapa4027;
/*
Programmer: Jonathan Trapane
Class ID: jtrapa4027
Lab #5: Maps, Hardware, Network, SMS,
Wireless (Communications), and some Widgets
CIS 4700: Mobile Commerce Development
Spring 2015
Due date: 04/10/15
Date completed: 04/10/15
*************************************
* Program Explanation
* Lab5 provides several functionalities
* to users. The user can open a clock widget,
* User can shake device to close clock widget.
* take a picture and apply a filter, and
* map their current lat/long. A proximity
* alert is displayed when near HCOB. Photo
* can be shared via Bluetooth.
*************************************
*/

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;

import java.util.List;

// GPSWidgetProvider implements AppWidgetProvider
public class GPSWidgetProvider extends AppWidgetProvider {
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    // on update
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        // int to display id length
        final int N = appWidgetIds.length;

        // display appwidget id
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // intent to get activity
            Intent intent = new Intent(context, GPSAppWidgetExample.class);
            // pending intent to fire on gps change
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // remote view for pending intent
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.gpswidget);
            views.setOnClickPendingIntent(R.id.txtInfo, pendingIntent);

            // update appwidget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        // start gpswidget service
        context.startService(new Intent(context,GPSWidgetService.class));
    }

    // gps service
    public static class GPSWidgetService extends Service {
        // location manager variable
        private LocationManager manager = null;

        // listener for locationlistener
        private LocationListener listener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}

            // on location change
            @Override
            public void onLocationChanged(Location location) {
                AppLog.logString("Service.onLocationChanged()");

                updateCoordinates(location.getLatitude(),location.getLongitude());

                stopSelf();
            }
        };

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();


            AppLog.logString("Service.onCreate()");

            manager = (LocationManager)getSystemService(LOCATION_SERVICE);
        }

        @Override
        public void onStart(Intent intent, int startId) {
            super.onStart(intent, startId);

            waitForGPSCoordinates();
        }

        @Override
        public void onDestroy() {
            stopListening();

            AppLog.logString("Service.onDestroy()");

            super.onDestroy();
        }

        public int onStartCommand(Intent intent, int flags, int startId) {
            waitForGPSCoordinates();

            AppLog.logString("Service.onStartCommand()");

            return super.onStartCommand(intent, flags, startId);
        }

        private void waitForGPSCoordinates() {
            startListening();
        }

        // method startListening
        private void startListening(){
            AppLog.logString("Service.startListening()");

            // update criteria
            final Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);

            // find provider
            final String bestProvider = manager.getBestProvider(criteria, true);

            if (bestProvider != null && bestProvider.length() > 0) {
                manager.requestLocationUpdates(bestProvider, 500, 10, listener);
            }
            else {
                final List<String> providers = manager.getProviders(true);

                for (final String provider : providers) {
                    manager.requestLocationUpdates(provider, 500, 10, listener);
                }
            }
        }

        // method for locationlistener to stop listening
        private void stopListening(){
            try {
                // removes coordinate updates
                if (manager != null && listener != null) {
                    manager.removeUpdates(listener);
                }

                manager = null;
            }
            catch (final Exception ex) {

            }
        }

        // method to update coordinates
        private void updateCoordinates(double latitude, double longitude){
            // starts geocoder
            Geocoder coder = new Geocoder(this);
            // address list
            List<Address> addresses = null;
            String info = "";

            // string for applog
            AppLog.logString("Service.updateCoordinates()");
            AppLog.logString(info);

            // begin geocoding from location
            try
            {
                addresses = coder.getFromLocation(latitude, longitude, 2);

                // if address
                if(null != addresses && addresses.size() > 0){
                    int addressCount = addresses.get(0).getMaxAddressLineIndex();

                    // if address exists
                    if(-1 != addressCount){
                        for(int index=0; index<=addressCount; ++index){
                            info += addresses.get(0).getAddressLine(index);

                            if(index < addressCount)
                                info += ", ";
                        }
                    }
                    // get address names
                    else
                    {
                        info += addresses.get(0).getFeatureName() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea();
                    }
                }

                AppLog.logString(addresses.get(0).toString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            coder = null;
            addresses = null;

            // if long and lat exists
            if(info.length() <= 0){
                // link info to lat and long
                info = "lat " + latitude + ", lon " + longitude;
            }
            else{
                info += ("\n" + "(lat " + latitude + ", lon " + longitude + ")");
            }

            // associate remote views
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.gpswidget);

            // set textviews to info (lat + long)
            views.setTextViewText(R.id.txtInfo, info);

            // defines component name as thisWidget
            ComponentName thisWidget = new ComponentName(this, GPSWidgetProvider.class);
            // appwidgetmanager linked to manager
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            // updates appwidgetmanager
            manager.updateAppWidget(thisWidget, views);
        }

    }
}
