package tech.iooo.coco.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.iooo.coco.configuration.Constants;

/**
 * Created on 2018/3/5 下午10:42
 *
 * @author Ivan97
 */
public class IoooVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(IoooVerticle.class);

  private static final String SEPARATOR = "#";

  private final AtomicLong id;

  public IoooVerticle(AtomicLong id) {
    this.id = id;
  }

  @Override
  public void start(Future<Void> startFuture) throws Exception {

    EventBus eventBus = vertx.eventBus();

    vertx.setPeriodic(10000,
        event -> {
          String time = String.valueOf(System.currentTimeMillis());
          logger.info("id:[{}]>>>{}", id.get(), time);
          eventBus.send(Constants.EVENT_ADDRESS,
              id.getAndIncrement() + SEPARATOR + DigestUtils.sha1Hex(time),
              messageAsyncResult -> {
                if (messageAsyncResult.succeeded()) {
                  logger.info("reply message:[{}]", messageAsyncResult.result().body());
                }
              });
        });
    super.start(startFuture);
  }
}
