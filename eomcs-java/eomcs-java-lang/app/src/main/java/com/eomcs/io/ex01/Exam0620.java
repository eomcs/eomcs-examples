// 디렉토리에 들어있는 파일(디렉토리) 목록을 꺼낼 때 필터 적용하기 II
package com.eomcs.io.ex01;

import java.io.File;
import java.io.FileFilter;

public class Exam0620 {


  public static void main(String[] args) throws Exception {

    class JavaFilter implements FileFilter {
      @Override
      public boolean accept(File file) {
        // 이 메서드는 listFiles() 메서드에서 호출한다.
        // 지정한 폴더에 들어 있는 파일이나 디렉토리를 찾을 때 마다 호출한다.
        // 리턴 값 File[] 에 찾은 파일 정보를 포함시킬지 여부를 결정한다.
        // true 이면 배열에 포함시키고,
        // false 이면 배열에서 제외한다.
        //
        return file.isFile() && file.getName().endsWith(".java");
      }
    }

    File dir = new File(".");

    // => 확장자가 .java 인 파일의 이름만 추출하기
    // 1) 필터 준비
    JavaFilter javaFilter = new JavaFilter();

    // 2) 필터를 사용하여 디렉토리의 목록을 가져오기
    File[] files = dir.listFiles(javaFilter);

    for (File file : files) {
      System.out.printf("%s %12d %s\n",
          file.isDirectory() ? "d" : "-",
              file.length(),
              file.getName());
    }

  }

}


