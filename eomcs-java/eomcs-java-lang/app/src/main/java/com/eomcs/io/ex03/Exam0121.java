// character stream - 문자 단위로 읽기
package com.eomcs.io.ex03;

import java.io.FileReader;
import java.nio.charset.Charset;

public class Exam0121 {

  public static void main(String[] args) throws Exception {
    // 1) 파일의 데이터를 읽을 객체를 준비한다.
    // => 파일의 문자집합을 명시할 수 있다.
    Charset charset = Charset.forName("UTF-8");
    FileReader in = new FileReader("sample/utf8.txt", charset); // 41 42 ea b0 81 ea b0 81

    // 2) 출력 스트림 객체를 생성할 때 파일의 문자 집합을 지정하면
    //    JVM 환경 변수 'file.encoding'에 설정된 값은 무시한다.

    // 현재 JVM 환경 변수 'file.encoding' 값 알아내기
    System.out.printf("file.encoding=%s\n", System.getProperty("file.encoding"));

    // file.encoding이 UTF-8로 되어 있다면,
    // => 영어는 1바이를 읽어서 2바이트 UCS2로 변환한다.
    int ch1 = in.read(); // 41 => 0041('A')
    int ch2 = in.read(); // 42 => 0042('B')

    // => 한글은 3바이를 읽어서 2바이트 UCS2로 변환한다.
    int ch3 = in.read(); // ea b0 80 => ac00('가')
    int ch4 = in.read(); // ea b0 81 => ac01('각')

    // 3) 읽기 도구를 닫는다.
    in.close();

    System.out.printf("%04x, %04x, %04x, %04x\n", ch1, ch2, ch3, ch4);
    System.out.printf("%c, %c, %c, %c\n", ch1, ch2, ch3, ch4);
  }
}






