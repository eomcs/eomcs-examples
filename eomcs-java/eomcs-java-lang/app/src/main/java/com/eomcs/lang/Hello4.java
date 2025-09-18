//# 패키지 무소속 클래스
//- 특정 패키지에 소속이 되지 않은 클래스이다.
//
//## 문법
//- package 키워드를 붙이지 않는다.
//
//## 소스 파일의 위치
//- 소스 파일 찾고 관리하기 쉽도록 루트 소스 폴더에 둔다.
//   /src/main/java/소스파일
//- 예) /src/main/java/Hello4.java
//
//* 패키지 폴더에 무소속 클래스를 둔다면?
//- 패키지 소속 클래스이든 무소속 클래스이든 소스 파일의 위치는 상관없다.
//  예) /src/main/com/eomcs/lang/Hello4.java
//- 보통 소스 파일의 관리가 쉽도록 패키지에 해당하는 디렉토리에 둔다.
//
//## 클래스 파일(.class)의 위치
//- 컴파일러는 패키지 무소속 클래스를 컴파일 할 때 소스 파일의 위치에 상관없이 클래스 파일을 두는 폴더(/bin/main)의 루트 디렉토리에 생성한다.
//  /bin/main/클래스파일
//- 예) /bin/main/Hello4.class
//

class Hello4 {
  public static void main(String[] args) {
    System.out.println("Hello, World!");
  }
}

//## 실습
//1) 컴파일하기
//- 프로젝트 폴더에서 실행 할 때:
//  예) $ javac -d bin/main src/main/java/com/eomcs/lang/Hello4.java
//- 클래스 파일은 클래스 파일을 두는 폴더의 루트에 생성된다.
//  예) /bin/main/Hello4.class
//
//2) 실행하기
//- 패키지에 소속된 클래스가 아니기 때문에 패키지 지정없이 클래스 이름만 지정해야 한다.
//  java [-classpath | -cp] [루트 패키지가 있는 디렉토리 경로] [패키지 이름을 포함한 클래스 이름]
//  예) $ java -cp bin/main Hello4
//
//* 주의!
//- 소스 파일이 있는 위치를 패키지로 간주하고 다음과 같이 패키지를 지정한다면 컴파일 오류가 발생한다.
//  예) $ java -cp bin/main com.eomcs.lang.Hello4
//
