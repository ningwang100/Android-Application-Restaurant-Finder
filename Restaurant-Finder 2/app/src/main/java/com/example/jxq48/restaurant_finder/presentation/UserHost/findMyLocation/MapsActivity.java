/**
 * This class is used to show user current location in the google map
 */
package com.example.jxq48.restaurant_finder.presentation.UserHost.findMyLocation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.jxq48.restaurant_finder.R;
import com.example.jxq48.restaurant_finder.entities.Restaurant;
import com.example.jxq48.restaurant_finder.presentation.intents.IntentToRestaurantHost;
import com.example.jxq48.restaurant_finder.ws.remote.RemoteServerProxy;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ArrayList<Restaurant> list = null;
    private Map<Marker, Restaurant> allMarkersMap = new Hashtable<Marker,Restaurant>();
    private double latitude=0;
    private double longitude=0;
    private String locationAddress;

    //get latitude and longitude
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            latitude = gpsLocation.getLatitude();
            longitude = gpsLocation.getLongitude();
        }
     else {
        showSettingsAlert();
    }

        RemoteServerProxy proxy = new RemoteServerProxy();
        list = proxy.getNearbyRestaurant(longitude,latitude);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    //Get the map
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    // Add marker and show current location in the map and find nearby restaurants
    private void setUpMap() {

            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title("CurrentLocation"));
            mMap.setOnInfoWindowClickListener(this);
            LatLng latLng = new LatLng(latitude, longitude);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 14.0f);
            mMap.animateCamera(yourLocation);
        if (list.size()!=0) {
           for (int i = 0; i < list.size(); i++) {

                double longi = list.get(i).getLongitude();
                double lati = list.get(i).getLatitude();
                String name = list.get(i).getName();
                String address = list.get(i).getAddress();

                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lati, longi)).title(name).snippet(address).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).anchor(1.0f, 1.0f));
                allMarkersMap.put(marker, list.get(i));
                mMap.setOnInfoWindowClickListener(this);

           }
        }
        else {
            Toast.makeText(this, "No nearby restaurant!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    // Set listen on marker title
    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.getTitle().equals("CurrentLocation")) {

            LocationAddress Address = new LocationAddress();
            Address.getAddressFromLocation(latitude, longitude,
                    getApplicationContext(), new GeocoderHandler());

        }
        else {
            Restaurant restaurant = allMarkersMap.get(marker);
            IntentToRestaurantHost intent = new IntentToRestaurantHost(MapsActivity.this);
            intent.putExtra("RESTAURANT", restaurant);
            this.startActivityForResult(intent, 0);
        }
    }
    // use dialog to show a certain address
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            AlertDialog alertDialog = new AlertDialog.Builder(
                    MapsActivity.this).create();
            alertDialog.setTitle("Current location");
            alertDialog.setMessage(locationAddress);
            alertDialog.setButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                        }
                    });
            alertDialog.show();
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MapsActivity.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        MapsActivity.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }



}
