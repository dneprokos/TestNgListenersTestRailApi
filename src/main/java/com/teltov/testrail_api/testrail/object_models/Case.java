package com.teltov.testrail_api.testrail.object_models;

public class Case extends _ModelBase {
    public Long id;
    public String title;
    public Long section_id;
    public Long suite_id;

    public Case() {
    }

    public Case(String title) {
        this.title = title;
    }
}
