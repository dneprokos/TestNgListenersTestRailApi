package com.teltov.testrail_api.testrail.object_models;

public class Test extends _ModelBase {
    public Long id;
    public Long case_id;
    public Long run_id;

    public Test() {
    }

    public Test(Long id) {
        this.id = id;
    }
}
