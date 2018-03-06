package tech.iooo.coco.domain;

import lombok.Data;

/**
 * Created on 2018/3/5 下午11:15
 *
 * @author Ivan97
 */
@Data
public class ApplicationProperties {

  private static ApplicationProperties ourInstance = new ApplicationProperties();
  private ApplicationProperties.IoooVertx vertx = new ApplicationProperties.IoooVertx();

  private ApplicationProperties() {
  }

  public static ApplicationProperties getInstance() {
    return ourInstance;
  }

  @Data
  public class IoooVertx {

    private int instance;
  }
}
