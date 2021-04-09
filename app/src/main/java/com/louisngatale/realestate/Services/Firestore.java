package com.louisngatale.realestate.Services;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.louisngatale.realestate.Models.House;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Firestore {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = "Items";
    String documentId;
    House house;
    DocumentReference docRef;

    //    Add new house
    public String addHouse(HashMap<String, Object> house, ArrayList<String> images, ProgressDialog mProgress){
        Firestorage firestorage = new Firestorage();
        mProgress.setTitle("Uploading");
        mProgress.setMessage("Please wait while uploading document");
        mProgress.show();
        db.collection("houses")
                .add(house)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        documentId = documentReference.getId();
                        // Upload images first
                        firestorage.uploadImages(images, documentId);
                        mProgress.cancel();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        return documentId;
    }

//    Get all houses
    public Query getAllHouses(){
        return db
                .collection("houses");
    }

//    Get one house
    public House getHouse(String house_index){
        docRef = db.collection("houses").document(house_index);

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            this.house = documentSnapshot.toObject(House.class);
            Log.d(TAG, "getHouse: " + this.house);
        });
        return this.house;
    }

    public Query getHouses(Set<String> set) {
        List<String> id = new ArrayList<>(set);
        Log.d(TAG, "getHouses: " + id);

        docRef = db.collection("houses").document();

        return db
                .collection("houses")
                .whereIn(FieldPath.documentId(), id);

    }
}
