package com.eomcs.quickstart.oop.exam11;

import java.io.File;
import java.io.FileFilter;

public class MyFileFilter implements FileFilter {

  @Override
  public boolean accept(File file) {
    return file.isFile();
  }
}
