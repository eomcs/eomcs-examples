package com.eomcs.quiz.ex01.sol;

// 출처: codefights.com
//
// 원과 점이 주어질 때, 점이 원 안에 있는지를 검사하라!
//
// 파라미터:
//   xa : 점의 x 좌표
//   ya : 점의 y 좌표
//   xc : 원의 중심 x 좌표 
//   yc : 원의 중심 y 좌표
//   rc : 원의 반지름
// 리턴 값:
//   boolean : true 또는 false
// 
// 테스트 예)
//   isInsideTheCircle(0, 0, 0, 0, 1) = true
//   isInsideTheCircle(-1, -1, 0, 0, 1) = false
//
/*
Given a point and a circle, determine if the point lies strictly 
inside the circle.

Example

isInsideTheCircle(0, 0, 0, 0, 1) = true
isInsideTheCircle(-1, -1, 0, 0, 1) = false

[input] integer xa
x coordinate of the given point

[input] integer ya
y coordinate of the given point

[input] integer xc
x coordinate of the center of the circle

[input] integer yc
y coordinate of the center of the circle

[input] integer rc
the radius of the circle

[output] boolean

true, if the point (xa, ya) lies inside the circle (xc, yc, rc), false otherwise
 */
//
// [시간 복잡도]
// - O(1): 시간 복잡도는 일정하다.
//
public class Test12x {
  public static void main(String[] args) {
    System.out.println(isInsideTheCircle(0, 0, 0, 0, 1) == true);
    System.out.println(isInsideTheCircle(-1, -1, 0, 0, 1) == false);
  }

  static boolean isInsideTheCircle(int xa, int ya, int xc, int yc, int rc) {
    // 점의 x 축 길이를 구한다.
    int a = xa - xc; // x 축 길이 = 점의 x 좌표 - 원의 중심 x 좌표  
    int b = ya - yc; // y 축 길이 = 점의 y 좌표 - 원의 중심 y 좌표
    int result = a * a + b * b; // 피타 고라스 공식 : c^2 = a^2 + b^2
    if (result <= (rc * rc)) {
      return true;
    }
    return false;
  }
}

// 피타고라스의 정리 이용
// => a^2 + b^2 = c^2
// 