package com.eomcs.lang.ex02;

//# 주석 - Javadoc 주석
//
//- "java document comment(doc comment)"
//- javadoc에서 HTML 문서를 만들 때 사용하는 주석이다.
//- 주로 API 문서를 자동 생성할 때 사용한다.
//- 클래스나 메서드, 변수 선언에 붙일 수 있다.
//

/**
 * 클래스에 대한 설명
 * @author eomjinyoung
 *
 */
public class Exam0200 {
  /**
   * 변수에 대한 설명
   * 변수 선언 앞에 설명을 붙여 놓으면 나중에 HTML 문서를 만들 때 추출할 수 있다.
   */
  public static String message = "Hello, world!";

  /**
   * 메서드에 대한 설명
   * 메서드에 대한 설명을 여기에 붙여 놓으면 나중에 HTML 문서를 만들 때 추출할 수 있다.
   * @param args 애플리테이션 아규먼트 값을 보관한 배열
   */
  public static void main(String[] args) {
    System.out.println(message);
  }
}

//## 실습
//1) Java API Document 만들기
//- javadoc.exe 도구를 사용하여 자바 소스 파일에서 Javadoc 주석을 추출하여 HTML 파일을 생성해 보자.
//     javadoc
//       -encoding [소스 파일의 문자집합]
//       -charset [생성될 HTML 파일의 문자집합]
//       -d [생성된 파일을 놓아둘 디렉토리]
//       -sourcepath [자바 소스 경로] [자바 패키지]
//예) $ javadoc -encoding UTF-8 -charset UTF-8 -d javadoc -sourcepath src/main/java com.eomcs.lang.ex02
//
//2) Javadoc으로 생성한 HTML 문서 확인하기
//- /javadoc 디렉토리를 보면 자바 소스 파일에서 추출한 Javadoc 으로 만든 HTML 문서를 확인할 수 있다.
//