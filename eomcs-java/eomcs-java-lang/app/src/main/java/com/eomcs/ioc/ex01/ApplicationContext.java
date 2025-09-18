// 지정된 폴더의 파일 목록만 가져온다.
package com.eomcs.ioc.ex01;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ApplicationContext {
  private ArrayList<File> list = new ArrayList<>();

  public ApplicationContext(File dir) {
    findFiles(dir);
  }

  private void findFiles(File dir) {
    File[] files = dir.listFiles();
    for (File f : files) {
      this.list.add(f);
    }
  }

  public List<File> getFiles() {
    return this.list;
  }
}




