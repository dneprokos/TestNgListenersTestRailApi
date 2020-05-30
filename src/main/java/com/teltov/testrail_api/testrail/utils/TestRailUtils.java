package com.teltov.testrail_api.testrail.utils;

import com.gurock.testrail.APIClient;
import com.gurock.testrail.APIException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class TestRailUtils {
    private final APIClient client;
    private static final Logger LOG = LoggerFactory.getLogger(TestRailUtils.class);

    public TestRailUtils(APIClient client) {
        this.client = client;
    }

    public JSONObject sendGetRequestReturnJsonObject(String uri) {
        LOG.info(String.format("Send GET request: %s", uri));
        try {
            client.sendGet(uri);
            return  (JSONObject) client.sendGet(uri);
        } catch (IOException | APIException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray sendGetRequestReturnJsonArray(String uri) {
        LOG.info(String.format("Send GET request: %s", uri));
        try {
            return  (JSONArray) client.sendGet(uri);
        } catch (IOException | APIException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject sendPostRequestReturnJsonObject(String uri, Map data) {
        StringBuilder jsonBodyBuilder = new StringBuilder();
        String jsonBody = "";

        if (!data.isEmpty()) {
            data.forEach((key, value) -> jsonBodyBuilder.append(key + ":" + value + ", "));
            jsonBody = jsonBodyBuilder.toString();
            jsonBody = jsonBody.substring(0, jsonBody.length()-2);
        }

        LOG.info(String.format("Send POST request: %s with body: { %s }", uri, jsonBody));
        try {
            return  (JSONObject) client.sendPost(uri, data);
        } catch (IOException | APIException e) {
            e.printStackTrace();
            return null;
        }
    }
}
