package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;

public class StaticVerticle extends AbstractVerticle {
    Router router;
    ThymeleafTemplateEngine thymeleafTemplateEngine;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        router = Router.router(vertx);
        thymeleafTemplateEngine = ThymeleafTemplateEngine.create(vertx);
        router.route("/static/*").handler(StaticHandler.create());
        router.route("/exe").handler(req -> {
            var obj = new JsonObject();

            obj.put("name", "<div><h1>Hello World from backend</h1></div>");
            obj.put("another", "<form >userId:<input type=text><input type=submit></form>");
            thymeleafTemplateEngine.render(obj,
                    "Templates/index.html",
                    bufferAsyncResult -> {
                        req.response()
                                .putHeader("content-type", "text/html")
                                .end(bufferAsyncResult.result());
                    });

        });

        vertx.createHttpServer().requestHandler(router).listen(8889, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 8889");
            } else {
                startPromise.fail(http.cause());
            }
        });
    }
}
