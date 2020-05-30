package com.teltov.testrail_api.testrail.helpers;

import com.teltov.testrail_api.testrail.object_models.Case;
import com.teltov.testrail_api.testrail.object_models.Test;
import com.teltov.testrail_api.testrail.testng.TestResult;
import com.teltov.testrail_api.testrail.testng.TestResultStatusConverter;
import com.teltov.testrail_api.testrail.utils.TestMethodAndClass;
import com.teltov.testrail_api.testrail.utils.TestRailManager;
import com.teltov.testrail_api.testrail.utils.TestStatus;
import com.teltov.testrail_api.utils.StringExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TestResultHelpers {
    private final TestRailManager testRailApi;
    private static final Logger LOG = LoggerFactory.getLogger(TestResultHelpers.class);

    public TestResultHelpers(TestRailManager testRailApi) {
        this.testRailApi = testRailApi;
    }

    public void setTestResult(HashMap<String, Long> sectionNameIdPair, List<Case> currentRunCases,
                              List<Test> currentRunTests, TestStatus testStatus, TestMethodAndClass testMethodAndClass) {
        String caseName = testMethodAndClass.testMethodName;
        String sectionName = StringExtension.getClassNameFromPackageName(testMethodAndClass.testClassName);
        findTestAndSetResult(testRailApi, sectionNameIdPair, sectionName, caseName, testStatus,
                currentRunCases, currentRunTests);
    }

    public void setTestResults(List<TestResult> xmlTestResults, HashMap<String, Long> sectionNameIdPair,
                               List<Case> currentRunCases, List<Test> currentRunTests) {
        xmlTestResults.forEach(testResult -> {
            TestStatus testStatus = null;
            try {
                testStatus = TestResultStatusConverter.convert(testResult.testStatus);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            String sectionName = StringExtension.getClassNameFromPackageName(testResult.testClassName);
            String testName = testResult.testMethodName;

            findTestAndSetResult(testRailApi, sectionNameIdPair, sectionName, testName,
                    testStatus, currentRunCases, currentRunTests);
        });
    }

    private void findTestAndSetResult(TestRailManager testRailApi, HashMap<String, Long> sectionNameIdPair,
                                 String sectionName, String testName, TestStatus testStatus,
                                 List<Case> currentRunCases, List<Test> currentRunTests) {
        Long sectionId = sectionNameIdPair.get(sectionName);

        Long caseId = currentRunCases.stream()
                .filter(aCase ->
                        aCase.title.equals(testName) && aCase.section_id.equals(sectionId))
                .map(t -> t.id)
                .collect(Collectors.toList())
                .get(0);

        Test test = currentRunTests.stream()
                .filter(aTest -> aTest.case_id.equals(caseId))
                .collect(Collectors.toList())
                .get(0);
        testRailApi.results().addResult(test.id, testStatus);
    }
}
