package play.modules.avajeinject;

import io.avaje.inject.BeanEntry;
import io.avaje.inject.BeanScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.inject.BeanSource;
import play.inject.DefaultBeanSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.System.nanoTime;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class AvajeInjectBeanSource extends DefaultBeanSource implements io.avaje.inject.BeanScope {
  private static final Logger logger = LoggerFactory.getLogger(AvajeInjectBeanSource.class);

  private final io.avaje.inject.BeanScope scope;
  private final DefaultBeanSource defaultBeanSource;

  public AvajeInjectBeanSource() {
    long start = nanoTime();
    this.scope = BeanScope.builder().build();
    this.defaultBeanSource = new DefaultBeanSource();
    logger.info("Initialized avaje inject in {} ms", NANOSECONDS.toMillis(nanoTime() - start));
  }

  @Override
  public <T> T getBeanOfType(Class<T> clazz) {
    if (contains(clazz)) {
      return get(clazz);
    }
    return defaultBeanSource.getBeanOfType(clazz);
  }

  @Override
  public <T> T get(Class<T> type) {
    return scope.get(type);
  }

  @Override
  public <T> T get(Class<T> type, String name) {
    return scope.get(type, name);
  }

  @Override
  public <T> T get(Type type, String name) {
    return scope.get(type, name);
  }

  @Override
  public <T> Optional<T> getOptional(Class<T> type) {
    return scope.getOptional(type);
  }

  @Override
  public <T> Optional<T> getOptional(Type type, String name) {
    return scope.getOptional(type, name);
  }

  @Override
  public List<Object> listByAnnotation(Class<? extends Annotation> annotation) {
    return scope.listByAnnotation(annotation);
  }

  @Override
  public <T> List<T> list(Class<T> type) {
    return scope.list(type);
  }

  @Override
  public <T> List<T> list(Type type) {
    return scope.list(type);
  }

  @Override
  public <T> List<T> listByPriority(Class<T> type) {
    return scope.listByPriority(type);
  }

  @Override
  public <T> List<T> listByPriority(Class<T> type, Class<? extends Annotation> priority) {
    return scope.listByPriority(type, priority);
  }

  @Override
  public <T> Map<String, T> map(Type type) {
    return scope.map(type);
  }

  @Override
  public List<BeanEntry> all() {
    return scope.all();
  }

  @Override
  public boolean contains(Type type) {
    return scope.contains(type);
  }

  @Override
  public boolean contains(String type) {
    return scope.contains(type);
  }

  @Override
  public void close() {
    scope.close();
  }
}
