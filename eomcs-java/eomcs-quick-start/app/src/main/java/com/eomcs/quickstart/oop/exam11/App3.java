package com.eomcs.quickstart.oop.exam11;

import java.io.File;
import java.io.FileFilter;

public class App3 {

  class MyFileFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
      return file.isFile();
    }
  }

  public static void main(String[] args) {
    App3 app = new App3();

    File dir = new File(System.getProperty("user.dir"));

    File[] files = dir.listFiles(app.new MyFileFilter());

    if (files == null) {
      System.out.println("디렉토리가 아니거나 I/O 오류가 발생했습니다.");
      return;
    }

    for (File file : files) {
      System.out.println("[파일] " + file.getName());
    }
  }
}
