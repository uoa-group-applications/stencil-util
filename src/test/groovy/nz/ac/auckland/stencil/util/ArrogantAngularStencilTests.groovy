package nz.ac.auckland.stencil.util

import nz.ac.auckland.grayles.services.AngularTemplates
import nz.ac.auckland.grayles.services.I18nClientMessageMap
import nz.ac.auckland.stencil.StencilService
import org.junit.Test

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class ArrogantAngularStencilTests {
  @Test
  public void basic() {
    AngularTemplates templates = mock(AngularTemplates)
    I18nClientMessageMap messages = mock(I18nClientMessageMap)
    HttpServletRequest req = mock(HttpServletRequest)
    HttpServletResponse resp = mock(HttpServletResponse)

    boolean called = false

    StencilService ps = [
      renderJsp: { HttpServletRequest request, HttpServletResponse response, String page, Map<String, Object> model ->
        assert page == "fred"
        assert model.i18n == "messages"
        assert model.templates == "angular"

        called = true
      }
    ] as StencilService

    ArrogantAngularStencil stencil = new ArrogantAngularStencil() {
      @Override
      protected String getPrefixStencilLocation(String currentName) {
        return "fred"
      }
    }

    stencil.pageService = ps
    stencil.angularTemplates = templates
    stencil.i18n = messages

    assert stencil.defaultView == "fred"

    when(messages.messagesMapAsJson).thenReturn("messages")
    when(templates.angularTemplatesAsJson).thenReturn("angular")

    stencil.render(req, resp, null)

    assert called // too complex to use verify
  }
}
