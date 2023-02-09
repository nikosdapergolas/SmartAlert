package com.unipi.nickdap.p19039.smartalert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity implements LocationListener {

    // A permission that i need
    String [] permission = {Manifest.permission.ACCESS_FINE_LOCATION};

    // Some persmission request codes
    final int ACCESS_FINE_LOCATION_REQUEST_CODE = 123;
    final int ACCESS_COARSE_LOCATION_REQUEST_CODE = 1234;

    // Some flags
    boolean locationPermissionHasBeenGranted;
    boolean locationChangedFirstTime = true;

    LocationManager locationManager;

    String locationOfUser = "";

    EditText fullname;
    TextView textView5;
    Button saveButton;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        locationPermissionHasBeenGranted = false;

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        // Request the permissions needed
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},ACCESS_FINE_LOCATION_REQUEST_CODE);

        fullname = findViewById(R.id.editTextTextPersonName3);
        textView5 = findViewById(R.id.textView5);
        saveButton = findViewById(R.id.button4);
    }

    public void reveal_Location(View view)
    {


        if(fullname.getText().toString().equals(""))
        {
            showMessage("Error!","You cannot leave fullname blank");
        }
        else
        {
            locationPermissionHasBeenGranted = true;
            textView5.setText("App is gathering your location.. Please wait");

            // If the user hasn't given the permissions that are needed
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},ACCESS_FINE_LOCATION_REQUEST_CODE);
                return;
            }
            // Ask for user's location
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//            textView5.setVisibility(View.VISIBLE);
        }
    }

    public void save_and_return(View view)
    {
        if(!locationOfUser.equals(""))
        {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // Getting the location of the user only 1 time
        if (locationChangedFirstTime)
        {
            locationChangedFirstTime = false;
            locationOfUser = location.getLatitude()+","+location.getLongitude();

            // For the user (and me) to see that the location has been acquired
            textView5.setText("Location has been taken, click here to save changes");
            saveButton.setVisibility(Button.VISIBLE);

            // Only for testing reasons
            TextView confirmationOfLocation = findViewById(R.id.textView6);
            confirmationOfLocation.setText("Location: " + locationOfUser);
        }
    }
}