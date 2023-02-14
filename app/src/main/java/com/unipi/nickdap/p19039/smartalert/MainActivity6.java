package com.unipi.nickdap.p19039.smartalert;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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

    TextView textView;

    List<Emergency> AllEmergencies = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Emergencies");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

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

    void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }
}