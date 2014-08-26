package nz.ac.auckland.stencil.util

import javax.inject.Inject
import javax.servlet.http.HttpServletRequest
import nz.ac.auckland.lmz.services.AngularTemplates
import nz.ac.auckland.lmz.services.I18nClientMessageMap

/**
 * Class assumes all the data you will need is the angular templates and the messages
 */
class ArrogantAngularStencil extends NamedStencil {
  @Inject AngularTemplates angularTemplates
  @Inject I18nClientMessageMap i18n

  @Override
  Map<String, Object> render(HttpServletRequest request, Map<String, String> pathParameters) {
    return [templates: angularTemplates.angularTemplatesAsJson, i18n: i18n.messagesMapAsJson]
  }
}
