package com.teltov.testrail_api.testrail.references;

import com.teltov.testrail_api.testrail.object_models.Test;
import com.teltov.testrail_api.testrail.utils.TestRailUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Tests {
    private final TestRailUtils testRailUtils;
    private final int projectId;
    private static final Logger LOG = LoggerFactory.getLogger(Cases.class);

    public Tests(TestRailUtils testRailUtils, int projectId) {
        LOG.info(String.format("Instantiating new Tests class instance for project %s", projectId));
        this.testRailUtils = testRailUtils;
        this.projectId = projectId;
    }

    //<editor-fold desc="Get tests">

    public List<Test> getTests(Long runId) {
        List<Test> tests = new ArrayList<>();

        JSONArray jsonArray = testRailUtils.sendGetRequestReturnJsonArray(String.format("get_tests/%d", runId));

        if (jsonArray != null) {
            int len = jsonArray.size();

            for (int i=0;i<len;i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                var test = (Test)new Test().deserialize(object, Test.class);
                tests.add(test);
            }
        }
        return tests;
    }

    public Test getTest(Long testId) {
        String url = String.format("get_test/%d", testId);
        JSONObject runObject = testRailUtils.sendGetRequestReturnJsonObject(url);
        return (Test) new Test().deserialize(runObject, Test.class);
    }

    //</editor-fold>
}
