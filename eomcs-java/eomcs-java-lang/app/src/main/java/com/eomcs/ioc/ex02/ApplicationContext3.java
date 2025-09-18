// 디렉토리 경로 대신 패키지 이름을 입력 받아 
// 해당 패키지의 파일 목록을 알아내기
package com.eomcs.ioc.ex02;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApplicationContext3 {
  private ArrayList<File> list = new ArrayList<>();

  public ApplicationContext3(String packageName) {
    // 1) 패키지 이름에 포함된 .을 파일 경로의 /로 변경한다.
    String path = packageName.replace(".", "/");

    // 2) 해당 경로의 디렉토리를 classpath에서 찾아 실제 경로를 알아낸다.
    // 프로그램과 관련된 파일을 일반 경로에 두는 것 보다
    // JVM이 알고 있는 경로(CLASSPATH)에 두면 해당 파일을 보다 쉽게 찾을 수 있다.
    // 왜냐하면,
    // JVM이 모르는 특정 경로에 파일을 두면, 
    // 그 파일을 찾기 위해서 소스 코드에 그 특정 경로를 지정해야 한다.
    // 만약 프로그램을 다른 OS에 배포하거나 경로가 달라진다면
    // 또 소스 코드를 변경해야 하는 번거로움이 발생한다.
    // 그래서 실무에서는 이런 문제를 해결하기 위해 
    // 프로그램과 관련된 파일은 주로 CLASSPATH에 둔다.

    // 다음은 CLASSPATH 에 파일을 두었을 때
    // 그 파일의 실제 경로를 알아내는 방법이다.
    // 
    // CLASSPATH 를 뒤져 파일을 찾아줄 객체를 얻는다.
    // => ClassLoader
    ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    // 클래스 로더를 이용하여 특정 패키지나 클래스의 절대 경로를 알아낸다.
    //   => 클래스 로더는 디렉토리나 파일을 찾을 때 JVM 에 지정된 CLASSPATH에서 찾는다.
    //      따라서 프로그램을 작성할 때 해당 패키지나 파일이 있는 경로를 
    //      코드에 지정할 필요가 없어 편리하다.
    //   => 디렉토리 경로를 지정할 때 / 를 사용하라!
    //   => getResource() 가 리턴하는 것은 찾은 자원의 경로 정보이다.
    //   => 같은 경로의 자원이 여러 디렉토리에 중복해서 있을 때는
    //      getResources()를 호출하여 여러 개의 경로 정보를 받아라!
    //   => 자원의 경로 정보를 담고 있는 URL 객체를 리턴한다.
    //  
    URL url = classLoader.getResource(path);

    // 3) URL에서 실제 경로를 뽑아 File 객체를 생성한다.
    File dir = new File(url.getPath());

    // 4) 해당 파일의 경로가 디렉토리를 가리키고 있다면,
    //    그 디렉토리의 파일 목록을 알아낸다.
    if (!dir.isDirectory())
      return;

    findFiles(dir);
  }

  private void findFiles(File dir) {
    File[] files = dir.listFiles();
    for (File f : files) {
      if (f.isDirectory()) {
        findFiles(f);
      } else {
        this.list.add(f);
      }
    }
  }

  public List<File> getFiles() {
    return this.list;
  }
}




