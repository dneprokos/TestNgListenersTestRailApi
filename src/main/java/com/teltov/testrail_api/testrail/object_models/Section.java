package com.teltov.testrail_api.testrail.object_models;

public class Section extends _ModelBase {
    public Long id;
    public Long suite_id;
    public String name;
    public String description;
    public Long parent_id;

    public Section() {
    }

    public Section(String name) {
        this.name = name;
    }
}
