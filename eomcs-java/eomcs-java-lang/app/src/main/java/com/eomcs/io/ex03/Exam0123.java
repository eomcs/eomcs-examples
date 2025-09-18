// character stream - 문자 단위로 읽기 
package com.eomcs.io.ex03;

import java.io.FileReader;
import java.nio.charset.Charset;

public class Exam0123 {

  public static void main(String[] args) throws Exception {
    // 1) 파일의 데이터를 읽을 객체를 준비한다. 
    // => 읽어들일 파일의 문자집합이 file.encoding 환경변수에 지정된 값과 다르다면,
    //    다음과 같이 출력 스트림을 생성할 때 파일의 문자집합을 알려줘라.
    FileReader in = new FileReader("sample/ms949.txt", Charset.forName("MS949")); // 41 42 b0 a1 b0 a2

    // 2) 출력 스트림 객체를 생성할 때 파일의 문자 집합을 지정하면,
    //    JVM 환경 변수 'file.encoding'에 설정된 문자집합을 무시한다.

    // 현재 JVM 환경 변수 'file.encoding' 값 알아내기
    System.out.printf("file.encoding=%s\n", System.getProperty("file.encoding"));

    // file.encoding이 UTF-8로 설정되어 있지만,
    // 출력 스트림에 'MS949'를 사용한다고 했기 때문에 
    // MS949 문자표에 따라 UCS2로 변환할 것이다.
    int ch1 = in.read(); // 41 => 0041('A')
    int ch2 = in.read(); // 42 => 0042('B')

    // 한글
    int ch3 = in.read(); // b0 a1 => ac00 => '가'
    int ch4 = in.read(); // b0 a2 => ac01 => '각'

    // 3) 읽기 도구를 닫는다.
    in.close();

    System.out.printf("%04x, %04x, %04x, %04x\n", ch1, ch2, ch3, ch4);
  }
}






