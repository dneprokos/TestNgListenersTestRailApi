package com.teltov.testrail_api.properties;

public class PropertyNotFoundException extends Exception {
    public PropertyNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
