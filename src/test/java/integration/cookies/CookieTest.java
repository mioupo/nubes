package integration.cookies;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import integration.VertxMVCTestBase;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class CookieTest extends VertxMVCTestBase {
    @Test
    public void testNoCookieValue(TestContext context) {
        Async async = context.async();
        client().getNow("/cookies/noCookie", response -> {
            assertNull(response.headers().get("Set-Cookie"));
            async.complete();
        });
    }

    @Test
    public void testSetCookieValue(TestContext context) {
        Async async = context.async();
        client().getNow("/cookies/setCookie", response -> {
            assertNotNull(response.headers().get("Set-Cookie"));
            async.complete();
        });
    }

    @Test
    public void testReadCookie(TestContext context) {
        String key = "dog";
        String value = "Cubitus";
        Async async = context.async();
        client().get("/cookies/echo", response -> {
            Buffer buff = Buffer.buffer();
            response.handler(buffer -> {
                buff.appendBuffer(buffer);
            });
            response.endHandler(handler -> {
                assertEquals(value, buff.toString("UTF-8"));
                async.complete();
            });
        }).putHeader("Cookie", key + "=" + value).end();
    }
}
