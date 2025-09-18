// 클래스 정보 추출 - 클래스 이름 알아내기
package com.eomcs.reflect.ex02;

public class Exam0110 {

  static class A {}

  static Object obj = new Object() {
    @Override
    public String toString() {
      return "익명 클래스";
    }
  };

  public static void main(String[] args) throws Exception {
    //1) 패키지 멤버 클래스
    Class<?> clazz = Class.forName("java.lang.String");

    // '클래스의 타입 정보를 담은 객체'를 통해 클래스 정보를 추출할 수 있다.
    System.out.println(clazz.getSimpleName()); // String
    System.out.println(clazz.getName()); // java.lang.String
    System.out.println(clazz.getCanonicalName()); // java.lang.String
    System.out.println(clazz.getTypeName()); // java.lang.String

    System.out.println("------------------------------------------");

    //2) 중첩 클래스
    Class<?> clazz2 = Class.forName("com.eomcs.reflect.ex02.Exam0110$A");

    // 클래스의 타입 객체를 통해 클래스 정보를 추출할 수 있다.
    System.out.println(clazz2.getSimpleName()); // A
    System.out.println(clazz2.getName()); // com.eomcs.reflect.ex02.Exam0110$A
    System.out.println(clazz2.getCanonicalName()); // com.eomcs.reflect.ex02.Exam0110.A
    System.out.println(clazz2.getTypeName()); // com.eomcs.reflect.ex02.Exam0110$A

    System.out.println("------------------------------------------");

    //3) 익명 클래스
    Class<?> clazz3 = obj.getClass(); // 익명 클래스의 인스턴스로부터 클래스 정보 추출

    // 클래스의 타입 객체를 통해 클래스 정보를 추출할 수 있다.
    System.out.println(clazz3.getSimpleName()); //
    System.out.println(clazz3.getName()); // com.eomcs.reflect.ex02.Exam0110$1
    System.out.println(clazz3.getCanonicalName()); // null
    System.out.println(clazz3.getTypeName()); // com.eomcs.reflect.ex02.Exam0110$1
  }

}
