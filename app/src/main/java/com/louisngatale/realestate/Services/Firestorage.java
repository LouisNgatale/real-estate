package com.louisngatale.realestate.Services;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.louisngatale.realestate.OnUploadEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Firestorage {
    StorageReference mImageStorage = FirebaseStorage.getInstance().getReference();


    private final String TAG = "Firestorage";

    public void uploadImages(ArrayList<String> images, String documentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference mDbRef = db.collection("houses").document(documentId);

        CompletableFuture.supplyAsync(() -> {
            // Upload Images in asynchronous mode
            Log.d(TAG, "run: Starting on new thread");
            images.forEach(image -> {
                StorageReference filepath = mImageStorage.child("House Images").child(Firestorage.this.createImageName() + ".jpg");
                Log.d(TAG, "uploadImages: Started");
                filepath
                    .putFile(Uri.parse(image))
                    .addOnSuccessListener(taskSnapshot -> filepath
                        .getDownloadUrl()
                        .addOnSuccessListener(uri -> {
                            String downloadUri = uri.toString();
                            mDbRef.update("houseImages", FieldValue.arrayUnion(downloadUri)).addOnCompleteListener(task -> {
                                Log.d(TAG, "uploadImages: Uploaded" + task);
                            });
                        }).addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e)));
            });
            return null;
        })
                .thenAccept(value -> Log.d(TAG, "uploadImages: Done"));
    }

    public String createImageName(){
        UUID uuid = UUID.randomUUID();
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "HOUSE_" + timeStamp+uuid;
    }
}
