package com.louisngatale.realestate.Screens.Main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.louisngatale.realestate.Models.House;
import com.louisngatale.realestate.OnUploadEventListener;
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.RecyclerViews.PicturePreviewRecyclerAdapter;
import com.louisngatale.realestate.Screens.Map;
import com.louisngatale.realestate.Services.Firestorage;
import com.louisngatale.realestate.Services.Firestore;
import com.louisngatale.realestate.Utils.Validator;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener , View.OnClickListener {
//    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = ;
    private static final int LOCATION_PERMISSION_CODE = 100,
        MAPS_ACTIVITY_REQUEST_CODE = 101,
        REQUEST_IMAGE_CAPTURE = 102,
        EXTERNAL_STORAGE_PERMISSION_CODE = 103,
        PICK_IMAGE = 104;

    private ImageView placeholder;
    String currentPhotoPath;


    Spinner spinner;
    EditText bedRooms, bathRooms, houseSize, price, description;
    TextView address;
    Button chooseAddress, takePhoto, submit,addImages;
    String bedRoomsValue;
    String bathRoomsValue;
    String houseSizeValue;
    Integer priceValue;
    String descriptionValue;
    String addressValue;
    String houseTypeValue;
    private boolean locationPermissionGranted, isAddress = false;
    HashMap<String,String> addressResult;
    RecyclerView imagePreviewRecView;
    private final String TAG = "Dashboard";
    PicturePreviewRecyclerAdapter previewAdapter;
    private String realPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeSpinner(view);

        initializeViews(view);

        addressResult = new HashMap<>();

    }

    /**
     * @Author: Eng. Louis Ngatale
     * Initialize widget objects
     * */
    private void initializeViews(View view) {
        bedRooms = view.findViewById(R.id.bedRooms);
        bathRooms = view.findViewById(R.id.bathRooms);
        houseSize = view.findViewById(R.id.houseSize);
        price = view.findViewById(R.id.priceValue);
        description = view.findViewById(R.id.description);
        address = view.findViewById(R.id.address);
        chooseAddress = view.findViewById(R.id.choose_address);
        takePhoto = view.findViewById(R.id.takePhoto);
        submit = view.findViewById(R.id.submit);
        addImages = view.findViewById(R.id.addImages);
        placeholder = view.findViewById(R.id.empty_image);

        imagePreviewRecView = view.findViewById(R.id.imagePreviewRecView);

        previewAdapter = new PicturePreviewRecyclerAdapter(getContext());

        imagePreviewRecView.setAdapter(previewAdapter);

        imagePreviewRecView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        submit.setOnClickListener(this);

        addImages.setOnClickListener(this);

        takePhoto.setOnClickListener(this);

        chooseAddress.setOnClickListener(this);

    }

    private void initializeSpinner(@NonNull View view) {
        spinner = view.findViewById(R.id.add_house_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.house_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                try {
                    validateForm();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.choose_address:
                checkLocationPermission();
                break;
            case R.id.takePhoto:
                checkCameraPermissions();
                break;
            case R.id.addImages:
                addImages();
                break;
            default:
                break;
        }
    }

    private void addImages() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");

        startActivityForResult(Intent.createChooser(galleryIntent,"SELECT IMAGE"),PICK_IMAGE);
    }


    @SuppressLint("QueryPermissionsNeeded")
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    private void addImage(@Nullable Intent data, String type) {
//        TODO: Toggle empty image placeholder
        switch (type) {
            case "Camera":

//                Bundle extras = Objects.requireNonNull(data).getExtras();
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri imageUri = getImageUri(getContext().getApplicationContext(), imageBitmap);

                previewAdapter.getImages().add(imageUri);

                previewAdapter.notifyDataSetChanged();

//                Get actual Path
                break;
            case "Picker":
                Uri selectedImg = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getContext()).getContentResolver(),selectedImg);
                    Uri test = MediaStore.Images.Media.getContentUri(String.valueOf(selectedImg));
                    if (bitmap!= null) {
                        previewAdapter.getImages().add(selectedImg);
                        previewAdapter.notifyDataSetChanged();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

//    TODO:Handle camera intent error
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContext().getContentResolver() != null) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Objects.requireNonNull(getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
    * Validate form fields after the user has pressed
    * the submit function. Call Validator class's validate
    * function and pass the values through Hash Map
    * @Author: Eng. Louis Ngatale
    * **/
    public void validateForm() throws InterruptedException {
//        Validate price
        HashMap<String,String> values = new HashMap<>();
        values.put("Price", price.getText().toString());
        values.put("Description", description.getText().toString());
        values.put("Address", String.valueOf(isAddress));

        Validator validator = new Validator(values);
        HashMap<String, String> formResults = validator.validateForm();

        if (!formResults.isEmpty()) {
            if (formResults.containsKey("Price")) {
                price.setError(formResults.get("Price"));
            }

            if (formResults.containsKey("Description")) {
                description.setError(formResults.get("Description"));
            }

            // TODO: Error dialog
//            if (formResults.containsKey("Address")){
//                Toast.makeText(getContext(), formResults.get("Address"), Toast.LENGTH_SHORT).show();
//            }

        } else {
//            Extract values
            ArrayList<String> images = new ArrayList<>();
            bedRoomsValue = bedRooms.getText().toString();
            bathRoomsValue = bathRooms.getText().toString();
            houseSizeValue = houseSize.getText().toString();
            priceValue = Integer.valueOf(price.getText().toString());
            descriptionValue = description.getText().toString();
//            addressValue = address.getText().toString();

            previewAdapter.getImages()
                          .forEach(image -> images.add(String.valueOf(image)));

            ArrayList<String> emptyImages = new ArrayList<>();

            HashMap<String, Object> map = new HashMap<>();
                map.put("address", addressValue);
                map.put("agentAuthority", "Owner");
                map.put("agentName", "John Doe");
                map.put("houseDescription", descriptionValue);
                map.put("houseImages", emptyImages);
                map.put("housePrice", priceValue.toString());
                map.put("houseType", houseTypeValue);

            Firestore firestore = new Firestore();
            String documentId = firestore.addHouse(map,images);



        }

    }

    /**
     * @param requestCode Request code for Intent
     * @param resultCode Result code form Intent
     * @param data Result data from Intent
     * @Author: Eng. Louis Ngatale
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case MAPS_ACTIVITY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    startMap(data);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(getContext(), "Couldn't get address", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == Activity.RESULT_OK){
                    Log.d(TAG, "onActivityResult: Image capture result ok");
                    addImage(data,"Camera");
                }else {
                    Log.d(TAG, "onActivityResult: Image capture result not ok");
                }
                break;
            case PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK){
                    addImage(data, "Picker");
                }else {
                    Log.d(TAG, "onActivityResult: Image chooser result not ok");
                }
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
//                cropImage(resultCode, data);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    private void cropImage(int resultCode, @Nullable Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);

        if (resultCode == Activity.RESULT_OK){
            Uri resultUri = result.getUri();
            Log.d(TAG, "cropImage: "+ resultUri);
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
            Log.d(TAG, "cropImage: "+ error);
        }
    }

    private void startMap(@Nullable Intent data) {
        addressResult = (HashMap<String, String>) data.getSerializableExtra("Address");
        address.setText(addressResult.get("Address"));
        isAddress = true;
    }

    /**
     * Check location permissions
     * @Author: Eng. Louis Ngatale
     */
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this.getContext()),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {
            // Request permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_CODE);

        } else {
            Toast.makeText(getActivity(), "Request granted", Toast.LENGTH_SHORT).show();
            Intent maps = new Intent(getContext(), Map.class);
            startActivityForResult(maps,MAPS_ACTIVITY_REQUEST_CODE);
        }
    }

    /**
     * Check camera permissions
     * @Author: Eng. Louis Ngatale
     */
    private void checkCameraPermissions() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this.getContext()),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            // Request permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_PERMISSION_CODE);

        } else {
            dispatchTakePictureIntent();
        }
    }

    /**
     * Request permission to access different features
     * @Author: Eng. Louis Ngatale
     * **/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case LOCATION_PERMISSION_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(),
                            "Permission Granted",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getContext(),
                            "Permission Denied",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case EXTERNAL_STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(getContext(),
                            "Permission Denied",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        houseTypeValue = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        houseTypeValue = String.valueOf(parent.getItemAtPosition(0));
    }

}

