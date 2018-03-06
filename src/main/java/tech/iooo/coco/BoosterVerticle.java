package tech.iooo.coco;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.iooo.coco.configuration.ApplicationConfiguration;
import tech.iooo.coco.configuration.VertxBuilder;
import tech.iooo.coco.verticles.CocoVerticle;
import tech.iooo.coco.verticles.IoooVerticle;

/**
 * @author Ivan97
 */
public class BoosterVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(BoosterVerticle.class);

  public BoosterVerticle() {
    this.vertx = VertxBuilder.getInstance().getVertx();
  }

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    logger.info("启动 {} initiation...", this.getClass().getSimpleName());

    //deploy verticles
    Future<Void> ioooVerticleFuture = deploy(IoooVerticle.class, new AtomicLong(0));
    Future<Void> cocoVerticleFuture = deploy(CocoVerticle.class);

    CompositeFuture.all(Arrays.asList(ioooVerticleFuture, cocoVerticleFuture))
        .setHandler(handler -> {
          if (handler.succeeded()) {
            startFuture.complete();
          } else {
            startFuture.fail(handler.cause());
          }
        });
  }

  private Future<Void> deploy(Class<? extends Verticle> clazz, Object... params)
      throws ConfigurationException {
    if (params.length == 0) {
      return deploy(clazz);
    } else {
      Future<Void> future = Future.future();
      Class<?>[] classes = new Class[params.length];
      for (int i = 0; i < params.length; i++) {
        classes[i] = params[i].getClass();
      }
      vertx.deployVerticle(() -> {
        Verticle verticle = null;
        try {
          verticle = clazz.getDeclaredConstructor(classes).newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
          future.fail(e);
        }
        return verticle;
      }, new DeploymentOptions().setInstances(
          ApplicationConfiguration.getApplicationProperties().getVertx().getInstance())
          .setWorker(true), handler -> {
        if (handler.failed()) {
          future.fail(handler.cause());
        } else {
          logger.info("{} deployed", clazz.getSimpleName());
        }
      });
      future.complete();
      return future;
    }
  }

  private Future<Void> deploy(Class<? extends Verticle> clazz)
      throws ConfigurationException {
    Future<Void> future = Future.future();
    vertx.deployVerticle(() -> {
          Verticle verticle = null;
          try {
            verticle = clazz.newInstance();
          } catch (InstantiationException | IllegalAccessException e) {
            future.fail(e);
          }
          return verticle;
        }, new DeploymentOptions()
            .setInstances(ApplicationConfiguration.getApplicationProperties().getVertx().getInstance())
            .setWorker(true),
        handler -> {
          if (handler.failed()) {
            future.fail(handler.cause());
          } else {
            logger.info("{} deployed", clazz.getSimpleName());
          }
        });
    return future;
  }

  @Override
  public void stop(Future<Void> stopFuture) throws Exception {
    logger.info("停止 {} stopped...", this.getClass().getSimpleName());
    super.stop(stopFuture);
  }
}
