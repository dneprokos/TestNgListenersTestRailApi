package com.teltov.testrail_api.testrail.object_models;

public class Result extends _ModelBase {
    public int status_id;
    public String comment;
    public String defects;

    public Result() {
    }

    public Result(int status_id) {
        this.status_id = status_id;
    }
}
