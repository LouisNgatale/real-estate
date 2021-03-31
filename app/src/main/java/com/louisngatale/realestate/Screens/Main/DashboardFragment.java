package com.louisngatale.realestate.Screens.Main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.louisngatale.realestate.Utils.HouseUtils;
import com.louisngatale.realestate.Utils.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener , View.OnClickListener {
    Spinner spinner;
    EditText bedRooms, bathRooms, houseSize, price, description;
    TextView address;
    Button chooseAddress, addImage, submit;
    String bedRoomsValue, bathRoomsValue, houseSizeValue, priceValue, descriptionValue, addressValue,houseTypeValue;
    HashMap<String,String> errors = new HashMap<>();
    ArrayList<House> house;
    Boolean addressIsSet = false;

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
                Toast.makeText(getContext(), "Address", Toast.LENGTH_SHORT).show();
                break;
            case R.id.addImages:
                Toast.makeText(getContext(), "Add Images", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
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
