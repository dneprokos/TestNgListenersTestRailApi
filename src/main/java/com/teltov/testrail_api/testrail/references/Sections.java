package com.teltov.testrail_api.testrail.references;

import com.teltov.testrail_api.testrail.object_models.Section;
import com.teltov.testrail_api.testrail.utils.ObjectMapperWrapper;
import com.teltov.testrail_api.testrail.utils.TestRailUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sections {
    private final TestRailUtils testRailUtils;
    private final int projectId;
    private static final Logger LOG = LoggerFactory.getLogger(Runs.class);

    public Sections(TestRailUtils testRailUtils, int projectId) {
        LOG.info(String.format("Instantiating new Sections class instance for project %s", projectId));
        this.testRailUtils = testRailUtils;
        this.projectId = projectId;
    }

    //<editor-fold desc="Get sections">

    public List<Section> getSectionsByProjectId() {
        return getSectionsByProjectId(this.projectId);
    }

    public List<Section> getSectionsByProjectId(long projectId) {
        JSONArray jsonArray = testRailUtils.sendGetRequestReturnJsonArray(String.format("get_sections/%d", projectId));

        List<Section> sections = new ArrayList<>();
        if (jsonArray != null) {
            int len = jsonArray.size();
            for (int i=0;i<len;i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                var section = new Section().deserialize(object, Section.class);
                sections.add((Section) section);
            }
        }
        return sections;
    }

    public Section getSectionById(Long sectionId) {
        JSONObject sectionJson = testRailUtils.sendGetRequestReturnJsonObject(String.format("get_section/%d", sectionId));
        return (Section) new Section().deserialize(sectionJson, Section.class);
    }

    //</editor-fold>


    //<editor-fold desc="Create sections">

    public Section addSection(String name) {
        String url = String.format("add_section/%d", projectId);

        var section = new Section();
        section.name = name;

        Map jsonBody = ObjectMapperWrapper.convertObjectToMap(section);
        JSONObject object = testRailUtils.sendPostRequestReturnJsonObject(url, jsonBody);
        return (Section) section.deserialize(object, Section.class);
    }

    //</editor-fold>


    //<editor-fold desc="Update sections">

    //</editor-fold>


    //<editor-fold desc="Delete sections">

    //</editor-fold>

}
