package play.classloading;

import static java.util.Collections.emptyList;

import io.github.classgraph.ClassGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class JavaClassesScanner {
  private static final Logger logger = LoggerFactory.getLogger(JavaClassesScanner.class);

  public List<Class<?>> allClassesInProject() {
    List<Class<?>> result = new ArrayList<>();

    Instant start = Instant.now();
    List<File> classpath = new ClassGraph().getClasspathFiles();
    logger.info("Getting classpath files took {} ms.", Duration.between(start, Instant.now()).toMillis());
    logger.trace("classpath size is {} for {}", classpath.size(), classpath);

    for (File file : classpath) {
      try {
        if (file.isDirectory()) result.addAll(classesInDirectory(null, file));
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }

    logger.trace("all classes in project are: {}", result);
    return result;
  }

  private List<Class<?>> classesInDirectory(String packageName, File directory)
      throws ClassNotFoundException {
    logger.trace("Examining directory {} for classes", directory);
    if (directory.getAbsolutePath().contains("/test")) return emptyList();
    if (directory.getAbsolutePath().contains("pdf/build/thirdParty"))
      return emptyList(); // it causes initialisation of org.xhtmlrenderer.swing.AWTFSImage which is slow

    List<Class<?>> result = new ArrayList<>();
    var files = directory.listFiles();
    if (files == null) return result;
    for (File file : files) {
      logger.trace("Examining file {}", file);
      if (file.isDirectory()) {
        String subPackage =
            packageName == null ? file.getName() : packageName + '.' + file.getName();
        result.addAll(classesInDirectory(subPackage, file));
      } else if (file.getName().endsWith(".class")) {
        String className =
            packageName == null ? classNameOf(file) : packageName + '.' + classNameOf(file);
        result.add(Class.forName(className, false, Thread.currentThread().getContextClassLoader()));
        logger.trace("Found class {} in file {}", className, file);
      }
    }
    return result;
  }

  private String classNameOf(File file) {
    return file.getName().replace(".class", "");
  }
}
