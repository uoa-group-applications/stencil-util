package nz.ac.auckland.stencil.util

import net.stickycode.stereotype.Configured
import nz.ac.auckland.common.stereotypes.UniversityComponent

/**
 * Author: Marnix
 *
 * Configuration for util elements
 */
@UniversityComponent
class UtilConfig {

    /**
     * Default faculty URL
     */
    private static final String DEFAULT_THEMES_INFO_URL = "http://cdn.auckland.ac.nz/uoa-style/themes.json"

    /**
     * Organisation themes URL is used to store the location of the themes information
     */
    @Configured
    String organisationThemesUrl = DEFAULT_THEMES_INFO_URL

}
