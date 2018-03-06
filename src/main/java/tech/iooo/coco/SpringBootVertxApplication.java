package tech.iooo.coco;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.iooo.coco.domain.ApplicationProperties;

/**
 * Created on 2018/3/6 上午11:04
 *
 * @author Ivan97
 */
@SpringBootApplication
public class SpringBootVertxApplication implements CommandLineRunner {

  @Autowired
  private Vertx vertx;

  @Autowired
  private ApplicationProperties applicationProperties;

  public static void main(String[] args) {
    SpringApplication.run(SpringBootVertxApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    vertx.deployVerticle(() -> new BoosterVerticle(vertx, applicationProperties),
        new DeploymentOptions());
  }
}
