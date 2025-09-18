package com.eomcs.quiz.ex02.sol;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// copyright by codefights.com
// 
// 삼각형의 세변 길이가 주어질 때,
// 직각 삼각형인지 판별하라!
// 예) 
//    rightTriangle(new int[] {3, 4, 5}) ==> true
//    rightTriangle(new int[] {3, 6, 7}) ==> false
//
// 구현조건)
// - 세변의 길이를 정렬할 때 자바 컬렉션 API를 사용하라!
//   - Arrays.asList()
//   - Collections.sort()
// 
/*
For a given triangle determine if it is a right triangle.

[input] array.integer sides
array of three integers representing triangle sides

[output] boolean
true if the triangle with sides from the sides array is a right triangle, false otherwise
 */
//
// [시간 복잡도]
// - ? 
//
public class Test11x {

  public static void main(String[] args) {
    System.out.println(rightTriangle(new int[] {4, 5, 3}) == true);
    System.out.println(rightTriangle(new int[] {7, 6, 3}) == false);
  }

  static boolean rightTriangle(int[] sides) {
    // 이 메서드를 완성하시오!
    // 1) 먼저 int 배열을 가지고 List 를 만든다.
    // 방법1:
    //    ArrayList<Integer> list = new ArrayList<>();
    //    for (int i : sides) {
    //      list.add(i);
    //    }

    // 방법2:
    List<Integer> list = Arrays.asList(sides[0], sides[1], sides[2]);

    // 2) List 에 들어 있는 값을 정렬하여 가장 큰 값이 맨 뒤로 오게 한다.
    //    => 가장 긴 변이 빗변이다.
    Collections.sort(list);

    //    for (int value : list) {
    //      System.out.println(value);
    //    }

    return list.get(0) * list.get(0) + list.get(1) * list.get(1) == list.get(2) * list.get(2);
  }
}
