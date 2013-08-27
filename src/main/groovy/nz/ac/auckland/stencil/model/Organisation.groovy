package nz.ac.auckland.stencil.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Author: Marnix
 *
 * Organisation information
 */
class Organisation {

    /**
     * Organisational unit code ("BUSEC")
     */
    @JsonProperty("organisational_unit")
    String organisationalUnit;

    /**
     * Descriptive name of organisation
     */
    String name;

    /**
     * Color of organisational unit
     */
    String color;

    /**
     * List of domains that belong to this organisational unit. (optional key)
     */
    List<String> domains = []

}
