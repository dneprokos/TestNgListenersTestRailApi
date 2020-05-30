package com.teltov.testrail_api.testrail.references;

import com.teltov.testrail_api.testrail.object_models.Case;
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

public class Cases {
    private final TestRailUtils testRailUtils;
    private final int projectId;
    private static final Logger LOG = LoggerFactory.getLogger(Cases.class);

    public Cases(TestRailUtils testRailUtils, int projectId) {
        LOG.info(String.format("Instantiating new Cases class instance for project %s", projectId));
        this.testRailUtils = testRailUtils;
        this.projectId = projectId;
    }

    //<editor-fold desc="Get test cases">

    public Case addCase(Long sectionId, String title) {
        String url = String.format("add_case/%d", sectionId);

        Case newCase = new Case();
        newCase.title = title;

        Map jsonBody = ObjectMapperWrapper.convertObjectToMap(newCase);
        JSONObject object = testRailUtils.sendPostRequestReturnJsonObject(url, jsonBody);
        return (Case) newCase.deserialize(object, Case.class);
    }

    //</editor-fold>

    //<editor-fold desc="Get test cases">

    public List<Case> getCases() {
        return getCases(this.projectId);
    }

    public List<Case> getCases(long projectId) {
        JSONArray jsonArray = testRailUtils.sendGetRequestReturnJsonArray(String.format("get_cases/%d", projectId));

        List<Case> aCases = new ArrayList<>();
        if (jsonArray != null) {
            int len = jsonArray.size();
            for (int i=0;i<len;i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                var testCase = new Case().deserialize(object, Case.class);
                aCases.add((Case) testCase);
            }
        }
        return aCases;
    }

    public List<Case> getCases(long projectId, Long sectionId) {
        JSONArray jsonArray = testRailUtils
                .sendGetRequestReturnJsonArray(String.format("get_cases/%d&section_id=%d", projectId, sectionId));

        List<Case> aCases = new ArrayList<>();
        if (jsonArray != null) {
            int len = jsonArray.size();
            for (int i=0;i<len;i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                var testCase = new Case().deserialize(object, Case.class);
                aCases.add((Case) testCase);
            }
        }
        return aCases;
    }

    //</editor-fold>

    //<editor-fold desc="Delete test cases">

    public void deleteCaseById(long caseId) {
        String url = String.format("delete_case/%d", caseId);
        testRailUtils.sendPostRequestReturnJsonObject(url, new HashMap());
    }

    public void deleteAllCases() {
        List<Case> allCases = getCases();
        allCases.forEach(aCase -> deleteCaseById(aCase.id));
    }

    //</editor-fold>
}
