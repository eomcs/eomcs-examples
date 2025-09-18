// IoC 컨테이너 만들기 - 2) 특정 패키지의 파일 목록 알아내기
package com.eomcs.ioc.ex02;

import java.io.File;
import java.util.List;

// => ClassLoader로 자바 classpath를 검색하여 해당 패키지를 찾는다. 
public class Exam05 {

  public static void main(String[] args) throws Exception {
    // 1) 패키지 경로를 따로 준비하지 않는다.
    // 소스 코드에 직접 전체 디렉토리 경로를 지정하게 되면,
    // 만약 해당 폴더가 옮겨졌을 때 디렉토리를 찾지 못하는 문제가 있다.
    // 찾으려면 소스 코드를 변경해야 한다.
    // 소스 코드를 변경하지 않고 계속 step18/ex6 가 존재하는 디렉토리를 찾는 방법은?
    // => step18/ex6를 자바 CLASSPATH 에 둔다.
    //         즉 jvm을 실행할 때 -classpath로 클래스 파일이 있는 경로를 지정하는데
    //         그 경로 안에 두는 것을 말한다.
    // => classpath에 두면 ClassLoader를 이용하여 해당 폴더가 
    //         실제 어느 디렉토리 밑에 있는지 쉽게 찾을 수 있다.

    // ClassLoader의 도움을 받지 않는다면,
    // 우리는 다음과 같이 코드에 직접 그 패키지가 있는 경로를 지정해야 한다.
    //File packageDir = new File("/Users/eomjinyoung/git/java106/java106-java-basic/bin/main/step19");

    // 2) 해당 패키지의 파일 목록을 가져오기
    ApplicationContext3 appContext = new ApplicationContext3("step19.ex1");
    List<File> files = appContext.getFiles();
    for (File f : files) {
      System.out.println(f.getCanonicalPath());
    }
  }



}





