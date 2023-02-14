package com.unipi.nickdap.p19039.smartalert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AfterClickOnListViewActivity extends AppCompatActivity {

    MyTTS myTTS;

    FirebaseDatabase database;
    DatabaseReference reference;

    List<Emergency> AllEmergencies = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Emergencies");

    String EmergencyType;
    String Descryption;
    String Location;
    String Longtitude;
    String Latitude;
    String Timestamp;

    EditText emerType, descr, locat, time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_click_on_list_view);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_HIGH);
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }

        myTTS = new MyTTS(this);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("messageForEverybody");

        //Intent intent = getIntent();
        EmergencyType = getIntent().getStringExtra("EmergencyType");
        Descryption = getIntent().getStringExtra("Descryption");
        Location = getIntent().getStringExtra("Location");
        Longtitude = getIntent().getStringExtra("Longtitude");
        Latitude = getIntent().getStringExtra("Latitude");
        Timestamp = getIntent().getStringExtra("Timestamp");

        emerType = findViewById(R.id.et_emergencyType);
        descr = findViewById(R.id.et_descryption);
        locat = findViewById(R.id.et_location);
        time = findViewById(R.id.et_timestamp);

        emerType.setText(EmergencyType);
        descr.setText(Descryption);
        locat.setText(Location);
        time.setText(Timestamp);


        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        for(QueryDocumentSnapshot documentSnapshot : querySnapshot)
                        {
                            Emergency emergency = documentSnapshot.toObject(Emergency.class);

                            AllEmergencies.add(emergency);
                        }

                        //Testing
                        Log.w("List of all Emergencies",AllEmergencies.toString());
                        //Toast.makeText(AfterClickOnListViewActivity.this,"Emergencies Count: " + AllEmergencies.size(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void announce(View view)
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myTTS.speak(snapshot.getValue().toString());
                showMessage("DB data change", snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(emerType.getText().toString().equals(""))
        {
            showMessage("Error","Cannot leave emergency type empty");
        }
        else if(descr.getText().toString().equals(""))
        {
            showMessage("Error","Cannot leave Description empty");
        }
        else if(locat.getText().toString().equals(""))
        {
            showMessage("Error","Cannot leave location empty");
        }
        else if(time.getText().toString().equals(""))
        {
            showMessage("Error","Cannot leave timestamp empty");
        }
        else
        {
            reference.setValue(descr.getText().toString());

            Map<String,String> emergency = new HashMap<String,String>();
            emergency.put("Emergency",descr.getText().toString());
            emergency.put("Timestamp",time.getText().toString());
            emergency.put("Location",locat.getText().toString());

            // Δημιουργεί την πρώτη φορά collection με όνομα document, κάποιο τυχαίο
            db.collection("EveryConfirmedEmergency").document().set(emergency)
                    .addOnCompleteListener((task) ->
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(AfterClickOnListViewActivity.this,"Emergency has been added successfully to the list of all emergencies so far",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            showMessage("Error", task.getException().getLocalizedMessage());
                        }
                    });
        }

//        startService(new Intent(this,MyService.class));

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(AfterClickOnListViewActivity.this,"My notification");
//        builder.setContentTitle("EMERGENCY");
//        builder.setContentText("Please log in Quickly into the app to see the emergency situation. Important!!!");
//        builder.setSmallIcon(R.drawable.ic_launcher_background);
//        builder.setAutoCancel(true);
//
//        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(AfterClickOnListViewActivity.this);
//        managerCompat.notify();

//        NotificationChannel channel = new NotificationChannel("123","channelUnipi",
//                NotificationManager.IMPORTANCE_HIGH);
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.createNotificationChannel(channel);
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(getApplicationContext(),"123");
//        builder.setContentTitle("EMERGENCY")
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentText("Please log in Quickly into the app to see the emergency situation. Important!!!")
//                .setAutoCancel(true);
//        notificationManager.notify(1,builder.build());


//        startService(new Intent(this,MyService.class));
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent intent = new Intent(this,MyService.class);
//        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),1234,intent,
//                PendingIntent.FLAG_IMMUTABLE);
//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()
//                +(5000),pendingIntent);

    }

    void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }
}