package com.louisngatale.realestate.Services;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Firestorage {
    StorageReference mImageStorage = FirebaseStorage.getInstance().getReference();
    private final String TAG = "Firestorage";

    public ArrayList<String> uploadImages(ArrayList<String> images){
        ArrayList<String> downloadUriResult = new ArrayList<>();

         images.forEach(image -> {
            StorageReference filepath = mImageStorage.child("House Images").child( createImageName() + ".jpg");
            filepath
                .putFile(Uri.parse(image))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "onSuccess: " + "uploaded");

                        filepath
                                .getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUri = uri.toString();
                                downloadUriResult.add(downloadUri);
                                Log.d(TAG, "onSuccess: " + downloadUri);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e);
                            }
                        });
                    }
                });
         });
         return downloadUriResult;
    }

    public String createImageName(){
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "HOUSE_" + timeStamp;
    }
}
