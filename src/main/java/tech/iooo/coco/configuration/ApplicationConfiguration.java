package tech.iooo.coco.configuration;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import tech.iooo.coco.domain.ApplicationProperties;

/**
 * Created on 2018/3/5 下午11:03
 *
 * @author Ivan97
 */
public class ApplicationConfiguration {

  public static ApplicationProperties getApplicationProperties() throws ConfigurationException {
    FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
        new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
            .configure(new Parameters().properties()
                .setFileName("application.properties")
                .setThrowExceptionOnMissing(true)
                .setListDelimiterHandler(new DefaultListDelimiterHandler(','))
                .setIncludesAllowed(false));
    PropertiesConfiguration config = builder.getConfiguration();

    ApplicationProperties applicationProperties = ApplicationProperties.getInstance();
    applicationProperties.getVertx().setInstance(config.getInt("vertx.instance"));

    return applicationProperties;
  }
}
