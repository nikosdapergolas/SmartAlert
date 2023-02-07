package com.unipi.nickdap.p19039.smartalert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //Each intent represents the Activities that I will open
    Intent intent;

    EditText email,password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Αρχικοποιώ το απο που θα παίρνω δεδομένα για το email και password που εισάγει ο χρήστης
        email= findViewById(R.id.editTextTextPersonName2);
        password=findViewById(R.id.editTextTextPersonName);

        // Αρχικοποιώ το Authentication object της Firebase με Singleton Design Pattern
        mAuth = FirebaseAuth.getInstance();
    }

    public void signin(View view)
    {
        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(),password .getText().toString().trim())
                // Using a Lambda expression
                .addOnCompleteListener((task)->{
                    if(task.isSuccessful())
                    {
                        showMessage("Success!","Welcome to the smart Alert app!!");

                        intent = new Intent(this,MainActivity2.class);
                        startActivity(intent);
                    }
                    else
                    {
                        showMessage("Error",task.getException().getLocalizedMessage());
                    }
                });
    }

    public void signup(View view)
    {
        mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        // Έχω 1 μόνο Listener και επιλέγω τα αποτελέσματα που μου δίνει με τη βοήθεια του task
                        // ( Δηλαδή της παραμέτρου της συνάρτησης onComplete )
                        if(task.isSuccessful())
                        {
                            showMessage("Success!","User Authentication was successfull");
                            // Μπορώ απο εκεί και ύστερα να έχω το όνομα του χρήστη από την βάση.
                            // Η Firebase χρησιμοποιεί token σε AppLevel

                            // Πως μπορώ να δείξω το Id του χρήστη που έκανε signup ή signin?
                            //showMessage("User Id:",mAuth.getUid());
                        }
                        else
                        {
                            // Αυτό το else πετάει το σωστό μήνυμα που πρέπει να δει ο χρήστης
                            // Χρησιμοποιώντας το ".getException().getLocalizedMessage()" :
                            // πιάνω όλα τα πιθανά errors, και όχι μόνο αυτό, δείχνω μάλιστα
                            // και το κατάλληλο μήνυμα στον χρήστη
                            showMessage("Error!",task.getException().getLocalizedMessage());
                        }
                    }
                });
    }

    void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }
}