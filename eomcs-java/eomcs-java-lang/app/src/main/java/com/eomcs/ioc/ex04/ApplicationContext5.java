// 중첩 클래스를 제외한 .class 파일만 추출한다.
package com.eomcs.ioc.ex04;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApplicationContext5 {
  private ArrayList<String> list = new ArrayList<>();

  public ApplicationContext5(String packageName) {
    String path = packageName.replace(".", "/");
    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    URL url = classLoader.getResource(path);
    File dir = new File(url.getPath());
    if (!dir.isDirectory())
      return;
    findFiles(dir, packageName);
  }

  // 파일 경로에서 패키지명과 클래스명을 추출할 수 없다.
  // 그래서 파일을 찾을 때 패키지 이름을 따로 관리해야 한다.
  private void findFiles(File dir, String packageName) {
    File[] files = dir.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        if (pathname.isDirectory() || // 디렉토리 이거나
            (pathname.getName().endsWith(".class") && // .class 파일인 경우 
                !pathname.getName().contains("$"))) { // 단 중첩클래스는 제외
          return true;
        }
        return false;
      }
    });
    for (File f : files) {
      if (f.isDirectory()) {
        findFiles(f, packageName + "." + f.getName());
      } else {
        String classname = f.getName();                
        this.list.add(packageName + "." + 
            classname.substring(0, classname.length() - 6));
      }
    }
  }

  public List<String> getClassname() {
    return this.list;
  }
}




