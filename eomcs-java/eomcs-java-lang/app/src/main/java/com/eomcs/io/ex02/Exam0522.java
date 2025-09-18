// Byte Stream - 텍스트 데이터 읽기 II
package com.eomcs.io.ex02;

import java.io.FileInputStream;

public class Exam0522 {

  public static void main(String[] args) throws Exception {
    // JVM 환경 변수 'file.encoding' 값
    System.out.printf("file.encoding=%s\n", System.getProperty("file.encoding"));

    FileInputStream in = new FileInputStream("sample/ms949.txt"); // 41 42 b0 a1 b0 a2 b6 ca 8c 63 b6 cb(AB가각똘똠)

    // 파일의 데이터를 한 번에 읽어보자.
    byte[] buf = new byte[1000];
    int count = in.read(buf);

    in.close();

    String str = new String(buf, 0, count);
    // 바이트 배열에 MS949 문자집합에 따라 인코딩 된 데이터가 들어 있는데,
    // String 클래스는 UTF-8 문자집합으로 인코딩 되었다고 가정하기 때문에
    // UTF-16으로 정확하게 변환할 수 없는 것이다.

    System.out.println(str);

    // ms949.txt 파일을 읽을 때 문자가 깨지는 이유?
    // - 한글이 다음과 같이 MS949 문자 집합으로 인코딩 되어 있다.
    //      ms949.txt => 41 42 b0 a1 b0 a2
    // - String 객체를 생성할 때 바이트 배열의 문자집합을 알려주지 않으면
    //   JVM 환경 변수 'file.encoding'에 설정된 문자집합이라고 가정한다.
    //
    // 1) 이클립스에서 실행 => 실패!
    // - 이클립스에서  JVM을 실행할 때 '-Dfile.encoding=UTF-8' 옵션을 붙인다.
    // - 따라서 String 클래스는 바이트 배열의 값을 UCS2로 변환할 때 UTF-8 문자표를 사용한다.
    // - 문제는, 바이트 배열의 값이 MS949로 인코딩 되어 있다는 사실이다.
    //   즉 잘못된 문자표를 사용하니까 변환을 잘못한 것이다.
    //
    // 2) Windows 콘솔에서 실행 => 성공!
    // - JVM을 실행할 때 'file.encoding' 환경 변수를 지정하지 않으면,
    //   OS의 기본 문자 집합으로 설정한다.
    // - Windows의 기본 문자 집합은 MS949이다.
    // - 따라서 file.encoding의 값은 MS949로 설정된다.
    // - 바이트 배열의 값이 MS949 이기 때문에 MS949 문자표로 변환하면
    //   UCS2 문자 코드로 잘 변환되는 것이다.
    //
    // MS949 코드를 UTF-8 문자로 가정하고 다룰 때 한글이 깨지는 원리!
    // - ms949.txt
    //   => 01000001 01000010 10110000 10100001 10110000 10100010 = 41 42 b0 a1 b0 a2
    // - MS949 코드를 변환할 때 UTF-8 문자표를 사용하면 다음과 같이 잘못된 변환을 수행한다.
    //   byte(UTF-8) => char(UCS2)
    //     01000001  -> 00000000 01000001 (00 41) = 'A' <-- 정상적으로 변환되었음.
    //     01000010  -> 00000000 01000010 (00 42) = 'B' <-- 정상적으로 변환되었음.
    //     10110000  -> 꽝 (xx xx) <-- 해당 바이트가 UTF-8 코드 값이 아니기 때문에 UCS2로 변환할 수 없다.
    //     10100001  -> 꽝 (xx xx) <-- 그래서 꽝을 의미하는 특정 코드 값이 들어 갈 것이다.
    //     10110000  -> 꽝 (xx xx) <-- 그 코드 값을  문자로 출력하면 => �
    //     10100010  -> 꽝 (xx xx)
  }

}
