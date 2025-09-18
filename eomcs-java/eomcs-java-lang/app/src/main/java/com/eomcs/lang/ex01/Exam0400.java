package com.eomcs.lang.ex01;

//# main() 메서드
//- 다음과 같이 JVM을 통해 클래스를 실행하면, JVM은 그 클래스에서 main() 메서드를 찾아 실행한다.
//  예) $ java -cp bin/main com.eomcs.basic.ex01.Exam400
//- main() 메서드가 없으면 실행 오류가 발생한다.
//- 프로그램을 실행하는 시작점이라는 의미에서  "entry point" 라 부른다.
//
//## JVM이 클래스를 실행하는 과정
//1) 실행할 클래스 파일을 CLASSPATH에 등록된 디렉토리 경로에서 찾는다.
//2) 클래스 파일이 유효한 바이트코드(bytecode) 인지 검사한다.
//3) 메모리에 바이트코드를 적재(load)한다.
//4) public static void main(String[] args) {} 블록을 찾는다.
//5) main() 블록에 들어 있는 코드를 실행한다.
//

public class Exam0400 {
  //  public static void main(String[] args) {
  //  }
}

//## 실습
//1) 컴파일하기
//- $ javac -d bin/main -encoding UTF-8 src/main/java/com/eomcs/lang/ex01/Exam0400.java
//
//2) 실행하기
//- $ java -cp bin/main com.eomcs.lang.ex01.Exam0400
//
//* main() 메서드를 주석으로 막은 다음에 컴파일하고 실행해 보자.
//- 실행 오류 메시지를 확인할 수 있다.