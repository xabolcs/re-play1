package criminals;

import io.avaje.inject.Component;

import play.Play;
import play.modules.avajeinject.AvajeInjectBeanSource;
import play.server.Server;

import javax.annotation.Nullable;
import java.util.Properties;

import static play.PropertiesConfLoader.read;

public class Application {
  public void start(String playId) {
    start(playId, null);
  }

  public int start(String playId, @Nullable String criminalRecordsServiceUrl) {
    Properties configuration = read(playId);
    if (criminalRecordsServiceUrl != null) {
      configuration.setProperty("criminal-records.service.url", criminalRecordsServiceUrl);
    }
    Configuration configModule = new Configuration(configuration);
    AvajeInjectBeanSource avaje = new AvajeInjectBeanSource();
    Play play = new Play(avaje);
    play.init(playId);
    play.start();
    return new Server(play).start();
  }

  public static void main(String[] args) {
    new Application().start(System.getProperty("play.id", ""));
  }
}
