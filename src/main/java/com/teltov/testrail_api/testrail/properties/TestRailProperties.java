package com.teltov.testrail_api.testrail.properties;

import com.teltov.testrail_api.properties.PropertyManager;
import com.teltov.testrail_api.properties.PropertyNotFoundException;

public class TestRailProperties {
    private final String url;
    private final String userName;
    private final String password;
    private final int projectId;

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getProjectId() { return projectId; }

    public TestRailProperties() throws PropertyNotFoundException {
        PropertyManager properties = new PropertyManager("testrail.properties");
        url = properties.getPropertyValue("base_url");
        userName = properties.getPropertyValue("user_name");
        password = properties.getPropertyValue("password");
        projectId = Integer.parseInt(properties.getPropertyValue("projectId"));
    }
}
