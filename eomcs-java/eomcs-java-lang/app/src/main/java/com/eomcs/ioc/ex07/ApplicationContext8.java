// 객체를 저장할 때 클래스 이름으로 저장하고 클래스 이름으로 꺼낼 수 있다.
package com.eomcs.ioc.ex07;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class ApplicationContext8 {
  // 클래스 이름으로 객체를 저장할 수 있도록 Map 을 사용한다.
  private HashMap<String,Object> objPool = new HashMap<>();

  public ApplicationContext8(String packageName) throws Exception {
    String path = packageName.replace(".", "/");
    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    URL url = classLoader.getResource(path);
    File dir = new File(url.getPath());
    if (!dir.isDirectory())
      return;
    findClasses(dir, packageName);
  }

  private void findClasses(File dir, String packageName) throws Exception {
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
        findClasses(f, packageName + "." + f.getName());
        continue;
      } 

      String classname = f.getName();    
      Class clazz = Class.forName(packageName + "." + 
          classname.substring(0, classname.length() - 6));
      Object obj = createObject(clazz);
      if (obj != null) {
        this.objPool.put(clazz.getName(), obj);
      }
    }
  }

  private Object createObject(Class clazz) {
    try {
      // 파라미터가 없는 기본 생성자를 찾는다.
      clazz.getConstructor();
      return clazz.newInstance(); // 기본 생성자를 호출하여 객체를 생성한다.
    } catch (Exception e) {
      return null;
    }
  }

  public Object getBean(String name) {
    return objPool.get(name);
  }
}




