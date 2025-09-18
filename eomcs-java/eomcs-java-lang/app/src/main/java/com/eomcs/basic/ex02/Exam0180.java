// String - 다양한 생성자 활용
package com.eomcs.basic.ex02;

public class Exam0180 {
  public static void main(String[] args) throws Exception {
    System.out.printf("JVM의 file.encoding=%s\n", System.getProperty("file.encoding"));

    // String 인스턴스 생성
    String s1 = new String();
    // => 내부적으로 문자의 코드 값을 저장할 char 배열(버전 1.8 까지)
    // 또는 byte 배열(버전 1.9 부터)을 생성한다.
    // => 생성자에서 넘겨주는 값을 배열에 저장한다.
    // => 만약 생성자에 아무것도 넘겨주지 않으면 빈 배열이 생성된다.

    System.out.println(s1); // 빈 문자열 출력

    String s2 = new String("Hello"); // 문자열 리터럴로 String 인스턴스 생성
    System.out.printf("s2=%s\n", s2);

    char[] chars = {'H', 'e', 'l', 'l', 'o'};
    String s3 = new String(chars); // char 배열로 String 인스턴스 생성
    System.out.printf("s3=%s\n", s3);

    byte[] bytes =
      {(byte) 0xb0, (byte) 0xa1, (byte) 0xb0, (byte) 0xa2, 0x30, 0x31, 0x32, 0x41, 0x42, 0x43};
    // 문자 코드 값이 저장된 바이트 배열로 String 인스턴스 생성
    String s4 = new String(bytes);
    System.out.printf("s4=%s\n", s4);
    // 한글이 깨진다. 이유?
    // => String 생성자는 파라미터로 받은 바이트 배열에 ISO-8859-1 문자 코드가 들어 있다가 간주한다.
    // 즉 0xb0 0xa1 값이 한글 '가'가 아니라 0xb0와 0xa1 각각을 영어라 간주하고
    // ISO-8859-1 에 정의된 문자표에 따라 유니코드(UTF-16)으로 바꿔 저장한다.
    // 0xb0(ISO-8859-1) ==> 0x00b0(UTF-16)
    // => 제대로 한글을 처리하려면?
    // 생성자에 바이트 배열을 넘겨줄 때
    // 배열에 들어 있는 코드 값이 어떤 문자표의 코드 값인지 알려줘야 한다.
    //

    String s5 = new String(bytes, "euc-kr");
    System.out.printf("s5=%s\n", s5);

    byte[] bytes2 =
      {(byte) 0xac, (byte) 0x00, (byte) 0xac, (byte) 0x01, 0x00, 0x61, 0x00, 0x62, 0x00, 0x63};

    // 바이트 배열에 들어 있는 코드는 무슨 문자표로 작성했는지 정확하게 알려줘야 한다.
    // 그래야 자바의 문자 코드로 제대로 변경할 수 있을 것이다.
    String s6 = new String(bytes2);
    System.out.printf("s6=%s\n", s6);

    s6 = new String(bytes2, "utf-16");
    System.out.printf("s6=%s\n", s6);

    byte[] bytes3 = {(byte) 0xea, (byte) 0xb0, (byte) 0x80, (byte) 0xea, (byte) 0xb0, (byte) 0x81,
        0x61, 0x62, 0x63};

    String s7 = new String(bytes3);
    System.out.printf("s7=%s\n", s7);

    s7 = new String(bytes3, "utf-8");
    System.out.printf("s7=%s\n", s7);
  }
}


