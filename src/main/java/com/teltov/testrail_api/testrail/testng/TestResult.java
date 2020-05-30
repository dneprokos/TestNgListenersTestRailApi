package com.teltov.testrail_api.testrail.testng;

public class TestResult {
    public String testClassName;
    public String testMethodName;
    public String testStatus;
    public String exception;

    public TestResult(String testClassName, String testMethodName, String testStatus, String exception) {
        this.testClassName = testClassName;
        this.testMethodName = testMethodName;
        this.testStatus = testStatus;
        this.exception = exception;
    }
}
