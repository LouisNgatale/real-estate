package com.louisngatale.realestate.Screens.Main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    Spinner spinner;
    EditText bedRooms, bathRooms, houseSize, price, description;
    TextView address;
    Button chooseAddress, addImage, submit;
    String bedRoomsValue, bathRoomsValue, houseSizeValue, priceValue, descriptionValue, addressValue,houseTypeValue;
    HashMap<String,String> errors = new HashMap<>();
    ArrayList<House> house;
    Boolean addressIsSet = false;
    private boolean locationPermissionGranted;

    private static final int LOCATION_PERMISSION_CODE = 100;

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

//        Add new house after validation
//        house.add(new House());

//        HouseUtils.getInstance();
//        HouseUtils.setHouses(house);

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
                Toast.makeText(getContext(), "Add Images", Toast.LENGTH_SHORT).show();
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
            startActivity(maps);
        }

        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        /*if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this.getContext()),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Intent maps = new Intent(getContext(), Map.class);
            startActivity(maps);
        } else {
            Toast.makeText(getContext(), "Request denied", Toast.LENGTH_SHORT).show();
        }*/
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

        Validator validator = new Validator(values);
        HashMap<String, String> formResults = validator.validateForm();

        if (!formResults.isEmpty()) {
            if (formResults.containsKey("Price")) {
                price.setError(formResults.get("Price"));
            }

            if (formResults.containsKey("Description")) {
                description.setError(formResults.get("Description"));
            }

            if (!addressIsSet){
                chooseAddress.setError("Choose an address");
            }
        }else {
//            TODO: Save the details
        }
        /*for (Map.Entry<String,String> entry : formResults.entrySet()){
            System.out.println(entry.getValue());
        }*/

    }

    
}
