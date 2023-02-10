package com.unipi.nickdap.p19039.smartalert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
    String LatitudeOfUser = "";
    String LongtitudeOfUser = "";

//    Log.w("getIntent().getStringExtra(userID)",getIntent().getStringExtra("userID"));
    String userID = "";

    EditText fullname;
    TextView textView5,textView6;
    Button saveButton;
    Intent intent;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        textView6 = findViewById(R.id.textView4);

        saveButton = findViewById(R.id.button4);

        Log.w("getIntent().getStringExtra(userID)",getIntent().getStringExtra("userID"));
        userID = getIntent().getStringExtra("userID");
        textView6.setText(userID);
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
            Map<String,String> user = new HashMap<String,String>();
            user.put("Fullname",fullname.getText().toString());
            user.put("Role","User");
            user.put("Location",locationOfUser);
            user.put("Latitude",LatitudeOfUser);
            user.put("Longtitude",LongtitudeOfUser);

            // Δημιουργεί την πρώτη φορά collection με όνομα document, το user ID από το Authentication της Firebase
            db.collection("Users").document(userID).set(user)
                    .addOnCompleteListener((task) ->
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity3.this,"User saved successfully",Toast.LENGTH_LONG).show();
                            intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            showMessage("Error", task.getException().getLocalizedMessage());
                        }
                    });


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
            LatitudeOfUser = ""+location.getLatitude();
            LongtitudeOfUser = ""+location.getLongitude();

            // For the user (and me) to see that the location has been acquired
            textView5.setText("Location has been taken, click here to save changes");
            saveButton.setVisibility(Button.VISIBLE);

            // Only for testing reasons
            TextView confirmationOfLocation = findViewById(R.id.textView6);
            confirmationOfLocation.setText("Location: " + locationOfUser);
        }
    }
}