package com.teltov.testrail_api.testrail.object_models;

public class Run extends _ModelBase {
    public Long id;
    public Long suite_id;
    public String name;
    public boolean include_all;
    public Long plan_id;
    public Long passed_count;
    public Long blocked_count;
    public Long untested_count;
    public Long retest_count;
    public Long failed_count;
    public Long [] case_ids;

    public Run() {
    }

    public Run(String name) {
        this.name = name;
    }
}
