package play.modules.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.inject.BeanSource;

import javax.annotation.Nonnull;
import java.util.List;

public class GuiceBeanSource implements BeanSource {
  private static final Logger logger = LoggerFactory.getLogger(GuiceBeanSource.class);

  @Nonnull
  private final Injector injector;

  public GuiceBeanSource(@Nonnull List<Module> modules) {
    logger.info("Initializing guice modules: " + modules);
    this.injector = Guice.createInjector(modules);
  }

  @Override
  public <T> T getBeanOfType(Class<T> clazz) {
    return injector.getInstance(clazz);
  }
}