package edu.wmich.lab5jtrapa4027;

import android.util.Log;

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

// applog for gps widget
public class AppLog
{
    private static final String APP_TAG = "GPSWidget";

    public static int logString(String message)
    {
        return Log.i(APP_TAG, message);
    }
}
