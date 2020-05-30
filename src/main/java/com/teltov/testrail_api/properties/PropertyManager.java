package com.teltov.testrail_api.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private Properties properties;
    private String propertyFileName;

    public PropertyManager(String fileName) {
        try (InputStream input = PropertyManager.class.getClassLoader().getResourceAsStream(fileName)) {
            properties = new Properties();
            propertyFileName = fileName;

            if (input == null) {
                System.out.println(String.format("Sorry, unable to find %s", fileName));
            }
            //load a properties file from class path, inside static method
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getPropertyValue(String key) throws PropertyNotFoundException {
        String value = properties.getProperty(key);

        if (value == null) {
            throw new PropertyNotFoundException(String.format("Property key '%s' was not found in %s." +
                    "Please make sure correct property name and value were specified", key, propertyFileName));
        }
        return value;
    }
}
