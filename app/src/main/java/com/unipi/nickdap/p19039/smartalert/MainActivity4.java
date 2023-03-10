package com.unipi.nickdap.p19039.smartalert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity4 extends AppCompatActivity {

    MyTTS myTTS;
    EditText data;

    FirebaseDatabase database;
    DatabaseReference reference;

    Intent intent;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        myTTS = new MyTTS(this);

        data = findViewById(R.id.editTextTextPersonName4);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("messageForEverybody");
    }

    public void write(View view)
    {
        if(data.getText().toString().equals(""))
        {
            showMessage("Error!","You cant leave the announcement empty");
        }
        else
        {
            reference.setValue(data.getText().toString());

            Map<String,String> emergency = new HashMap<String,String>();
            emergency.put("Emergency",data.getText().toString());

            // Δημιουργεί την πρώτη φορά collection με όνομα document, κάποιο τυχαίο
            db.collection("EveryConfirmedEmergency").document().set(emergency)
                    .addOnCompleteListener((task) ->
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity4.this,"Emergency has been added successfully to the list of all emergencies so far",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            showMessage("Error", task.getException().getLocalizedMessage());
                        }
                    });

            Toast.makeText(MainActivity4.this,"Success! The message has been successfully sent to everybody",Toast.LENGTH_SHORT).show();
//            showMessage("Success!!","The message has been successfully sent to everybody");
        }
    }

    public void read(View view){

        // Έχω την επιλογή να ενημερώνομαι:
        //      α) Στο onClick
        //      β) Για οποιαδήποτε αλλαγή γίνεται στη βάση
        // Εδώ υλοποιούμε την 2η επιλογη

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
        /*reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });//αυτό είναι για ανάγνωση τιμής μια φορά μόνο*/
    }

    public void seeAll(View view)
    {
        intent = new Intent(this, MainActivity6.class);
        intent.putExtra("most_important_only","false");
        startActivity(intent);
    }

    void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }
}