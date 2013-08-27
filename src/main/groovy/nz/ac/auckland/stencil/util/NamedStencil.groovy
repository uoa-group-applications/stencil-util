package nz.ac.auckland.stencil.util

import nz.ac.auckland.stencil.Stencil
import nz.ac.auckland.stencil.StencilService

import javax.inject.Inject
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**

 * author: Richard Vowles - http://gplus.to/RichardVowles
 */
abstract class NamedStencil implements Stencil {
  public static final STENCIL_LOCATION = "/WEB-INF/stencils/"

  @Inject StencilService pageService

  String defaultView

  public NamedStencil() {
    StringBuilder sb = new StringBuilder()

    String clazzName = this.getClass().simpleName

    if (clazzName.endsWith("Stencil"))
      clazzName = clazzName.substring(0, clazzName.length() - "Stencil".length())
    else if (clazzName.endsWith("Page"))
      clazzName = clazzName.substring(0, clazzName.length() - "Page".length())

    for(Character c : clazzName.chars) {
      if (c.isUpperCase()) {
        if (sb.size() > 0)
          sb.append('_')
        sb.append(c.toLowerCase())
      } else {
        sb.append(c)
      }
    }

    defaultView = getPrefixStencilLocation(sb.toString())
  }

  protected String getPrefixStencilLocation(String currentName) {
    return STENCIL_LOCATION + currentName + ".jsp"
  }

  @Override
  void render(HttpServletRequest request, HttpServletResponse response, Map<String, String> pathParameters) {
    Map<String, Object> model = render(request, pathParameters)

    pageService.renderJsp(request, response, defaultView, model)
  }

  abstract Map<String, Object> render(HttpServletRequest request, Map<String, String> pathParameters)
}
