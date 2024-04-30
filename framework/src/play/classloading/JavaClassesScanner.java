package play.classloading;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.slf4j.Logger;
import play.templates.FastTags;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class JavaClassesScanner {
  private static final Logger logger = LoggerFactory.getLogger(JavaClassesScanner.class);

  public List<Class<?>> allClassesInProject() {
    List<Class<?>> result = new ArrayList<>();

    Instant start = Instant.now();
    List<File> classpath = new ClassGraph().getClasspathFiles();
    logger.info("Getting classpath files took {} ms.", Duration.between(start, Instant.now()).toMillis());

    for (File file : classpath) {
      try {
        if (file.isDirectory()) result.addAll(classesInDirectory(null, file));
      }
      catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }

    try {
      result.addAll(subClassesOf(FastTags.class, null));
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  private List<Class<?>> subClassesOf(Class<?> thisClass, UnaryOperator<ClassGraph> classGraphconfigurator) throws ClassNotFoundException{
    List<Class<?>> result = new ArrayList<>();
    Instant start = Instant.now();
    try (ScanResult scanResult = Optional.ofNullable(classGraphconfigurator)
            .orElseGet(UnaryOperator::identity)
            .apply(new ClassGraph())
            .enableClassInfo()
            .scan()) {
      List <String> classNames = scanResult.getSubclasses(thisClass).stream().map(ClassInfo::getName).collect(Collectors.toList());
      for (String className : classNames) {
        result.add(Class.forName(className, false, Thread.currentThread().getContextClassLoader()));
      }
    }
    logger.info("Classpath scan for subclasses of {} took {} ms.", thisClass.getName(), Duration.between(start, Instant.now()).toMillis());

    return result;
  }

  private List<Class<?>> classesInDirectory(String packageName, File directory) throws ClassNotFoundException {
    if (directory.getAbsolutePath().contains("/test"))
      return emptyList();
    if (directory.getAbsolutePath().contains("pdf/build/thirdParty"))
      return emptyList(); // it causes initialisation of org.xhtmlrenderer.swing.AWTFSImage which is slow

    List<Class<?>> result = new ArrayList<>();
    for (File file : directory.listFiles()) {
      if (file.isDirectory()) {
        String subPackage = packageName == null ? file.getName() : packageName + '.' + file.getName();
        result.addAll(classesInDirectory(subPackage, file));
      }
      else if (file.getName().endsWith(".class")) {
        String className = packageName == null ? classNameOf(file) : packageName + '.' + classNameOf(file);
        result.add(Class.forName(className, false, Thread.currentThread().getContextClassLoader()));
      }
    }
    return result;
  }

  private String classNameOf(File file) {
    return file.getName().replace(".class", "");
  }
}
