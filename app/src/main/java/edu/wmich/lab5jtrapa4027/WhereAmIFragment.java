package edu.wmich.lab5jtrapa4027;

/*
Programmer:Jonathan Trapane
Class ID:jtrapa4027
Lab #5: Maps,Hardware,Network,SMS,
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

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class WhereAmIFragment extends Activity
    implements LocationListener
{
    // variables for lat/long/address textviews
    private TextView latitudeField;
    private TextView longitudeField;
    private TextView addressField;

    // variable for LocationManager
    private LocationManager locationManager;
    // string for provider
    private String provider;

    // string for prosximity alert
    private static final String SINDECUSE_PROXIMITY_ALERT =
            "SINDECUSE PROXIMITY ALERT";

    // callback methods implemented by MainActivity
    public interface WhereAmIFragmentListener
    {}

    private WhereAmIFragmentListener listener;
/*
    // set WhereAmIFragmentListener when fragment attached
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        listener = (WhereAmIFragmentListener) activity;
    }

    // remove WhereAmIFragmentListener when Fragment detached
    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }
*/
    // called when activity is created


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whereami);

        // link variable fields to corresponding textviews
        latitudeField = (TextView) findViewById(R.id.txtViewLatitude);
        longitudeField = (TextView) findViewById(R.id.txtViewLongitude);
        addressField = (TextView) findViewById(R.id.txtViewAddress);

        //get location manager
        locationManager = (LocationManager) this.getSystemService(
                Context.LOCATION_SERVICE);

        // tell criteria how to select location provider
        Criteria criteria = new Criteria();
        // get provider
        provider = locationManager.getBestProvider(criteria,false);
        // set location based on last known
        Location location = locationManager.getLastKnownLocation(provider);

        // initialize location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");

            // when location is changed
            onLocationChanged(location);
        } else {
            latitudeField.setText("Location not available");
            longitudeField.setText("Location not available");
            addressField.setText("Location not available");
        }

        // sets proximity alert
        setProximityAlert();

    }

    // update at startup
    @Override
    public void onResume()
    {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    // remove locationlistener updates on pause
    @Override
    public void onPause()
    {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    // update when location is changed
    public void onLocationChanged(Location location) {
        // assign location latitude to lat
        double lat = (int) (location.getLatitude());
        // assign location longitude to lng
        double lng = (int) (location.getLongitude());
        // set textview values to lat/long fields
        latitudeField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));

        // instantiate geocoder
        Geocoder gc = new Geocoder(this, Locale.ENGLISH);


        // check for geocoder
        if(gc.isPresent()){
            // address list list is null
            List<Address> list = null;

            try {
                list = gc.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = list.get(0);

            StringBuffer str = new StringBuffer();
            str.append("Address: " + address.getAddressLine(0) + "\n");
            str.append("City: " + address.getLocality() + "\n");
            str.append("Zip Code: " + address.getPostalCode() + "\n");
            str.append("Country: " + address.getCountryName() + "\n");

            String strAddress = str.toString();
            addressField.setText(String.valueOf(strAddress));


        }

    }



    private void setProximityAlert(){
    }

    // on status change
    public void onStatusChanged(String provider, int status, Bundle extras)
    {}

    // when provider is enabled
    @Override
    public void onProviderEnabled(String provider)
    {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    // when provider is disabled
    @Override
    public void onProviderDisabled(String provider)
    {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }


}
