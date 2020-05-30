package com.teltov.listeners;

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
import com.teltov.testrail_api.testrail.utils.TestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TestRailListener implements ITestListener {
    private final TestRailManager testRailApi;
    private final SectionHelper sectionHelper;
    private final RunHelpers runHelpers;
    private final TestHelper testHelper;
    private final TestResultHelpers testResultHelper;
    private static final Logger LOG = LoggerFactory.getLogger(TestRailListener.class);
    private HashMap<String, Long> sectionNameIdPair;
    private List<Test> currentRunTests;
    private List<Case> currentRunCases;

    public TestRailListener() {
        testRailApi = new TestRailManager();
        sectionNameIdPair = new HashMap<>();
        sectionHelper = new SectionHelper(testRailApi);
        runHelpers = new RunHelpers(testRailApi);
        testHelper = new TestHelper(testRailApi);
        testResultHelper = new TestResultHelpers(testRailApi);
    }

    @Override
    public void onStart(ITestContext context) {
        createSectionsAndAddToKeyValuePair(context);
        currentRunCases = createTestsAndAddToFutureRunCollection(context);
        Run run = runHelpers.createRun(currentRunCases, true);
        currentRunTests = testRailApi.tests().getTests(run.id);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        setTestResult(result, TestStatus.PASSED);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        setTestResult(result, TestStatus.FAILED);
    }


    private void setTestResult(ITestResult result, TestStatus testStatus) {
        var testClassAndMethod = new TestMethodAndClass(result.getTestClass().getName(), result.getName());
        testResultHelper.setTestResult(sectionNameIdPair, currentRunCases, currentRunTests,
                testStatus, testClassAndMethod);
    }

    private void createSectionsAndAddToKeyValuePair(ITestContext context) {
        List<String> expectedSectionNames = sectionHelper.convertContextPackageClassNamesToClassNames(context);
        List<Section> actualSections = testRailApi.sections().getSectionsByProjectId();
        sectionNameIdPair = sectionHelper.createMissingSections(actualSections, expectedSectionNames);
    }

    private List<Case> createTestsAndAddToFutureRunCollection(ITestContext context) {
        List<TestMethodAndClass> expectedMethodClassCollection = new ArrayList<>();
        Arrays.stream(context.getAllTestMethods())
                .forEach(method -> {
                    var testClassAndMethod = new TestMethodAndClass(method.getTestClass().getName(), method.getMethodName());
                    expectedMethodClassCollection.add(testClassAndMethod);
                });
        return testHelper.createTestsForTestRun(sectionNameIdPair, expectedMethodClassCollection);
    }
}
