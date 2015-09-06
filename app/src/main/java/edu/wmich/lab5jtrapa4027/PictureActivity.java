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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class PictureActivity extends Activity
{
    ImageView imgFavorite;
    File filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        imgFavorite = (ImageView)findViewById(R.id.imageViewPicture);
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });
    }
    public void open(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // link bitmap to picture taken
        Bitmap bp = (Bitmap) data.getExtras().get("data");

        // run createContrast method
        createContrast(bp,50);
        // set bitmap into imageview
        imgFavorite.setImageBitmap(bp);

        /* This is my attempted bluetooth share. Was not able
        to make the app not crash on send. Error occurred at
        Uri.fromFile line in intent, but could not successfully
        troubleshoot to make it send. Commented out to allow
        picture activity to function properly. */

        /*
        // check if bluetooth is supported
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();



        // if no bluetooth, alert user to no bluetooth
        if (btAdapter == null)
        {
            Toast.makeText(getBaseContext(), "No Bluetooth!", Toast.LENGTH_SHORT);
        } else
        // convert bitmap to png
        {
            FileOutputStream out = null;
            try {
                // define file output stream
                out = new FileOutputStream(filename);
                // compress bitmap to png
                bp.compress(Bitmap.CompressFormat.PNG, 100, out);
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // intent to send over bluetooth
            Intent intent = new Intent();
            // send action
            intent.setAction(Intent.ACTION_SEND);
            // type is png
            intent.setType("image/png");
            // uri from converted png
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(filename));
            // run intent
            startActivity(intent);

        }
    */

    }

    // method to grayscale image
    // could not get to work properly, attempted to grab image,
    // apply createContrast method, and place bitmap in imageview
    public static Bitmap createContrast(Bitmap src, double value) {
        // get size of image
        int width = src.getWidth();
        int height = src.getHeight();
        // make bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // integers for color information
        int A, R, G, B;
        int pixel;
        // values for contrast
        double contrast = Math.pow((100 + value) / 100, 2);

        // scan through each pixel
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.red(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.red(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // make new pixel colors for output
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        return bmOut;
    }

 /*   public void runBluetooth()
    {
        // check if bluetooth is supported
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        // if no bluetooth, alert user to no bluetooth
        if (btAdapter == null)
        {
            Toast.makeText(getBaseContext(), "No Bluetooth!", Toast.LENGTH_SHORT);
        } else
        {   // initiate android chooser
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(bp))
        }
    } */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
