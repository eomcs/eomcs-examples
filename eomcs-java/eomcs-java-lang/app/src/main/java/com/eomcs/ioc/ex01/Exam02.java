// IoC 컨테이너 만들기 - 1) 특정 패키지의 파일 목록 알아내기
package com.eomcs.ioc.ex01;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// => 파일 및 디렉토리 정보를 알아내는 코드를 별도의 메서드로 분리한다.
public class Exam02 {

  public static void main(String[] args) {
    // 1) 패키지 경로 준비
    File packageDir = new File("/Users/eomjinyoung/git/java106/java106-java-basic/bin/main/step19/ex1");

    // 2) 해당 디렉토리에서 파일 목록을 가져오기
    List<File> files = findFiles(packageDir);
    for (File f : files) {
      if (f.isDirectory()) {
        System.out.printf("d %s\n", f.getName());
      } else {
        System.out.printf("- %s\n", f.getName());
      }
    }
  }

  static List<File> findFiles(File dir) {
    // 배열을 사용하는 것 보다 ArrayList를 사용하는 것이 편하기 때문에
    // 배열에 들어있는 File 객체들을 ArrayList로 복사한다.
    ArrayList<File> list = new ArrayList<>();
    File[] files = dir.listFiles();
    for (File f : files) {
      list.add(f);
    }
    return list;
  }

}





