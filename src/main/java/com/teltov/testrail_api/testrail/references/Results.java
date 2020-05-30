package com.teltov.testrail_api.testrail.references;

import com.teltov.testrail_api.testrail.object_models.Result;
import com.teltov.testrail_api.testrail.utils.ObjectMapperWrapper;
import com.teltov.testrail_api.testrail.utils.TestRailUtils;
import com.teltov.testrail_api.testrail.utils.TestStatus;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Results {
    private final TestRailUtils testRailUtils;
    private final int projectId;

    private static final Logger LOG = LoggerFactory.getLogger(Results.class);

    public Results(TestRailUtils testRailUtils, int projectId) {
        LOG.info(String.format("Instantiating new Results class instance for project %s", projectId));
        this.testRailUtils = testRailUtils;
        this.projectId = projectId;
    }

    //<editor-fold desc="Add results">

    public Result addResult(long testId, TestStatus resultStatus) {
        String url = String.format("add_result/%d", testId);
        var result = new Result(resultStatus.getStatus());

        Map jsonBody = ObjectMapperWrapper.convertObjectToMap(result);
        JSONObject object = testRailUtils.sendPostRequestReturnJsonObject(url, jsonBody);
        return (Result) result.deserialize(object, Result.class);
    }

    //</editor-fold>
}
