package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class EventBusVerticle extends AbstractVerticle {
    Router router = Router.router(vertx);

    @Override

    public void start() {
        DeploymentOptions opts = new DeploymentOptions().setWorker(true).setInstances(8);
        vertx.deployVerticle("vertx.HelloVerticle",opts);
        vertx.deployVerticle(new HelloVerticle());
        router.get("/exe/hello").handler(this::helloVertx);
        router.get("/exe/name").handler(this::helloName);
        vertx.createHttpServer().requestHandler(router).listen(8888);

    }

    void helloVertx(RoutingContext ctx) {
        vertx.eventBus().request("hello.vertx.addr","",reply->{
            ctx.request().response().end((String)reply.result().body());
        });
        ctx.request().response().end("hello world");

    }

    void helloName(RoutingContext ctx) {
        String name = ctx.pathParam("name");
        vertx.eventBus().request("hello.named.addr",name,reply->{
            ctx.request().response().end((String)reply.result().body());
        });

    }
}
