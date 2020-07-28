package vertx;

import io.vertx.core.AbstractVerticle;

public class HelloVerticle extends AbstractVerticle {
    @Override
    public void start() {
        vertx.eventBus().consumer("hello.vertx.addr", msg -> {
            msg.reply("hello vertx world");
        });
        vertx.eventBus().consumer("hello.named.addr", msg -> {
            String name = (String) msg.body();
            msg.reply(String.format("hello %s!", name));

        });
    }
}
