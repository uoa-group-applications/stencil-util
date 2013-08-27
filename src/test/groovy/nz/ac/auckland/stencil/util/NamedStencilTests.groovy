package nz.ac.auckland.stencil.util

import org.junit.Test

import javax.servlet.http.HttpServletRequest

/**

 * author: Richard Vowles - http://gplus.to/RichardVowles
 */
class NamedStencilTests {
  class Base extends NamedStencil {

    @Override
    Map<String, Object> render(HttpServletRequest request, Map<String, String> pathParameters) {
      return null  //To change body of implemented methods use File | Settings | File Templates.
    }
  }
  class FredAndWilma extends Base {}

  class myOtterLikesMe extends Base {}

  class imalllowercaseactually extends Base {}

  class LooseTheStencil extends Base {
  }

  class CharlieeeeeeeePage extends Base {
    @Override
    protected String getPrefixStencilLocation(String currentName) {
      return "Unicorn" + currentName
    }
  }

  @Test
  public void nameTranslationWorksProperly() {
    def f = new FredAndWilma()
    assert f.defaultView == NamedStencil.STENCIL_LOCATION + "fred_and_wilma.jsp"
    def m = new myOtterLikesMe()
    assert m.defaultView == NamedStencil.STENCIL_LOCATION + 'my_otter_likes_me.jsp'
    def i = new imalllowercaseactually()
    assert i.defaultView == NamedStencil.STENCIL_LOCATION + "imalllowercaseactually.jsp"
    assert new LooseTheStencil().defaultView == NamedStencil.STENCIL_LOCATION + 'loose_the.jsp'
    assert new CharlieeeeeeeePage().defaultView == "Unicorncharlieeeeeeee"
  }
}
