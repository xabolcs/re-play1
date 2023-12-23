package org.xhtmlrenderer.swing;

import play.Play;

import java.io.File;

public class FileSearcher {
  public File searchFor(String filePath) {
    return Play.file(filePath);
  }
}
