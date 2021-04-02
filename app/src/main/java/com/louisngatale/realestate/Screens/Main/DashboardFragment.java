package com.louisngatale.realestate.Screens.Main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
import com.louisngatale.realestate.R;
import com.louisngatale.realestate.Screens.Map;
import com.louisngatale.realestate.Utils.HouseUtils;
import com.louisngatale.realestate.Utils.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener , View.OnClickListener {
//    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = ;
    private static final int MAPS_ACTIVITY_REQUEST_CODE = 101,REQUEST_IMAGE_CAPTURE = 102;
    Spinner spinner;
    EditText bedRooms, bathRooms, houseSize, price, description;
    TextView address;
    Button chooseAddress, addImage, submit;
    String bedRoomsValue, bathRoomsValue, houseSizeValue, priceValue, descriptionValue, addressValue,houseTypeValue;
    private boolean locationPermissionGranted, isAddress = false;
    HashMap<String,String> addressResult;
    RecyclerView imagePreviewRecView;

    private static final int LOCATION_PERMISSION_CODE = 100;
    private final String TAG = "Dashboard";

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

    private void initializeViews(View view) {
        bedRooms = view.findViewById(R.id.bedRooms);
        bathRooms = view.findViewById(R.id.bathRooms);
        houseSize = view.findViewById(R.id.houseSize);
        price = view.findViewById(R.id.priceValue);
        description = view.findViewById(R.id.description);
        address = view.findViewById(R.id.address);
        chooseAddress = view.findViewById(R.id.choose_address);
        addImage = view.findViewById(R.id.addImages);
        submit = view.findViewById(R.id.submit);

        submit.setOnClickListener(this);

        addImage.setOnClickListener(this);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       houseTypeValue = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        houseTypeValue = String.valueOf(parent.getItemAtPosition(0));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                validateForm();
                break;
            case R.id.choose_address:
                getLocationPermission();
                break;
            case R.id.addImages:
                dispatchTakePictureIntent();
                break;
            default:
                break;
        }
    }

    private void getLocationPermission() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_CODE){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(),
                        "Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();


            }
            else {
                Toast.makeText(getContext(),
                        "Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void validateForm(){
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
            if (formResults.containsKey("Address")){
                Toast.makeText(getContext(), formResults.get("Address"), Toast.LENGTH_SHORT).show();
            }

        }else {
//            TODO: Save the details
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case MAPS_ACTIVITY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    addressResult = (HashMap<String, String>) data.getSerializableExtra("Address");
                    address.setText(addressResult.get("Address"));
                    isAddress = true;
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                    Toast.makeText(getContext(), "Couldn't get address", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == Activity.RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
//                    imageView.setImageBitmap(imageBitmap);
                }
                break;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }
}
