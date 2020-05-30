package com.teltov.testrail_api.testrail.utils;

public enum TestStatus {
    PASSED(1),
    UNTESTED(3),
    RETEST(4),
    FAILED(5);

    int status_id;

    TestStatus(int status_id) {
        this.status_id = status_id;
    }

    public int getStatus() {
        return status_id;
    }

}
