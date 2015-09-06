package edu.wmich.lab5jtrapa4027;

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

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment{

    // callback methods implemented by MainActivity
    public interface MainFragmentListener
    {
    }

    // listener for MainFragment
    private MainFragmentListener listener;

    // set MainFragmentListener when fragment attached
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        listener = (MainFragmentListener) activity;
    }

    // remove MainFragmentListener when Fragment detached
    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }

    // display layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // save fragment across changes
        setRetainInstance(true);
        // has menu options
        setHasOptionsMenu(true);

        // inflate layout
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // variable for buttonWhereAmI
        Button whereAmIButton = (Button) view.findViewById(R.id.buttonWhereAmI);
        whereAmIButton.setOnClickListener(whereAmIButtonClicked);
        // variable for buttonPicture
        Button pictureButton = (Button) view.findViewById(R.id.buttonPicture);
        pictureButton.setOnClickListener(pictureButtonClicked);
        // variable for buttonGPSWidget
        Button gpsWidgetButton = (Button) view.findViewById(R.id.buttonGPSWidget);
        gpsWidgetButton.setOnClickListener(gpsWidgetButtonClicked);

        return view;
    }

    // onclicklistener for where am i button
    View.OnClickListener whereAmIButtonClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            Intent myIntent = new Intent(getActivity(), WhereAmIFragment.class);
            getActivity().startActivity(myIntent);
            /*
            Fragment whereAmIFragment = new WhereAmIFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.add(R.id
                    .fragmentContainer2, whereAmIFragment);
            transaction.addToBackStack(null);

            transaction.commit(); */
        }
    };

    // onclicklistener for gps widget button
    View.OnClickListener gpsWidgetButtonClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent myIntent = new Intent(getActivity(), GPSAppWidgetExample.class);
            getActivity().startActivity(myIntent);
        }
    };

    // onclicklistener for picture button
    View.OnClickListener pictureButtonClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent myIntent = new Intent(getActivity(), PictureActivity.class);
            getActivity().startActivity(myIntent);
        }
    };



}