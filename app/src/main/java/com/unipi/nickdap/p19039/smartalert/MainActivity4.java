package com.unipi.nickdap.p19039.smartalert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity4 extends AppCompatActivity {

    EditText data;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        data = findViewById(R.id.editTextTextPersonName4);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("messageForEverybody");
    }

    public void write(View view)
    {
        reference.setValue(data.getText().toString());

        showMessage("Success!!","The message has been successfully sent to everybody");
    }

    public void read(View view){

        // Έχω την επιλογή να ενημερώνομαι:
        //      α) Στο onClick
        //      β) Για οποιαδήποτε αλλαγή γίνεται στη βάση
        // Εδώ υλοποιούμε την 2η επιλογη

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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

    void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }
}