package com.teltov.testrail_api.testrail.helpers;

import com.teltov.testrail_api.testrail.object_models.Case;
import com.teltov.testrail_api.testrail.utils.TestMethodAndClass;
import com.teltov.testrail_api.testrail.utils.TestRailManager;
import com.teltov.testrail_api.utils.StringExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TestHelper {
    private final TestRailManager testRailApi;
    private static final Logger LOG = LoggerFactory.getLogger(TestHelper.class);

    public TestHelper(TestRailManager testRailApi) {
        this.testRailApi = testRailApi;
    }

    public List<Case> createTestsForTestRun(HashMap<String, Long> sectionNameIdPair, List<TestMethodAndClass> expectedClassMethodName) {
        List<Case> cases = testRailApi.cases().getCases();
        List<Case> currentRunCases = new ArrayList<>();

        expectedClassMethodName
                .forEach(aTest -> {
                    String testName = aTest.testMethodName;
                    String sectionName = StringExtension.getClassNameFromPackageName(aTest.testClassName);
                    long expectedSectionId = sectionNameIdPair.get(sectionName);

                    List<Case> foundCase = cases.stream().filter(test -> test.title.equals(testName)
                            && test.section_id.equals(expectedSectionId))
                            .collect(Collectors.toList());
                    int foundCaseSize = foundCase.size();

                    if (foundCaseSize == 1) {
                        LOG.info(String.format("Case with name: %s already exists for section name %s", testName, sectionName));
                        currentRunCases.add(foundCase.get(0));
                    }
                    else if (foundCaseSize == 0) {
                        LOG.info(String.format("Case with name: %s does not exist for section name %s. Case will be added", testName, sectionName));
                        Case newCase = testRailApi.cases().addCase(expectedSectionId, testName);
                        currentRunCases.add(newCase);
                    }
                    else {
                        LOG.warn(String.format("Expected found cases size should be 0 or 1. But found %d", foundCaseSize));
                    }
                });
        return currentRunCases;
    }
}
