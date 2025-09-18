// character stream - 문자 단위로 읽기
package com.eomcs.io.ex03;

import java.io.FileReader;

public class Exam0120 {

  public static void main(String[] args) throws Exception {

    // 1) 파일의 데이터를 읽는 일을 하는 객체를 준비한다.
    FileReader in = new FileReader("sample/utf8.txt"); // 41 42 ea b0 80 ea b0 81

    // 2) JVM 환경 변수 'file.encoding'에 설정된 문자코드표에 따라
    //    바이트를 읽어서 UCS2로 바꾼 후에 리턴한다.
    //
    // - JVM을 실행할 때 다음 옵션을 지정하지 않으면
    //     -Dfile.encoding=문자표
    // - JVM은 OS의 기본 문자표라고 가정하고 파일을 읽는다.
    //   만약 이클립스에서 실행한다면,
    //   UTF-8이라고 가정하고 읽는다.
    //   만약 명령창(콘솔창)에서 실행한다면,
    //   OS에 따라 다르게 읽는다.
    //
    // 현재 JVM 환경 변수 'file.encoding' 값 알아내기
    System.out.printf("file.encoding=%s\n", System.getProperty("file.encoding"));

    // file.encoding이 UTF-8로 되어 있다면,
    // => 영어는 1바이트를 읽어서 2바이트 UCS2로 변환한다.
    int ch1 = in.read(); // 41 => 0000 0041('A')
    int ch2 = in.read(); // 42 => 0000 0042('B')

    // => 한글은 3바이트를 읽어서 2바이트 UCS2로 변환한다.
    int ch3 = in.read(); // ea b0 80 => 0000 ac00('가')
    int ch4 = in.read(); // ea b0 81 => 0000 ac01('각')

    // 3) 읽기 도구를 닫는다.
    in.close();

    System.out.printf("%08x, %08x, %08x, %08x\n", ch1, ch2, ch3, ch4);
  }
}






