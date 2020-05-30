package com.teltov.testrail_api.testrail.utils;

import com.gurock.testrail.APIClient;
import com.teltov.testrail_api.properties.PropertyNotFoundException;
import com.teltov.testrail_api.testrail.properties.TestRailProperties;
import com.teltov.testrail_api.testrail.references.*;

public class TestRailManager {
    private final int projectId;
    private final TestRailUtils testRailUtils;
    private Runs runs;
    private Cases cases;
    private Sections sections;
    private Results results;
    private Tests tests;

    public TestRailManager() {
        TestRailProperties properties;

        try {
            properties = new TestRailProperties();
        }
        catch (PropertyNotFoundException ex) {
            ex.printStackTrace();
            properties = null;
        }

        assert properties != null;
        String url = properties.getUrl();
        String userName = properties.getUserName();
        String password = properties.getPassword();
        projectId = properties.getProjectId();

        APIClient client = new APIClient(url);
        client.setUser(userName);
        client.setPassword(password);
        testRailUtils = new TestRailUtils(client);
    }

    public Runs runs() {
        if (runs == null)
            runs = new Runs(testRailUtils, projectId);
        return runs;
    }

    public Cases cases() {
        if (cases == null)
            cases = new Cases(testRailUtils, projectId);
        return cases;
    }

    public Sections sections() {
        if (sections == null)
            sections = new Sections(testRailUtils, projectId);
        return sections;
    }

    public Results results() {
        if (results == null)
            results = new Results(testRailUtils, projectId);
        return results;
    }

    public Tests tests() {
        if (tests == null)
            tests = new Tests(testRailUtils, projectId);
        return tests;
    }

    public long getProjectId() {
        return projectId;
    }
}
