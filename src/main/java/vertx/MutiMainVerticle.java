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

    Router router;
    ThymeleafTemplateEngine thymeleafTemplateEngine;


    @Override

    public void start() {
        router = Router.router(vertx);
        thymeleafTemplateEngine=ThymeleafTemplateEngine.create(vertx);
        router.get("/exe/hello").handler(this::helloVertx);
        router.get("/exe/name").handler(this::helloName);
        vertx.createHttpServer().requestHandler(router).listen(8888);
    }

    void helloVertx(RoutingContext ctx) {
        var obj = new JsonObject();
        obj.put("name","where is jekyll?");
        System.out.println(ctx.getAcceptableContentType());
        System.out.println(ctx.getBodyAsString());
        thymeleafTemplateEngine.render(obj,"Templates/index.html",bufferAsyncResult -> {
            ctx.response().putHeader("content-type","text/html").end(bufferAsyncResult.result());
        });
        //ctx.request().response().end("hello world");

    }

    void helloName(RoutingContext ctx) {
        var obj = new JsonObject();
        obj.put("name","where is jekyll?");
        System.out.println(ctx.getAcceptableContentType());
        System.out.println(ctx.getBodyAsString());
        thymeleafTemplateEngine.render(obj,"Templates/index.html",bufferAsyncResult -> {
            ctx.response().putHeader("content-type","text/html").end(bufferAsyncResult.result());
        });
    }
}
