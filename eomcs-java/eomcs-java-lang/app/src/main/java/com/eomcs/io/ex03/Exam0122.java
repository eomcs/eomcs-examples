// character stream - 문자 단위로 읽기 
package com.eomcs.io.ex03;

import java.io.FileReader;

public class Exam0122 {

  public static void main(String[] args) throws Exception {
    // 1) 파일의 데이터를 읽을 객체를 준비한다. 
    FileReader in = new FileReader("sample/ms949.txt"); // 41 42 b0 a1 b0 a2

    // 2) 출력 스트림 객체를 생성할 때 파일의 문자 집합을 지정하지 않으면,
    //    JVM 환경 변수 'file.encoding'에 설정된 문자코드표에 따라 
    //    바이트를 읽어서 UCS2로 바꾼 후에 리턴한다.

    // 현재 JVM 환경 변수 'file.encoding' 값 알아내기
    System.out.printf("file.encoding=%s\n", System.getProperty("file.encoding"));

    // file.encoding이 UTF-8로 되어 있다면,
    // => 영어는 1바이를 읽어서 2바이트 UCS2로 변환한다.
    // => MS949도 영어인 경우 UTF-8과 같기 때문에 변환하는데 문제가 없다.
    int ch1 = in.read(); // 41 => 0041('A')
    int ch2 = in.read(); // 42 => 0042('B')

    // => 한글은 MS949와 UTF-8이 다르다.
    //    MS949로 인코딩 된 것을 UTF-8로 간주하여 변환한다면,
    //    UTF-8 문자가 아니기 때문에 
    //    다음과 같이 잘못되었다는 의미로 특정 값(fffd)으로 변환한다.
    int ch3 = in.read(); // b0 a1 => fffd => �
    int ch4 = in.read(); // b0 a2 => fffd => �

    // 3) 읽기 도구를 닫는다.
    in.close();

    System.out.printf("%04x, %04x, %04x, %04x\n", ch1, ch2, ch3, ch4);
  }
}






