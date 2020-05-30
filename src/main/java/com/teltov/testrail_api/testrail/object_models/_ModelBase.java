package com.teltov.testrail_api.testrail.object_models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class _ModelBase {
    /**
     * Converts JSONObject to object itself
     * @param jsonObject JSONObject to be converted
     * @param javaClass Java class you want to convert to
     * @return plain object. You need to cast it to expected object
     */
    public Object deserialize(JSONObject jsonObject, Class javaClass) {
        var jsonString = jsonObject.toJSONString();
        ObjectMapper oMapper = new ObjectMapper();
        Object object = this;
        try {
            object = oMapper.readValue(jsonString, javaClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return object;
    }
}
