package com.teltov.main;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.teltov.testrail_api.testrail.testng.TestRailTestNgReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private final String defaultPath = "/target/surefire-reports/testng-results.xml";

    @Parameter(names = "--help", help = true, description = "Invokes help command")
    private boolean help = false;

    @Parameter(names = "--reportDir", description = "Path to testng-results.xml.")
    private String reportDir = System.getProperty("user.dir") + defaultPath;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(main)
                .build();
        jCommander.setProgramName("TestRailRunGenerator");
        try {
            jCommander.parse(args);
        }
        catch (ParameterException ex) {
            LOG.error(ex.getMessage());
            jCommander.usage();
            return;
        }

        if (main.help) {
            jCommander.usage();
            return;
        }

        main.run();
    }

    private void run() {
        var testRailTesNgApi = new TestRailTestNgReporter(reportDir);
        testRailTesNgApi.createTestRailRunFromTestNgReport(true);
    }
}
