package com.teltov.testrail_api.utils;

public class StringExtension {
    /**
     * Returns only class name itself without package
     * @param fullName full package name
     * @return returns the last part of the package
     */
    public static String getClassNameFromPackageName(String fullName) {
        String [] fullNameSplit = fullName.split("\\.");
        return fullNameSplit[fullNameSplit.length -1];
    }
}
