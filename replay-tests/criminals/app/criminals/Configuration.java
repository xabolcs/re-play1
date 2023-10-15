package criminals;

import criminals.annotation.CriminalRecordsServiceUrl;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;

import javax.inject.Inject;
import java.util.Properties;

@Factory
public class Configuration {
  private final Properties configuration;

  //@Inject
  public Configuration(Properties configuration) {
    this.configuration = configuration;
  }

  @CriminalRecordsServiceUrl
  @Bean
  String getServiceUrl() {
    return configuration.getProperty("criminal-records.service.url");
  }
}
