package com.eomcs.lang.ex99;

//# 키보드 입력 받기 - System.in 과 java.util.Scanner
//
public class Exam0210 {
  public static void main(String[] args) {
    // 1) 키보드로 입력한 데이터를 읽을 때 사용할 도구를 준비한다.
    java.io.InputStream keyboard = System.in;

    // 2) InputStream은 바이트 단위로 읽는 기능이 있다.
    //    바이트 단위로 읽어서 int나 문자열로 바꾸려면 또 코딩해야 하는 불편함이 있다.
    //    이런 불편함을 줄이기 위해 자바에서는 바이트를 개발자가 원하는 값으로
    //    바꿔주는 기능이 들어 있는 도구를 제공한다.
    //    그 도구가 java.util.Scanner 이다.
    // => 키보드를 스캐너에 연결한다.
    java.util.Scanner keyboardScanner = new java.util.Scanner(keyboard);

    // print()는 문자열을 출력한 후 줄 바꿈을 하지 않는다.
    System.out.print("팀명? "); 

    // nextLine()
    // - Scanner 도구를 사용하여 키보드로부터 한 줄의 문자열을 
    //   가져올 때 사용하는 명령이다.
    // - 동작은? 
    //   사용자가 한 줄 입력할 때까지,
    //   즉 입력 데이터에 줄바꿈을 의미하는 0d0a 2바이트가 들어올 때까지 대기한다.
    //   사용자가 엔터키를 누르면, 
    //   입력값으로 0d0a 2바이트 값이 들어오고,
    //   nextLine()은 그 전까지 들어온 데이터를 문자열로 만들어 리턴한다.
    String str = keyboardScanner.nextLine();

    // 사용자가 입력한 문자열을 출력한다.
    System.out.println(str);

    // 스캐너는 사용 후 키보드와의 연결을 끊고 닫는다.
    keyboardScanner.close();
  }
}

// System.out
// - 표준 출력 장치.
// - 즉 콘솔(모니터, 명령창)을 가리킨다.
// System.in
// - 표준 입력 장치.
// - 즉 키보드를 가리킨다.
