package nz.ac.auckland.stencil.services

import nz.ac.auckland.stencil.model.Organisation
import nz.ac.auckland.stencil.model.Themes
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Author: Marnix
 *
 * Unit tests for faculty query service
 */
@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration("classpath:spring-utils.xml")
class ThemesQueryServiceImplTest {

    /**
     * Local fixture
     */
    private final static String DEFAULT_ORGANISATION_URL = "file:./src/test/resources/organisations.json"

    /**
     * Mocked list of themes
     */
    private static def themesMock = new Themes(themes: [
            new Organisation(organisationalUnit: "1", name: "Business School", color: "#ff0000"),
            new Organisation(organisationalUnit: "2", name: "Faculty of Arts", color: "#00ff00")
    ]);

    /**
     * mock implementation of getAll
     */
    private def getAllMock = {
        return themesMock
    };

    /**
     * Typical JSON list
     */
    private def getThemesJsonTextMock = {
        return '''{
                "themes" : [
                    { "organisational_unit" : "1", "name" : "Business School", "color" : "#ff0000" },
                    { "organisational_unit" : "2", "name" : "Faculty of Arts", "color" : "#00ff00", "domains" : [ "www1", "www2", "www3" ] }
                ]
            }
         '''.trim()
    }

    /**
     * Test that we can retrieve by code successfully.
     */
    @Test
    public void testRetrievingByCode() {
        ThemesQueryServiceImpl service = [ themes : themesMock ] as ThemesQueryServiceImpl

        Organisation f1 = service.getOrganisationByCode("1")
        assert f1

        assert f1.organisationalUnit == "1"
        assert f1.color == "#ff0000"
        assert f1.name == "Business School"

        Organisation f2 = service.getOrganisationByCode("2")
        assert f2
        assert f2.organisationalUnit == "2"
        assert f2.color == "#00ff00"
        assert f2.name == "Faculty of Arts"
    }

    /**
     * Happy flow
     */
    @Test
    public void testParsingHappyFlow() {
        ThemesQueryServiceImpl service = [ getThemesJsonText: getThemesJsonTextMock ] as ThemesQueryServiceImpl
        service.loadOrganisationThemes();

        assert service.themes
        assert service.themes.themes.size() == 2

        assert service.themes.themes.get(0).organisationalUnit == "1"
        assert service.themes.themes.get(0).color == "#ff0000"
        assert service.themes.themes.get(0).name == "Business School"

        assert service.themes.themes.get(1).organisationalUnit == "2"
        assert service.themes.themes.get(1).color == "#00ff00"
        assert service.themes.themes.get(1).name == "Faculty of Arts"
        assert service.themes.themes.get(1).domains == [ "www1", "www2", "www3" ]
    }

    /**
     * Get all faculties
     */
    @Test
    public void testFacultyAll() {
        ThemesQueryServiceImpl service = [ themes : themesMock ] as ThemesQueryServiceImpl
        assert service.getAll() == themesMock.themes
    }

    /**
     * Should throw exception when null is returned from getthemesJsonText (when URL can't be found)
     */
    @Test(expected = IllegalStateException)
    public void testParsingNoResult() {
        ThemesQueryServiceImpl service = [ getThemesJsonText: { -> null } ] as ThemesQueryServiceImpl
        service.loadOrganisationThemes()
    }

    /**
     * Should throw exception when invalid json was returned from getThemesJsonText
     */
    @Test(expected = IllegalStateException)
    public void testParsingNoValidJson() {
        ThemesQueryServiceImpl service = [ getThemesJsonText: { -> 'garblediegoog' } ] as ThemesQueryServiceImpl
        service.loadOrganisationThemes()
    }

    /**
     * Assumes you have a faculties.json at src/tests/resources/organisations.json
     *
     * NOTE: if this test is failing, make sure you have setup your work directory for the
     * unit test run configuration to be the stencil-util base directory.
     */
    @Test
    public void testFromLocalFixture() {
        ThemesQueryServiceImpl service = [ getThemesInfoUrl: { -> return DEFAULT_ORGANISATION_URL } ] as ThemesQueryServiceImpl
        assert service.getThemesJsonText() != null

        // parse
        service.loadOrganisationThemes();

        assert service.themes.themes.size() == 10

        Organisation org = service.themes.themes.last()
        assert org.name == "New Start"
        assert org.color == "#3399CC"
        assert org.organisationalUnit == "NEWSTART"

        org = service.themes.themes.get(7)
        assert org.organisationalUnit == "SCIFAC"
        assert org.domains == [
                "science.auckland.ac.nz", "chemistry.auckland.ac.nz", "clinics.auckland.ac.nz",
                "cs.auckland.ac.nz", "leighmarine.auckland.ac.nz", "marine.auckland.ac.nz",
                "math.auckland.ac.nz", "phy.auckland.ac.nz", "psych.auckland.ac.nz",
                "sbs.auckland.ac.nz"
        ]

    }





}
