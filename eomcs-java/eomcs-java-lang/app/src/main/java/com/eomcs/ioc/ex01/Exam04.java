// IoC 컨테이너 만들기 - 1) 특정 패키지의 파일 목록 알아내기
package com.eomcs.ioc.ex01;

import java.io.File;
import java.util.List;

// => 지정된 디렉토리 뿐만아니라 하위 디렉토리도 검색하여 파일 목록을 알아낸다.
public class Exam04 {

  public static void main(String[] args) throws Exception {
    // 1) 패키지 경로 준비
    File packageDir = new File("/Users/eomjinyoung/git/java106/java106-java-basic/bin/main/step19");

    // 2) 해당 디렉토리에서 파일 목록을 가져오기
    ApplicationContext2 appContext = new ApplicationContext2(packageDir);
    List<File> files = appContext.getFiles();
    for (File f : files) {
      System.out.println(f.getCanonicalPath());
    }
  }



}





