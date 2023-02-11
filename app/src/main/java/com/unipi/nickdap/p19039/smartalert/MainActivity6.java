package com.unipi.nickdap.p19039.smartalert;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MainActivity6 extends AppCompatActivity {

    TextView textView;

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
                        List<Emergency> AllEmergencies = new List<Emergency>() {
                            @Override
                            public int size() {
                                return 0;
                            }

                            @Override
                            public boolean isEmpty() {
                                return false;
                            }

                            @Override
                            public boolean contains(@Nullable Object o) {
                                return false;
                            }

                            @NonNull
                            @Override
                            public Iterator<Emergency> iterator() {
                                return null;
                            }

                            @NonNull
                            @Override
                            public Object[] toArray() {
                                return new Object[0];
                            }

                            @NonNull
                            @Override
                            public <T> T[] toArray(@NonNull T[] a) {
                                return null;
                            }

                            @Override
                            public boolean add(Emergency emergency) {
                                return false;
                            }

                            @Override
                            public boolean remove(@Nullable Object o) {
                                return false;
                            }

                            @Override
                            public boolean containsAll(@NonNull Collection<?> c) {
                                return false;
                            }

                            @Override
                            public boolean addAll(@NonNull Collection<? extends Emergency> c) {
                                return false;
                            }

                            @Override
                            public boolean addAll(int index, @NonNull Collection<? extends Emergency> c) {
                                return false;
                            }

                            @Override
                            public boolean removeAll(@NonNull Collection<?> c) {
                                return false;
                            }

                            @Override
                            public boolean retainAll(@NonNull Collection<?> c) {
                                return false;
                            }

                            @Override
                            public void clear() {

                            }

                            @Override
                            public Emergency get(int index) {
                                return null;
                            }

                            @Override
                            public Emergency set(int index, Emergency element) {
                                return null;
                            }

                            @Override
                            public void add(int index, Emergency element) {

                            }

                            @Override
                            public Emergency remove(int index) {
                                return null;
                            }

                            @Override
                            public int indexOf(@Nullable Object o) {
                                return 0;
                            }

                            @Override
                            public int lastIndexOf(@Nullable Object o) {
                                return 0;
                            }

                            @NonNull
                            @Override
                            public ListIterator<Emergency> listIterator() {
                                return null;
                            }

                            @NonNull
                            @Override
                            public ListIterator<Emergency> listIterator(int index) {
                                return null;
                            }

                            @NonNull
                            @Override
                            public List<Emergency> subList(int fromIndex, int toIndex) {
                                return null;
                            }
                        };

                        for(QueryDocumentSnapshot documentSnapshot : querySnapshot)
                        {
                            Emergency emergency = documentSnapshot.toObject(Emergency.class);
                            //Adding all emergencies in a list
//                            AllEmergencies.add(emergency);

                            String description = emergency.getDescription();
                            String type = emergency.getEmergency();
                            String latitude = emergency.getLatitude();
                            String longtitude = emergency.getLongtitude();
                            String location = emergency.getLocation();
                            String timeStamp = emergency.getTimestamp();

//                            data += "------------------------------------" + "\n"
//                                    + "Description: " + description + "\n"
//                                    + "Type: " + type + "\n"
//                                    + "Latiude: " + latitude + "\n"
//                                    + "Longtitude: " + longtitude + "\n"
//                                    + "Location: " + location + "\n"
//                                    + "TimeStamp: " + timeStamp + "\n\n";

                            dataBuilder.append("------------------------------------").append("\n")
                                    .append("Description: ").append(description).append("\n")
                                    .append("Type: ").append(type).append("\n")
                                    .append("Latiude: ").append(latitude).append("\n")
                                    .append("Longtitude: ").append(longtitude).append("\n")
                                    .append("Location: ").append(location).append("\n")
                                    .append("TimeStamp: ").append(timeStamp).append("\n\n");


                        }
                        textView.setText(dataBuilder);
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