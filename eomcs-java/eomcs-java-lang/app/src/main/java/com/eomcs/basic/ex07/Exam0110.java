// java.util.HashSet 클래스 사용 - 중복저장 불가 테스트
package com.eomcs.basic.ex07;

import java.util.HashSet;

// HashSet의 특징
// 1) 값을 중복 저장하지 않는다.
//    - Set은 집합을 의미한다.
//    - 집합에서는 중복값을 허용하지 않는다.
//    - 값의 중복 여부는 hashCode()의 리턴 값이 같고,
//      equals()의 검사 결과가 true일 때
//      같은 값으로 취급한다.
//    - 즉 중복된 값을 저장하고 싶지 않을 때 HashSet을 사용한다.
// 2) 값을 순서대로 저장하지 않는다.
//    - 값 객체의 hashCode()의 리턴 값으로 저장 위치를 계산하기 때문에
//      add() 한 순서대로 저장되지 않는다.
//    - 그래서 값을 index를 이용하여 꺼낼 수 없다.
//
public class Exam0110 {
  public static void main(String[] args) {
    HashSet<String> set = new HashSet<>();

    // Set에 값 추가하기
    set.add(new String("aaa"));
    set.add(new String("bbb"));
    set.add(new String("ccc"));

    // Set은 집합의 특성을 따른다.
    // 같은 값을 중복해서 넣을 수 없다.
    set.add(new String("aaa")); // 위에서 입력한 "aaa"와 인스턴스가 달라도 중복된 것으로 간주.
    set.add(new String("bbb")); // 위에서 입력한 "bbb"와 인스턴스가 달라도 중복된 것으로 간주.

    // null을 넣을 수 있다. 단 중복해서 넣을 수 없다.
    set.add(null);
    set.add(null);

    System.out.println(set);
    // 출력 결과를 보면 입력 순서와 다르다.
    // 이유?
    // - 저장할 때 각 인스턴스의 hashCode() 리턴 값으로 위치를 정하기 때문이다.
  }
}


