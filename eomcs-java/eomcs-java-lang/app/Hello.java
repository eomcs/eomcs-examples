// # 코드 작성과 컴파일, 실행
// - 자바로 코드를 작성하고 컴파일하여 실행하는 방법.
//
// ## 용어
// 1) 소스 파일(source file)
// - 컴퓨터가 해야 할 일을 명령어로 기술한 파일이다.
// - 예) Hello.c, Hello.cpp, Hello.java, Hello.py 등
//
// 2) 프로그래밍 언어(programming language)
// - 명령어를 작성할 때 사용하는 문법이다.
// - 예) Java, Kotlin, JavaScript, Python, C, C++ 등
//
// 3) 자바 소스 파일
// - 자바 프로그래밍 언어로 명령어를 기술한 파일이다.
// - '.java' 파일 확장자를 가진다.
// - 예) Hello.java
//
// 4) 컴파일(compile)
// - 인간이 작성한 명령어를 컴퓨터가 인식할 수 있는 명령어로 번역하는 것.
// - 즉 자바로 작성한 명령어를 JVM이 이해할 수 있는 기계어로 바꾼다.
// - 자바 소스 파일(.java)을 컴파일하면 기계어 파일(.class)을 생성한다.
//
// 5) 컴파일러
// - 컴파일을 수행하는 프로그램.
// - 예) javac.exe 
//
// 6) 바이트코드(bytecode)
// - 자바 컴파일러가 번역한 기계어이다.
// - 다른 말로 portable code 또는 줄여서 p-code 라고도 부른다.
// - JVM이 실행하는 명령어이다.
// - 자바 클래스 파일에 저장된다.
// - 대부분의 기계어 명령(instruction sets)이 1바이트의 동작코드(opcode; operation code)와 0개 이상의 파라미터로 구성되어서 "바이트코드"라는 이름이 붙여졌다.
//
// 7) 자바 클래스 파일(class file)
// - 바이트 코드가 들어 있는 파일이다.
// - '.class'라는 파일 확장자를 가진다.
// - 자바 컴파일러가 생성한다.
// - 예) Hello.class
//
// 8) 자바가상머신(JVM; Java Virtual Machine)
// - 바이트코드로 작성된 .class 파일을 실행하는 프로그램.
// - 예) java.exe
// - VM과 유사한 일을 하는 프로그램을 부르는 용어: player, interpreter, engine, viewer 등
// - 예) MP3 플레이어, 동영상 플레이어, 플래시 플레이어, 코볼 인터프리터, JSP 엔진, HWP 뷰어, PPT 뷰어 등
//
class Hello {
  public static void main(String[] args) {
    System.out.println("Hello, world!");
  }
}

// ## 실습
// 1) 자바 소스 파일 작성하기
// - /Hello.java 파일을 생성한다.
//  
// 2) 컴파일하기
// - .java 파일을 컴파일하여 .class 파일을 생성한다.
// - 사용법:
//   javac 소스파일명
//   예) $ javac Hello.java
//
// 3) 실행하기  
// - .class 파일을 실행한다.
// - 사용법
//   java 클래스파일명(.class 확장자 제외)
//   예) $ java Hello
// 
// 4) PATH 환경변수
// - 자바 컴파일러와 JVM의 경로를 운영체제의 PATH 환경변수에 등록해 놓으면 
//   컴파일하거나 실행할 때 전체 경로를 지정할 필요가 없다.
// - 사용하는 운영체제에 맞춰 설정한다.
//