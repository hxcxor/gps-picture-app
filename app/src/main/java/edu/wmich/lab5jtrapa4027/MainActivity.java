package edu.wmich.lab5jtrapa4027;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/*
Programmer:Jonathan Trapane
Class ID:jtrapa4027
Lab #5:Maps,Hardware,Network,SMS,
Wireless(Communications),and some Widgets
CIS 4700:Mobile Commerce Development
Spring 2015
Due date:04/10/15
Date completed:04/10/15
*************************************
*Program Explanation
*Lab5 provides several functionalities
*to users.The user can open a clock widget,
*User can shake device to close clock widget.
*take a picture and apply a filter,and
*map their current lat/long.A proximity
*alert is displayed when near HCOB.Photo
*can be shared via Bluetooth.
*************************************
*/

public class MainActivity extends Activity
    implements MainFragment.MainFragmentListener,
    WhereAmIFragment.WhereAmIFragmentListener

{

    // displays main fragment
    MainFragment mainFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // return if activity being restored
        if (savedInstanceState != null)
            return;

        // does layout contain fragmentContainer? (phone)
        // mainfragment displays in fragmentcontainer
        if (findViewById(R.id.fragmentContainer) != null) {
            mainFragment = new MainFragment();

            // replace container with main fragment
            FragmentTransaction transaction =
                    getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, mainFragment);
            // displays mainFragment
            transaction.commit();
        }

        // string for locservice
        String locService = Context.LOCATION_SERVICE;
        // location manager
        LocationManager locationManager;
        // pulls locationmanager system service
        locationManager = (LocationManager)this.getSystemService(locService);

        // lat and long for sindecuse health center
        double lat = 42.285528;
        double lng = -85.616393;
        // 100 meters
        float radius = 100f;
        // no expiration
        long expiration = -1;

        // string for proximity alert
        String PROXIMITY_ALERT_INTENT = "edu.wmich.lab5jtrapa4027";
        // proximity alert intent
        Intent intent = new Intent(PROXIMITY_ALERT_INTENT);
        // pendingintent to fire once within radius
        PendingIntent proximityIntent = PendingIntent.getBroadcast(
                this, -1, intent, PendingIntent.FLAG_ONE_SHOT);
        // adds proximity alert
        locationManager.addProximityAlert(lat,lng,radius,expiration,proximityIntent);

    }




    // when MainActivity resumes
    @Override
    protected void onResume()
    {
        super.onResume();

        // if no mainFragment, tablet in use
        // get reference from FragmentManager
        if (mainFragment == null)
        {
            mainFragment =
                    (MainFragment) getFragmentManager().findFragmentById(
                            R.id.fragmentContainer);
        }
    }

    // inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // update menu items on change
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
