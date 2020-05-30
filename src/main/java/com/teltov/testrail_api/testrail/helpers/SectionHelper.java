package com.teltov.testrail_api.testrail.helpers;

import com.teltov.testrail_api.testrail.object_models.Section;
import com.teltov.testrail_api.testrail.utils.TestRailManager;
import com.teltov.testrail_api.utils.StringExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SectionHelper {
    private final TestRailManager testRailApi;
    private static final Logger LOG = LoggerFactory.getLogger(SectionHelper.class);

    public SectionHelper(TestRailManager testRailApi) {
        this.testRailApi = testRailApi;
    }

    public HashMap<String, Long> createMissingSections(List<Section> actualSections, List<String> expectedSectionNames) {
        HashMap<String, Long> sectionNameIdPair = new HashMap<>();

        expectedSectionNames.forEach(sectionName -> {
            List<Section> filerSection = actualSections
                    .stream()
                    .filter(sectionObj -> sectionObj.name.equals(sectionName))
                    .collect(Collectors.toList());
            Section section = null;

            if (filerSection.size() == 0) {
                LOG.info(String.format("Section with name %s does not exist and will be created", sectionName));
                section = testRailApi.sections().addSection(sectionName);
            } else if (filerSection.size() == 1) {
                LOG.info(String
                        .format("Section with name %s no need to be created because it's already exists",
                                sectionName));
                section = filerSection.get(0);
            } else LOG.warn(String.format("Expected sections size is 0 or 1. But found %d", filerSection.size()));

            try {
                sectionNameIdPair.put(section.name, section.id);
            } catch (NullPointerException exception) {
                LOG.error(exception.getMessage());
            }
        });
        return sectionNameIdPair;
    }

    public List<String> convertContextPackageClassNamesToClassNames(ITestContext context){
        List<String> expectedNames = new ArrayList<>();
        context.getCurrentXmlTest().getClasses().forEach(xmlClass -> {
            String sectionName = StringExtension.getClassNameFromPackageName(xmlClass.getName());
            expectedNames.add(sectionName);
        });
        return expectedNames;
    }
}
