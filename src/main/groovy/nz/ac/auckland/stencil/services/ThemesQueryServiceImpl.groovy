package nz.ac.auckland.stencil.services

import groovy.transform.CompileStatic
import nz.ac.auckland.common.stereotypes.UniversityComponent
import nz.ac.auckland.stencil.model.Organisation
import nz.ac.auckland.stencil.model.Themes
import nz.ac.auckland.stencil.util.UtilConfig
import nz.ac.auckland.util.JacksonException
import nz.ac.auckland.util.JacksonHelper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.annotation.PostConstruct
import javax.inject.Inject

/**
 * Author: Marnix
 *
 * This service reads an external source that can be queried regarding
 * basic faculty information. This external source is configurable.
 */
@CompileStatic
@UniversityComponent
class ThemesQueryServiceImpl implements ThemesQueryService {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ThemesQueryServiceImpl.class);

    /**
     * Bind utility configuration here
     */
    @Inject UtilConfig utilConfiguration;

    /**
     * Data structure that contains all faculty information
     */
    Themes themes;

    /**
     * Load organisations theme information
     */
    @PostConstruct
    public void loadOrganisationThemes() {
        String themesJsonText = this.getThemesJsonText();

        if (!themesJsonText) {
            throw new IllegalStateException("Was unable to retrieve faculty information from: " + this.themesInfoUrl);
        }

        try {
            this.themes = JacksonHelper.deserialize(themesJsonText, Themes);
        }
        catch (JacksonException jEx) {
            LOG.error("Could not load the faculty list from ${this.themesInfoUrl}")
            throw new IllegalStateException("Could not load the faculty list from ${this.themesInfoUrl}", jEx);
        }
    }

    /**
     * Get organisation by organisation code
     *
     * @param organisationalUnit is the code of the organisation to retrieve the instance for
     * @return an Organisation instance or null when not found
     */
    @Override
    public Organisation getOrganisationByCode(String organisationalUnit) {
        return this.getAll()?.find { Organisation fac ->
            fac.organisationalUnit == organisationalUnit
        }
    }

    /**
     * @return a list of all organisations or null when they are not available.
     */
    @Override
    List<Organisation> getAll() {
        return this.themes?.themes
    }

    /**
     * @return the location from which to load the faculty information
     */
    protected String getThemesInfoUrl() {
        return utilConfiguration?.organisationThemesUrl;
    }

    /**
     * Load a remote JSON document
     *
     * @return a json string with faculty information
     */
    protected String getThemesJsonText() {
        try {
            return new URL(this.themesInfoUrl).openStream().text?.trim()
        }
        catch (Exception ex) {
            LOG.error("Unable to read JSON from ${this.themesInfoUrl}", ex);
        }
        return null;

    }

}
