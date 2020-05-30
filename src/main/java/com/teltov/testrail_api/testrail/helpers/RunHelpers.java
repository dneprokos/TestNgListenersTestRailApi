package com.teltov.testrail_api.testrail.helpers;

import com.teltov.testrail_api.testrail.object_models.Case;
import com.teltov.testrail_api.testrail.object_models.Run;
import com.teltov.testrail_api.testrail.utils.TestRailManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;

public class RunHelpers {
    private final TestRailManager testRailApi;
    private static final Logger LOG = LoggerFactory.getLogger(RunHelpers.class);

    public RunHelpers(TestRailManager testRailApi) {
        this.testRailApi = testRailApi;
    }

    public Run createRun(List<Case> currentRunCases, boolean deleteAllPreviousRuns) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String runName = String.format("RegressionRun-%s", timestamp);
        return createRun(currentRunCases, deleteAllPreviousRuns, runName);
    }

    public Run createRun(List<Case> currentRunCases, boolean deleteAllPreviousRuns, String runName) {
        if (deleteAllPreviousRuns) {
            testRailApi.runs().deleteAllPreviousRuns();
        }

        Long [] caseIds = currentRunCases
                .stream()
                .map(aCase -> aCase.id)
                .toArray(Long[]::new);

        return testRailApi.runs().addRun(runName, caseIds);
    }
}
