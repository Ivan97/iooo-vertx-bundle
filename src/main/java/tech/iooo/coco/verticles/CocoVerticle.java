package tech.iooo.coco.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.iooo.coco.configuration.Constants;

/**
 * Created on 2018/3/5 下午10:42
 *
 * @author Ivan97
 */
public class CocoVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(CocoVerticle.class);

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    EventBus eventBus = vertx.eventBus();
    eventBus.consumer(Constants.EVENT_ADDRESS, handler -> {
      logger.info("received:[{}]", handler.body());
      handler.reply("ok");
    });
  }
}
