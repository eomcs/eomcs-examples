package com.eomcs.quiz.ex01;
// 출처: codefights.com
// 
// 주어진 년도가 윤년인지 아닌지 검사하라!
// 
// 예)
//    윤년: 1988, 1992, 1996, 1600, 2000, 2400
//    윤년아님: 1700, 1800, 1900, 2100, 2200, 2300, 2500, 2600
// 
/*
Year is leap if its number is divisible by 4 and isn't divisible by 100 or if its number is divisible by 400. 
Determine if a given year is leap or not.

[input] integer year
number of the year

[output] boolean
true if the given year is leap, false otherwise
 */
//
// [시간 복잡도]
// - ?
//
public class Test16 {

  public static void main(String[] args) {
    System.out.println(leapYear(1988));
    System.out.println(leapYear(1992));
    System.out.println(leapYear(1996));
    System.out.println(leapYear(2000));
    System.out.println(leapYear(2400));
    System.out.println(leapYear(2100) == false);
    System.out.println(leapYear(2200) == false);
  }

  static boolean leapYear(int year) {
    // 이 메서드를 완성하시오!
    return false;
  }
}
