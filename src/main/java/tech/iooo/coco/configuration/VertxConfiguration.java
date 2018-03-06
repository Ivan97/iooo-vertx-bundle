package tech.iooo.coco.configuration;

import io.vertx.core.Vertx;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2018/3/6 上午11:06
 *
 * @author Ivan97
 */
@Configuration
public class VertxConfiguration {

  @Bean
  public Vertx vertx() {
    return Vertx.vertx();
  }
}
