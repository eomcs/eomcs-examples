// Byte Stream - 텍스트 출력 하기
package com.eomcs.io.ex02;

import java.io.FileOutputStream;

public class Exam0510 {

  public static void main(String[] args) throws Exception {

    String str = new String("AB가각");

    // String 객체의 데이터를 출력하려면
    // 문자열을 담은 byte[] 배열을 리턴 받아야 한다.

    // String.getBytes()
    // => 특정 인코딩을 지정하지 않고 그냥 바이트 배열을 달라고 하면,
    //    String 클래스는 JVM 환경 변수 'file.encoding' 에 설정된 문자집합으로 
    //    바이트 배열을 인코딩 한다.  
    // => 이클립스에서 애플리케이션을 실행할 때 다음과 같이 JVM 환경변수를 자동으로 붙인다. 
    //      $ java -Dfile.encoding=UTF-8 .... 
    // => 그래서 getBytes()가 리턴한 바이트 배열의 인코딩은 UTF-8이 되는 것이다.
    // => 만약 이 예제를 이클립스가 아니라 콘솔창에서 
    //    -Dfile.encoding=UTF-8 옵션 없이 실행한다면,
    //    getBytes()가 리턴하는 바이트 배열은
    //    OS의 기본 인코딩으로 변환될 것이다.
    // => OS 기본 인코딩 
    //      Windows => MS949
    //      Linux/macOS => UTF-8
    // => OS에 상관없이 동일한 실행 결과를 얻고 싶다면, 다음과 같이 file.encoding 옵션을 붙여라
    //      $ java -Dfile.encoding=UTF-8 -cp bin/main .....
    // => 또는 getBytes() 호출할 때 인코딩할 문자집합을 지정하라.
    //      str.getBytes("UTF-8")
    //
    // JVM에 문자를 입출력할 때 사용하는 기본 문자 집합이 무엇인지 알아 본다.
    System.out.printf("file.encoding=%s\n", System.getProperty("file.encoding"));
    byte[] bytes = str.getBytes();  // 문자집합을 지정하지 않으면 file.encoding에 설정된 문자집합으로 인코딩하여 리턴한다.
    //
    // 이클립스: 
    //      UCS2 ==> UTF-8
    //      이클립스의 경우 자바 앱을 실행할 때 file.encoding 변수의 값을 utf-8 로 설정한다.
    // 
    // file.encoding JVM 환경 변수의 값이 설정되어 있지 않을 경우, 
    //      Windows: UCS2 ==> MS949
    //      Linux: UCS2 ==> UTF-8
    //      macOS: UCS2 ==> UTF-8
    //
    for (byte b : bytes) {
      System.out.printf("%x ", b);
    }
    System.out.println();

    // 바이트 배열 전체를 그대로 출력한다.
    FileOutputStream out = new FileOutputStream("temp/utf.txt");
    out.write(bytes);
    out.close();
    System.out.println("데이터 출력 완료!");
  }

}

