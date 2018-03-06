package tech.iooo.coco.configuration;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * Created on 2018/3/5 下午5:38
 *
 * @author Ivan97
 */
public class VertxBuilder {

  private static VertxBuilder ourInstance = new VertxBuilder();

  private Vertx vertx;

  private VertxBuilder() {
    VertxOptions vertxOptions = new VertxOptions();
    this.vertx = Vertx.vertx(vertxOptions);
  }

  public static VertxBuilder getInstance() {
    return ourInstance;
  }

  public Vertx getVertx() {
    return this.vertx;
  }
}
