package com.teltov.testrail_api.testrail.testng;

import com.teltov.testrail_api.testrail.helpers.RunHelpers;
import com.teltov.testrail_api.testrail.helpers.SectionHelper;
import com.teltov.testrail_api.testrail.helpers.TestHelper;
import com.teltov.testrail_api.testrail.helpers.TestResultHelpers;
import com.teltov.testrail_api.testrail.object_models.Case;
import com.teltov.testrail_api.testrail.object_models.Run;
import com.teltov.testrail_api.testrail.object_models.Section;
import com.teltov.testrail_api.testrail.object_models.Test;
import com.teltov.testrail_api.testrail.utils.TestMethodAndClass;
import com.teltov.testrail_api.testrail.utils.TestRailManager;
import com.teltov.testrail_api.utils.StringExtension;
import com.teltov.testrail_api.utils.TestNgResultsXmlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestRailTestNgReporter {
    private final String path;
    private final TestRailManager testRailApi;
    private final Logger LOG = LoggerFactory.getLogger(TestRailTestNgReporter.class);
    private List<TestResult> xmlTestResults;
    private HashMap<String, Long> sectionNameIdPair;

    public TestRailTestNgReporter(String path) {
        this.path = path;
        this.testRailApi = new TestRailManager();
    }

    public void createTestRailRunFromTestNgReport(Boolean removePreviousRuns) {
        //Deserialize xml test results to Test Result objects collections
        xmlTestResults = TestNgResultsXmlParser.convertXmlToTestResult(path);

        createSectionsThatDoesNotExist();
        List<Case> currentRunCases = createMissingTestsAndAddToFutureRunCollection();
        var runHelpers = new RunHelpers(testRailApi);
        Run run = runHelpers.createRun(currentRunCases, removePreviousRuns);
        List<Test> currentRunTests = testRailApi.tests().getTests(run.id);
        var resultHelpers = new TestResultHelpers(testRailApi);
        resultHelpers.setTestResults(xmlTestResults, sectionNameIdPair, currentRunCases, currentRunTests);
    }

    private void createSectionsThatDoesNotExist() {
        var sectionHelper = new SectionHelper(testRailApi);
        List<Section> actualTestRailSections = testRailApi.sections().getSectionsByProjectId();
        List<String> expectedNames = new ArrayList<>();
        xmlTestResults.forEach(testResult -> {
            String expectedName = StringExtension.getClassNameFromPackageName(testResult.testClassName);
            if (!expectedNames.contains(expectedName))
                expectedNames.add(expectedName);
        });

        sectionNameIdPair = sectionHelper.createMissingSections(actualTestRailSections, expectedNames);
    }


    private List<Case> createMissingTestsAndAddToFutureRunCollection() {
        var testHelper = new TestHelper(testRailApi);
        List<TestMethodAndClass> expectedMethodAndClassCollection = new ArrayList<>();
        xmlTestResults.forEach(testResult -> {
            var methodClass = new TestMethodAndClass(
                    StringExtension.getClassNameFromPackageName(testResult.testClassName),
                    testResult.testMethodName);
            expectedMethodAndClassCollection.add(methodClass);
        });

        return testHelper.createTestsForTestRun(sectionNameIdPair, expectedMethodAndClassCollection);
    }
}
