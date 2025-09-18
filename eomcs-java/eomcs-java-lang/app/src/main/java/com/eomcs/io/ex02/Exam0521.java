// Byte Stream - 텍스트 데이터 읽기 II
package com.eomcs.io.ex02;

import java.io.FileInputStream;

public class Exam0521 {

  public static void main(String[] args) throws Exception {
    // JVM 환경 변수 'file.encoding' 값
    System.out.printf("file.encoding=%s\n", System.getProperty("file.encoding"));

    FileInputStream in = new FileInputStream("temp/utf8.txt");

    // 파일의 데이터를 한 번에 읽어보자.
    byte[] buf = new byte[1000]; 
    int count = in.read(buf); // <== 41 42 ea b0 80 ea b0 81 (AB가각)

    in.close();

    // 읽은 바이트 수를 출력해보자.
    System.out.printf("읽은 바이트 수: %d\n", count);

    // 읽은 바이트를 String 객체로 만들어보자.
    // - 바이트 배열에 저장된 문자 코드를 
    //   JVM이 사용하는 문자 집합(UCS2=UTF16BE)의 코드 값으로 변환한다.
    // - 바이트 배열에 들어 있는 코드 값이 어떤 문자 집합의 값인지 알려주지 않는다면,
    //   JVM 환경 변수 file.encoding에 설정된 문자 집합으로 가정하고 변환을 수행한다.
    String str = new String(buf, 0, count); 
    // 바이트 배열이 어떤 문자집합으로 인코딩 된 것인지 알려주지 않으면, 
    // file.encoding에 설정된 문자집합으로 인코딩된 것으로 간주한다.

    System.out.println(str);

    // 바이트가 어떤 문자 집합으로 인코딩 되었는지 알려주지 않는다면?
    // String 객체를 생성할 때 다음의 규칙에 따라 변환한다.
    // => JVM 환경 변수 'file.encoding' 에 설정된 문자집합으로 가정하고 UCS2 문자 코드로 변환한다.
    //
    // 1) 이클립스에서 실행 => 성공!
    // - JVM 실행 옵션에 '-Dfile.encoding=UTF-8' 환경 변수가 자동으로 붙는다. 
    // - 그래서 String 클래스는 바이트 배열의 값을 UCS2로 바꿀 때 UTF-8 문자표를 사용하여 UCS2 로 변환한다.
    // - 예1)
    //   utf8.txt  => 41 42 ea b0 80 ea b0 81
    //   UCS2      => 0041 0042 ac00 ac01  <== 정상적으로 바뀐다.
    //
    // 2) Windows 콘솔에서 실행 => 실패!
    // - JVM을 실행할 때 file.encoding을 설정하지 않으면 
    //   OS의 기본 문자집합으로 설정한다.
    // - Windows의 기본 문자집합은 MS949 이다.
    // - 따라서 file.encoding 값은 MS949가 된다.
    // - 바이트 배열은 UTF-8로 인코딩 되었는데, 
    //   MS949 문자표를 사용하여 UCS2로 변환하려 하니까 
    //   잘못된 문자로 변환되는 것이다.
    // - 해결책?
    //   JVM을 실행할 때 file.encoding 옵션에 정확하게 해당 파일의 인코딩을 설정하라.
    //   즉 utf8.txt 파일은 UTF-8로 인코딩 되었기 때문에 
    //     '-Dfile.encoding=UTF-8' 옵션을 붙여서 실행해야 UCS2로 정상 변환된다.
    //
    // 3) Linux/macOS 콘솔에서 실행 => 성공!
    // - Linux와 macOS의 기본 문자 집합은 UTF-8이다. 
    // - 따라서 JVM을 실행할 때 '-Dfile.encoding=UTF-8' 옵션을 지정하지 않아도 
    //   해당 파일을 UTF-8 문자로 간주하여 UTF-8 문자표에 따라 UCS2로 변환한다.
    // 
  }

}
