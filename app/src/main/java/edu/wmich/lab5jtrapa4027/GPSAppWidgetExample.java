package edu.wmich.lab5jtrapa4027;/*
*************************************
* Programmer: Jonathan Trapane
* Class ID: jtrapa4027
* Lab #3: Interactive Design-o-Rama
* CIS 4700: Mobile Commerce Development
* Spring 2015
* Due date: 4/11/15
* Date completed: 4/11/15
*************************************
* Program Explanation
* 
*
*
*************************************
*/

import android.app.Activity;
import android.os.Bundle;

/* Could not get the GPS widget to work properly.
   Will not grab GPS coordinates and display them
   properly. Also will not properly display as
   a widget. Attempted to troubleshoot thee
   WidgetProvider in order to fix the GPS coordinates
   but could not get them to retrieve properly.
 */

public class GPSAppWidgetExample extends Activity {
    /**
     * Called when the activity is first created.
     */
    // oncreate display layout gpswidget
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpswidget);
    }
}
