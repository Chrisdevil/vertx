package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MutiMainVerticle extends AbstractVerticle {

    Router router = Router.router(vertx);

    @Override

    public void start() {

        router.get("/exe/hello").handler(this::helloVertx);
        router.get("/exe/name").handler(this::helloName);
        vertx.createHttpServer().requestHandler(router).listen(8888);

    }

    void helloVertx(RoutingContext ctx) {
        vertx.eventBus();
        ctx.request().response().end("hello world");

    }

    void helloName(RoutingContext ctx) {
        ctx.request().response().end("hello name");
    }
}
