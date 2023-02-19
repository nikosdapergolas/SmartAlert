package com.unipi.nickdap.p19039.smartalert;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MainActivity6 extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    private String most_important_only = "false";

    TextView textView;

    List<Emergency> AllEmergencies = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Emergencies");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        most_important_only = getIntent().getStringExtra("most_important_only");
//        Log.w("======================================================",most_important_only);
        textView = findViewById(R.id.textView10);


        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        String data = "";
                        StringBuilder dataBuilder = new StringBuilder();

                        for(QueryDocumentSnapshot documentSnapshot : querySnapshot)
                        {
                            Emergency emergency = documentSnapshot.toObject(Emergency.class);
                            //Adding all emergencies in a list
//                            AllEmergencies.add(emergency);

                            AllEmergencies.add(emergency);

//                            String description = emergency.getDescription();
//                            String type = emergency.getEmergency();
//                            String latitude = emergency.getLatitude();
//                            String longtitude = emergency.getLongtitude();
//                            String location = emergency.getLocation();
//                            String timeStamp = emergency.getTimestamp();

//                            data += "------------------------------------" + "\n"
//                                    + "Description: " + description + "\n"
//                                    + "Type: " + type + "\n"
//                                    + "Latiude: " + latitude + "\n"
//                                    + "Longtitude: " + longtitude + "\n"
//                                    + "Location: " + location + "\n"
//                                    + "TimeStamp: " + timeStamp + "\n\n";

                            // Trying to append strings to a string builder, not a string
//                            dataBuilder.append("------------------------------------").append("\n")
//                                    .append("Description: ").append(description).append("\n")
//                                    .append("Type: ").append(type).append("\n")
//                                    .append("Latiude: ").append(latitude).append("\n")
//                                    .append("Longtitude: ").append(longtitude).append("\n")
//                                    .append("Location: ").append(location).append("\n")
//                                    .append("TimeStamp: ").append(timeStamp).append("\n\n");



                        }

                        recyclerView = findViewById(R.id.listView_emergencies);
                        recyclerView.setHasFixedSize(true);

                        // Use a Linear layout manager
                        layoutManager = new LinearLayoutManager(MainActivity6.this);
                        recyclerView.setLayoutManager(layoutManager);

                        // If this Activity is called to show only the most important
                        // Then modify the list to only show the most Important emergencies
                        if(most_important_only.equals("true"))
                        {
                            AllEmergencies = keepOnlyTheImportantEmergencies(AllEmergencies);
                        }

                        // Specify an Adapter
                        mAdapter = new RecycleViewAdapter(AllEmergencies,MainActivity6.this);
                        recyclerView.setAdapter(mAdapter);

                        //Testing
                        Log.w("List of all Emergencies",AllEmergencies.toString());
                        //Toast.makeText(MainActivity6.this,"Emergencies Count: " + AllEmergencies.size(),Toast.LENGTH_LONG).show();

                        textView.setText(data);
                        // Put the entire list into a listView control
//                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity6.this, android.R.layout.simple_list_item_1,AllEmergencies);
//                        listView.setAdapter(arrayAdapter);
//                        showMessage("Data", data);
                    }
                });


//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        showMessage("Error", e.getLocalizedMessage());
//                    }
//                });
    }

    private List<Emergency> keepOnlyTheImportantEmergencies(List<Emergency> allEmergencies)
    {
        // Some important lists and variables
        List<Emergency> important_emergencies = new ArrayList<>();
//        List<Emergency> copy_of_all_emergencies = allEmergencies;
        int importance = 0;
        for( Emergency emergency_out : allEmergencies)
        {
            for( Emergency emergency_in : allEmergencies)
            {
                // If i am not comparing the same emergency
                if(emergency_out != emergency_in)
                {
                    // Get the 2 locations
                    String longtitude1 = emergency_out.getLongtitude();
                    String latitude1 = emergency_out.getLatitude();
                    String longtitude2 = emergency_in.getLongtitude();
                    String latidude2 = emergency_in.getLatitude();

                    // Convert to DoubleN
                    Double long1 = Double.parseDouble(longtitude1);
                    Double lat1 = Double.parseDouble(latitude1);
                    Double long2 = Double.parseDouble(longtitude2);
                    Double lat2 = Double.parseDouble(latidude2);
                    //---------------------------------------------
                    // In this part i am going to compare the 2 locations and try to
                    // see if the 2 locations are less than 1 kilometer appart
//                    Double distance_between = distance(Double.valueOf(latitude1),Double.valueOf(longtitude1),Double.valueOf(latidude2),Double.valueOf(longtitude2));
//                    Double distance_between = distance(long1,lat1,long2,lat2);
                    Location locationA = new Location("point A");

                    Log.w("latitude1",lat1.toString());
                    Log.w("longtitude1",long1.toString());
                    locationA.setLatitude(lat1);
                    locationA.setLongitude(long1);
//                    Log.w("location1",locationA.toString());

                    Location locationB = new Location("point B");

                    Log.w("latitude2",lat2.toString());
                    Log.w("longtitude3",long2.toString());
                    locationB.setLatitude(lat2);
                    locationB.setLongitude(long2);
//                    Log.w("location2",locationB.toString());

                    Float distance_between = locationA.distanceTo(locationB);
                    Log.w("===============================================================", distance_between.toString());
                    //---------------------------------------------
                    if (distance_between <= 1000.0 && emergency_in.getEmergency().equals(emergency_out.getEmergency())) // Σιγουρα χρειάζεται αλλαγή αυτή η τιμή. Την τεστάρω και την αλλάζω αναλόγως
                    {
                        importance += 1;
                    }
                }
            }
            if(importance > 0)
            {
                important_emergencies.add(emergency_out);
            }
            importance = 0;
        }

        // Filter the list so that only the MOST IMPORTANT emergencies get sent back
        // Filtering starts here

        // Sending back the filtered list
        return important_emergencies;
    }

    private Double distance(Double lat1, Double lon1, Double lat2, Double lon2) {
        Double theta = lon1 - lon2;
        Double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private Double deg2rad(Double deg) {
        return (deg * Math.PI / 180.0);
    }

    private Double rad2deg(Double rad) {
        return (rad * 180.0 / Math.PI);
    }

    // An on click event to call function to show only the most important emergencies
    public void show_most_important(View view)
    {
        Intent most_important = new Intent(this,MainActivity6.class);
        most_important.putExtra("most_important_only","true");
        startActivity(most_important);
    }

    void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }
}