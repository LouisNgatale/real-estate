package com.louisngatale.realestate.Utils;

import android.util.Log;

import java.util.HashMap;
import java.util.Objects;

public class Validator {

    HashMap<String,String> values = new HashMap<>();

    HashMap<String,String> errors = new HashMap<>();
    private final String TAG  = "Validator";

    public Validator(HashMap<String,String> values) {
        this.values = values;
    }

    public HashMap<String,String> validateForm(){
        validatePrice(Objects.requireNonNull(values.get("Price")));
        validateDescription(Objects.requireNonNull(values.get("Description")));
        validateAddress(values.get("Address"));
        return errors;
    }

    private void validateAddress(String address) {
        if (address.equals("false")){
            errors.put("Address", "Choose address");
        }
    }

    private void validateDescription(String description) {
        if (!description.isEmpty()){
//            TODO: Sanitize Input
        }else {
            errors.put("Description","Enter description");
        }
    }

    public void validatePrice(String price){
        if (!price.isEmpty()) {
            String numberRegex = "^(0|[1-9][0-9]*)$";

            if (!price.matches(numberRegex)){
                errors.put("Price","Enter a valid price");
            }else {
                if (!errors.containsKey("Price")) {
//                    TODO: Sanitize input
                    return;
                }
                errors.remove("Price");
            }
        }else {
            errors.put("Price", "Enter price");
        }
    }

}
