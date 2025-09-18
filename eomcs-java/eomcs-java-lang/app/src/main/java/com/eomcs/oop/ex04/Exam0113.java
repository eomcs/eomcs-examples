// 생성자 활용 예 - 자바에서 제공하는 클래스 사용을 통해 생성자 활용을 익혀보자!
package com.eomcs.oop.ex04;

import java.nio.charset.Charset;

public class Exam0113 {

  public static void main(String[] args) throws Exception {
    System.out.println(Charset.defaultCharset());

    // UTF-8 문자 코드의 바이트 배열을 사용하여 String 인스턴스 초기화시키기.
    byte[] bytes = {
        (byte)0x41, // A
        (byte)0x42, // B
        (byte)0x43, // C
        (byte)0x61, // a
        (byte)0x62, // b
        (byte)0x63, // c
        (byte)0x30, // 0
        (byte)0x31, // 1
        (byte)0x32, // 2
        (byte)0x20, // space
        (byte)0x21, // !
        (byte)0x23, // #
        (byte)0x25, // %
        (byte)0x2b, // +
        (byte)0xea, (byte)0xb0, (byte)0x80, // 가
        (byte)0xea, (byte)0xb0, (byte)0x81, // 각
        (byte)0xeb, (byte)0x98, (byte)0x98, // 똘
        (byte)0xeb, (byte)0x98, (byte)0xa5  // 똥
    };

    String s1 = new String(bytes);
    System.out.println(s1);
    System.out.println("-------------------------------------");

    // 생성자에 바이트 배열을 넘길 때
    // 바이트 배열에 들어 있는 데이터의 문자 코드표를 알려주지 않으면,
    // String 생성자는 OS의 기본 문자 코드표로 간주하여 변환한다.
    // 따라서 위 예제를 Linux, macOS, Unix 의
    // CLI(Command Line Interface; 콘솔창/명령창)에서 실행하면
    // 정상적으로 한글 문자열이 출력되지만,
    // Windows에서 실행하면 한글 문자열이 깨진다.
    //
    // 이유?
    // => Linux, macOS, Unix가 기본으로 사용하는 문자 코드표는 UTF-8이다.
    // => Windows가 기본으로 사용하는 문자 코드표는 MS949이다.
    //
    // 그런데 이클립스에서 실행하면 깨지지 않는 이유?
    // => 이클립스에서 JVM을 실행할 때 입출력 기본 인코딩을 UTF-8로 설정하기 때문이다.
    //
    // Windows CLI에서 깨지지 않게 하는 방법?
    // => JVM 실행 옵션에 다음을 추가하라
    //     -Dfile.encoding=UTF-8
    // => java -Dfile.encoding=UTF-8 -cp bin/main 클래스명
    // => PowerShell 이 아닌 Command 창에서 실행하라!
    //    PowerShell 에서는 -Dfile.encoding 옵션을 제대로 처리하지 못한다.
    //    도트(.)를 분리 문자로 인식한다.
    //
  }
}
















