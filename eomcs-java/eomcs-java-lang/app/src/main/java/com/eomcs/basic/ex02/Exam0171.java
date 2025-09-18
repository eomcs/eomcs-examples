// String - 기타 메서드 : join()
package com.eomcs.basic.ex02;

public class Exam0171 {
  public static void main(String[] args) {

    String[] arr = {"101", "제목", "내용", "4", "2021-2-2"};

    // 1) 배열의 모든 값을 CSV 형식의 한 문자열로 만들기
    // => format() 사용
    String s1 = String.format("%s,%s,%s,%s,%s", arr[0], arr[1], arr[2], arr[3], arr[4]);
    System.out.println(s1);

    // => 가변 파라미터 자리에 배열을 전달해도 된다.
    String s2 = String.format("%s,%s,%s,%s,%s", arr);
    System.out.println(s2);

    // => join() 사용
    String s3 = String.join(",", arr[0], arr[1], arr[2], arr[3], arr[4]);
    System.out.println(s3);

    // => 가변 파라미터 자리에 배열을 전달해도 된다.
    String s4 = String.join(",", arr);
    System.out.println(s4);

    String s5 = String.join("#", arr);
    System.out.println(s5);

    String s6 = String.join("0x0", arr);
    System.out.println(s6);
  }
}


