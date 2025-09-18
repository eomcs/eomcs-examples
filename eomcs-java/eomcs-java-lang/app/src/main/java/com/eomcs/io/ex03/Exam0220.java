// Character Stream - 문자 배열 읽기
package com.eomcs.io.ex03;

import java.io.FileReader;

public class Exam0220 {

  public static void main(String[] args) throws Exception {
    FileReader in = new FileReader("temp/test2.txt");

    // UCS2 문자 코드 값을 저장할 배열을 준비한다.
    // => 이렇게 임시 데이터를 저장하기 위해 만든 바이트 배열을 보통 "버퍼(buffer)"라 한다.
    char[] buf = new char[100];

    // read(버퍼의주소)
    // => 버퍼가 꽉 찰 때까지 읽는다.
    // => 물론 버퍼 크기보다 파일의 데이터가 적으면 파일을 모두 읽어 버퍼에 저장한다.
    // => 리턴 값은 읽은 문자의 개수이다. 바이트의 개수가 아니다!!!!!
    //    FileInputStream.read()의 리턴 값은 읽은 바이트의 개수였다.
    // => 파일을 읽을 때 JVM 환경 변수 'file.encoding'에 설정된 문자코드표에 따라 바이트를 읽는다.
    //    그리고 2바이트 UCS2 코드 값으로 변환하여 리턴한다.
    // => JVM의 문자코드표가 UTF-8이라면,
    //    파일을 읽을 때, 영어나 숫자, 특수기호는 1바이트를 읽어 UCS2으로 변환할 것이고
    //    한글은 3바이트를 읽어 UCS2으로 변환할 것이다.
    int count = in.read(buf);

    // File(UTF-8)  JVM(UCS2)
    // 41       ==> 00 41
    // 42       ==> 00 42
    // 43       ==> 00 43
    // 30       ==> 00 30
    // 31       ==> 00 31
    // 32       ==> 00 32
    // ea b0 80 ==> ac 00
    // ea b0 81 ==> ac 01
    // ea b0 84 ==> ac 04
    // eb 98 98 ==> b6 18
    // eb 98 a5 ==> b6 25

    in.close();

    System.out.printf("%d\n", count);
    for (int i = 0; i < count; i++) {
      System.out.printf("%c(%04x)\n", buf[i], (int) buf[i]);
    }

    System.out.println();

  }

}




