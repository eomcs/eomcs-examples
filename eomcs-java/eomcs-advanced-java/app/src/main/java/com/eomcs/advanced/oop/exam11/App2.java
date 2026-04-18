package com.eomcs.advanced.oop.exam11;

import java.io.File;
import java.io.FileFilter;

public class App2 {

  static class MyFileFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
      return file.isFile();
    }
  }

  public static void main(String[] args) {
    File dir = new File(System.getProperty("user.dir"));

    File[] files = dir.listFiles(new MyFileFilter());

    if (files == null) {
      System.out.println("디렉토리가 아니거나 I/O 오류가 발생했습니다.");
      return;
    }

    for (File file : files) {
      System.out.println("[파일] " + file.getName());
    }
  }
}
