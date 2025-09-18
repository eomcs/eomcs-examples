package com.eomcs.lang;

//# 패키지와 클래스
//- 여러 개발자가 같은 이름의 클래스를 만들 수 있다.
//- 같은 이름의 클래스를 함께 사용할 경우가 있는데, 이런 경우 이름이 같아서 구분하지 못하는 문제가 발생한다.
//- 이를 회피하기 위해 만든 문법이 '패키지'이다. 
//
//## 패키지란?
//- 클래스의 이름 충돌을 방지하는 문법이다.
//- 클래스가 소속된 폴더를 가리키는 용어이다.
//- 패키지는 폴더로 표현한다.
//- 클래스를 관리하기 쉽게 서로 밀접하게 관련된 클래스를 그룹으로 분류하는 문법이다.
//
//## 문법
//- 소스 파일의 첫 번째 문장으로 선언해야 한다.
//   package 패키지명.패키지명.패키지명;
//- 예) package com.eomcs.lang;
//
//## 소스 파일의 위치
//- 소스 파일 찾고 관리하기 쉽도록 패키지와 일치하는 폴더에 둔다.
//   /src/main/java/패키지명/패키지명/패키지명/소스파일    
//- 예) package com.eomcs.lang; 
//     => /src/main/java/com/eomcs/lang/Hello3.java
//- 예) package com.eomcs.ok; 
//     => /src/main/java/com/eomcs/ok/Hello3.java
//- 예) package com.eomcs; 
//     => /src/main/java/com/eomcs/Hello3.java
//- 예) package com; 
//     => /src/main/java/com/Hello3.java
//- 예) 무소속
//     => /src/main/java/Hello3.java
//
//## 클래스 파일(.class)의 위치
//- 컴파일러가 생성한 .class 파일은 무조건 패키지 이름과 같은 폴더에 있어야 한다.
//  => /bin/패키지명/패키지명/패키지명/클래스파일
//- 예) $ javac -d bin src/main/java/com/eomcs/lang/Hello3.java
//      => /bin/com/eomcs/lang/Hello3.class
//- 예) $ javac -d bin/main src/main/java/com/eomcs/lang/Hello3.java
//      => /bin/main/com/eomcs/lang/Hello3.class
//- 예) $ javac -d bin/main src/main/java/com/eomcs/Hello3.java
//      => /bin/main/com/eomcs/Hello3.class
//- 예) $ javac -d bin/main src/main/java/com/Hello3.java
//      => /bin/main/com/Hello3.class
//- 예) $ javac -d bin/main src/main/java/Hello3.java
//      => /bin/main/Hello3.class
//
//## 결론!
//- 소스 파일도 자신이 소속된 패키지 폴더에 놓아라!
//- 그래야 소스 파일을 관리하기 쉽다.
//- 패키지는 디렉토리로 표현한다.
//

class Hello3 {
  public static void main(String[] args) {
    System.out.println("Hello, World!");
  }
}

//## 실습
//1) 소스 디렉토리 생성
//- /src/main/java/com/eomcs/lang 폴더를 생성한다.
//
//2) 소스 파일 생성
//- Hello3.java 파일을 작성한다.
//
//3) 컴파일하기
//- 프로젝트 폴더에서 실행 할 때:
//  예) $ javac -d bin/main src/main/java/com/eomcs/lang/Hello3.java
//- 클래스 파일은 패키지 폴더에 생성된다.
//  예) /bin/main/com/eomcs/lang/Hello3.class
//
//4) 실행하기
//- 프로젝트 폴더에서 실행 할 때:
//  java [-classpath | -cp] [루트 패키지가 있는 디렉토리 경로] [패키지 이름을 포함한 클래스 이름]
//  예) $ java -cp bin/main com.eomcs.lang.Hello3
//- 다음과 같이 . 대신에 / 를 사용해도 된다.
//  예) $ java -cp bin/main com/eomcs/lang/Hello3
//
//* 주의!
//- 패키지에 소속된 클래스를 실행할 때는 반드시 패키지명과 클래스명을 함께 지정해야 한다.
//- 다음과 같이 -cp에 패키지 경로를 지정한 후 클래스 이름으로 실행할 수 없다.
//  예) $ java -cp bin/main/com/eomcs/lang Hello3  <== 실행 오류!
//


