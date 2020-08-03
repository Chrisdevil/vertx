package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;
import lombok.extern.slf4j.Slf4j;

import java.time.Year;

@Slf4j
public class MutiMainVerticle extends AbstractVerticle {

    Router router = Router.router(vertx);
    ThymeleafTemplateEngine thymeleafTemplateEngine = ThymeleafTemplateEngine.create(vertx);


    @Override

    public void start() {

        router.get("/exe/hello").handler(this::helloVertx);
        router.get("/exe/name").handler(this::helloName);
        vertx.createHttpServer().requestHandler(router).listen(8888);

    }

    void helloVertx(RoutingContext ctx) {
        var obj = new JsonObject();
        obj.put("name","where is jekyll?");
        thymeleafTemplateEngine.render(obj,"Templates/index.html",bufferAsyncResult -> {
            ctx.request().response().end(bufferAsyncResult.result());
        });
        //ctx.request().response().end("hello world");

    }

    void helloName(RoutingContext ctx) {
        ctx.request().response().end("hello name");
    }
}
