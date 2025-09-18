// 인스턴스 메서드와 클래스 메서드의 활용 - wrapper 클래스 
package com.eomcs.oop.ex04;

public class Exam0220 {
  public static void main(String[] args) throws Exception {
    // 다음과 같이 생성자를 통해 Integer 객체를 생성할 수 있지만,
    // 이 생성자는 사용하지 말라고 권고한 것이기 때문에 
    // 가능한 개발 중에 사용하지 말라!
    Integer obj1 = new Integer(100);
    Integer obj2 = new Integer(200);
    Integer obj3 = new Integer(300);

    // 대신 다음과 같이 클래스 메서드를 사용하여 Integer 인스턴스를 생성하라!
    Integer i1 = Integer.valueOf(100); // int 값을 가지고 Integer 객체를 생성할 때!
    Integer i2 = Integer.valueOf(200);
    Integer i3 = Integer.valueOf(300);

    // 인스턴스 메서드 사용
    System.out.println(i2.compareTo(i1));
    System.out.println(i2.compareTo(i3));

    int v1 = i2.intValue(); // Integer 객체에서 int 값을 뽑아 낼 때 
    System.out.println(v1);

    // 스태틱 메서드 = 클래스 메서드 사용
    int v2 = Integer.parseInt("1280"); // String ===> int 변환
    String s1 = Integer.toBinaryString(77);
    String s2 = Integer.toOctalString(77);
    String s3 = Integer.toHexString(77);
    System.out.printf("77 = %s, %s, %s\n", s1, s2, s3);

    Integer x1 = Integer.valueOf("44"); // 문자열에 있는 수를 10진수로 간주한다.
    Integer x2 = Integer.valueOf("44", 16); // 16진수라고 지정한다.
    System.out.printf("%d, %d\n", x1, x2);

    float f = Float.parseFloat("3.14f");
    boolean b = Boolean.parseBoolean("true");
    System.out.printf("%f, %b\n", f, b);

    Float f2 = Float.valueOf("3.14f");
    System.out.printf("%f\n", f2);
  }
}





