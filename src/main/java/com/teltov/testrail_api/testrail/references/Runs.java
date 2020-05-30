package com.teltov.testrail_api.testrail.references;

import com.teltov.testrail_api.testrail.object_models.Run;
import com.teltov.testrail_api.testrail.utils.ObjectMapperWrapper;
import com.teltov.testrail_api.testrail.utils.TestRailUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Runs {
    private final TestRailUtils testRailUtils;
    private final int projectId;

    private static final Logger LOG = LoggerFactory.getLogger(Runs.class);

    public Runs(TestRailUtils testRailUtils, int projectId) {
        LOG.info(String.format("Instantiating new Runs class instance for project %s", projectId));
        this.testRailUtils = testRailUtils;
        this.projectId = projectId;
    }

    //<editor-fold desc="Get runs">

    public Run getRunById(long runId) {
        String url = String.format("get_run/%d", runId);
        JSONObject runObject = testRailUtils.sendGetRequestReturnJsonObject(url);
        return (Run)new Run().deserialize(runObject, Run.class);
    }
    public List<Run> getRunsByProjectId(){
        JSONArray jsonArray = testRailUtils.sendGetRequestReturnJsonArray(String.format("get_runs/%d", projectId));
        List<Run> runs = new ArrayList();

        if (jsonArray != null) {
            int len = jsonArray.size();

            for (int i=0;i<len;i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                var testRun = (Run)new Run().deserialize(object, Run.class);
                runs.add(testRun);
            }
        }
        return runs;
    }

    //</editor-fold>

    //<editor-fold desc="Create runs">

    public Run addRun(String testRunName, boolean includeAllTestCases) {
        String url = String.format("add_run/%d", projectId);

        Run run = new Run(testRunName);
        run.include_all = includeAllTestCases;

        Map jsonBody = ObjectMapperWrapper.convertObjectToMap(run);
        JSONObject object = testRailUtils.sendPostRequestReturnJsonObject(url, jsonBody);
        return (Run)run.deserialize(object, Run.class);
    }

    public Run addRun(String testRunName, Long [] casesIds) {
        String url = String.format("add_run/%d", projectId);

        Run run = new Run(testRunName);
        run.include_all = false;
        run.case_ids = casesIds;

        Map jsonBody = ObjectMapperWrapper.convertObjectToMap(run);
        JSONObject object = testRailUtils.sendPostRequestReturnJsonObject(url, jsonBody);
        return (Run)run.deserialize(object, Run.class);
    }

    //</editor-fold>

    //<editor-fold desc="Update runs">

    public Run updateRun(Long runId, Long [] casesIds) {
        String url = String.format("update_run/%d", runId);

        Run run = new Run();
        run.case_ids = casesIds;
        run.include_all = false;

        Map jsonBody = ObjectMapperWrapper.convertObjectToMap(run);
        JSONObject object = testRailUtils.sendPostRequestReturnJsonObject(url, jsonBody);
        return (Run)run.deserialize(object, Run.class);
    }

    //</editor-fold>

    //<editor-fold desc="Delete runs">

    public void deleteRunById(long runId) {
        String url = String.format("delete_run/%d", runId);
        testRailUtils.sendPostRequestReturnJsonObject(url, new HashMap());
    }

    public void deleteAllPreviousRuns() {
        List<Run> runs = getRunsByProjectId();
        runs.forEach(run -> deleteRunById(run.id));
    }

    //</editor-fold>
}
