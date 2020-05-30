package com.teltov.testrail_api.testrail.testng;

import com.teltov.testrail_api.testrail.utils.TestStatus;

public class TestResultStatusConverter {
    public static TestStatus convert(String xmlStatus) throws NoSuchFieldException {
        switch (xmlStatus) {
            case "PASS":
                return TestStatus.PASSED;
            case "FAIL":
                return TestStatus.FAILED;
            default:
                throw new NoSuchFieldException(String.format("Unsupported XML status %s", xmlStatus));
        }
    }
}
