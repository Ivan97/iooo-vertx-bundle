package tech.iooo.coco.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created on 2018/3/5 下午11:15
 *
 * @author Ivan97
 */
@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

  private ApplicationProperties.IoooVertx vertx = new ApplicationProperties.IoooVertx();

  private ApplicationProperties() {
  }

  @Data
  public class IoooVertx {

    private int instance;
  }
}
