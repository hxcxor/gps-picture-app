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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

// broadcast receiver
public class ProximityIntentReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive (Context context, Intent intent)
    {
        // key for location manager
        String key = LocationManager.KEY_PROXIMITY_ENTERING;

        // boolean key "entering" for intent
        Boolean entering = intent.getBooleanExtra(key, false);

        if (entering) {
            Log.d(getClass().getSimpleName(), "entering");
        }
        else {
        Log.d(getClass().getSimpleName(), "exiting");

    }


        // display toast message to user
        Toast.makeText(context, "Welcome to Sindecuse!", Toast.LENGTH_SHORT);
    }
}
