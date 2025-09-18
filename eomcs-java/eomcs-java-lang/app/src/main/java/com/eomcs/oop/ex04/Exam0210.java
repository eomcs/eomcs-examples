// 인스턴스 메서드와 클래스 메서드의 활용 - String 클래스
package com.eomcs.oop.ex04;

public class Exam0210 {
  public static void main(String[] args) throws Exception {
    String s1 = new String("Hello");

    // s1 인스턴스(s1 레퍼런스가 가리키는 인스턴스)의 값을 조회하는 메서드 사용
    // 따라서 이 메서드를 호출하려면 반드시 String 인스턴스의 주소를 줘야 한다.
    char c = s1.charAt(1);
    System.out.println(c);

    System.out.println(s1.compareTo("Helli"));
    System.out.println(s1.compareTo("Hello"));
    System.out.println(s1.compareTo("Hellq"));

    System.out.println(s1.contains("ll"));
    System.out.println(s1.contains("ee"));

    // 두 문자열을 연결하여 새 문자열을 만들자!
    String s2 = s1.concat(", world!");
    System.out.println(s1); // 기존 인스턴스의 값은 변경하지 않는다.
    System.out.println(s2); // 새로 문자열을 만든다.

    // 두 인스턴스에 들어 있는 문자열이 같은 지 비교할 때 
    System.out.println(s1.equals("aaa")); // false
    System.out.println(s1.equals("Hello")); // true
    System.out.println(s1 == "Hello"); // false

    System.out.println("-------------------------");

    String s3 = new String("ABC가각똘똠똥");
    // 인스턴스에 들어 있는 문자 코드를 바이트 배열로 만들어 리턴한다.
    byte[] bytes = s3.getBytes(); 
    // => 인스턴스에 들어 있는 각 문자를 바이트 배열에 저장할 때 
    //    인코딩 문자집합을 지정하지 않으면 JVM의 기본 문자집합으로 인코딩 한다. 
    //    이클립스에서 JVM을 실행하면 JVM은 기본으로 UTF-8 문자표를 사용하여 
    //    바이트 배열에 코드 값을 저장한다. 
    for (int i = 0; i < bytes.length; i++)
      System.out.printf("%x,", bytes[i]);
    System.out.println();
    System.out.println("-------------------------");

    // 다른 인스턴스 메서드를 사용하여 바이트 배열을 추출해보자.
    bytes = s3.getBytes("EUC-KR");
    for (int i = 0; i < bytes.length; i++)
      System.out.printf("%x,", bytes[i]);
    System.out.println();
    System.out.println("-------------------------");

    // String 클래스에도 특정 인스턴스가 아닌 일반용으로 
    // 문자열을 다룰 수 있는 메서드를 제공한다.
    // 즉 "클래스 메서드=스태틱 메서드"를 제공한다.

    // => 형식을 갖춘 문자열을 만들기
    String s4 = String.format("%s님 반갑습니다", "홍길동");
    System.out.println(s4);

    // => 구분자와 문자열들을 파라미터로 받아서 새 문자열을 만든다.
    String s5 = String.join(":", "홍길동", "임꺽정", "유관순");
    System.out.println(s5);

    // => primitive 값을 문자열로 만든다.
    String s6 = String.valueOf(true); // "true"
    String s7 = String.valueOf(3.14f); // "3.14"
    String s8 = String.valueOf(100); // "100"
    System.out.println(s6);
    System.out.println(s7);
    System.out.println(s8);


  }
}





