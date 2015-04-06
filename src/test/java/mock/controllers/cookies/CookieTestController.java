package mock.controllers.cookies;

import io.vertx.ext.apex.RoutingContext;
import io.vertx.ext.apex.Cookie;
import io.vertx.mvc.annotations.Controller;
import io.vertx.mvc.annotations.cookies.Cookies;
import io.vertx.mvc.annotations.routing.Path;
import io.vertx.mvc.controllers.AbstractController;

@Controller("/cookies/")
public class CookieTestController extends AbstractController {

    @Path("noCookie")
    public void noCookie(RoutingContext context) {
        context.next();
    }

    @Path("setCookie")
    @Cookies
    public void setCookie(RoutingContext context) {
        context.addCookie(Cookie.cookie("dog", "Rantanplan"));
        context.next();
    }

    @Path("echo")
    @Cookies
    public void echoCookies(RoutingContext context) {
        String dog = context.getCookie("dog").getValue();
        renderText(context, dog);
    }

}