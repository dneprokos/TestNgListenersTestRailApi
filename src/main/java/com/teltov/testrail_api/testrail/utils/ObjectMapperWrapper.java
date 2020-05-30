package com.teltov.testrail_api.testrail.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ObjectMapperWrapper {

    public static Map convertObjectToMap(Object object) {
        ObjectMapper oMapper = new ObjectMapper();
        return oMapper.convertValue(object, Map.class);
    }
}
