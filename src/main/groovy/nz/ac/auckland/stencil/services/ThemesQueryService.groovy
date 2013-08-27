package nz.ac.auckland.stencil.services

import nz.ac.auckland.stencil.model.Organisation

/**
 * Author: Marnix
 *
 * Interface definition for Faculty Query Service
 */
interface ThemesQueryService {

    /**
     * Get faculty by faculty code
     *
     * @param code is the faculty code
     * @return a faculty instance or null when not found
     */
    public Organisation getOrganisationByCode(String code);

    /**
     * @return a list of all faculty instances
     */
    public List<Organisation> getAll();

}
