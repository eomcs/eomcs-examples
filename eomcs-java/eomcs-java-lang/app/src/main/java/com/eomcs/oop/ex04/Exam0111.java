// 생성자 활용 예 - 자바에서 제공하는 클래스 사용을 통해 생성자 활용을 익혀보자!
package com.eomcs.oop.ex04;

public class Exam0111 {

  public static void main(String[] args) throws Exception {
    // 생성자를 호출하여 문자열 인스턴스를 초기화시킨다.

    String s0 = new String(); // default constructor 호출
    System.out.println(s0);
    System.out.println("-----------------------------------");

    // => 문자열 리터럴을 사용하여 String 인스턴스를 초기화시키기.
    String s1 = new String("HelloABCabc012가각간"); // String(스트링 리터럴) 생성자를 호출하여 인스턴스 초기화
    System.out.println(s1);
    System.out.println("-----------------------------------");

    // => char[] 을 사용하여 String 인스턴스 초기화시키기.
    char[] chars = new char[] {'H', 'e', 'l', 'l', 'o'};
    String s2 = new String(chars); // String(char[]) 생성자를 호출하여 인스턴스 초기화
    System.out.println(s2);
    System.out.println("-----------------------------------");

    s2 = new String(chars, 1, 3); // String(char[], offset, count) 생성자를 호출하여 인스턴스 초기화
    System.out.println(s2);
    System.out.println("-----------------------------------");

    // => 바이트 배열을 가지고 String 인스턴스 초기화시키기
    byte[] bytes = {
        (byte)0x48, // H 
        (byte)0x65, // e
        (byte)0x6c, // l
        (byte)0x6c, // l
        (byte)0x6f  // o 
    };
    String s3 = new String(bytes); // String(byte[]) 생성자를 호출하여 인스턴스 초기화

    System.out.printf("%s, %s, %s\n", s1, s2, s3);
  }
}

// 생성자의 활용
// => 인스턴스 변수를 초기화시키기 위해 여러 개의 생성자를 만들어 제공할 수 있다.
// => 자신에게 맞는 적절한 생성자를 호출하여 인스턴스를 초기화시킨 후 사용하면 된다. 















