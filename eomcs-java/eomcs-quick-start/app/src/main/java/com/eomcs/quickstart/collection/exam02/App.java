package com.eomcs.quickstart.collection.exam02;

import java.util.ArrayList;
import java.util.List;

// List 인터페이스:
// - java.util 패키지에 소속되어 있다.
// - Collection을 상속한다.
// - 항목의 추가 순서를 유지하고 중복을 허용한다.
// - 인덱스(0부터 시작)로 항목에 접근할 수 있다.
//
// Collection을 상속한 후 추가된 주요 메서드:
//   E get(int index)                  인덱스에 해당하는 항목 반환
//   E set(int index, E element)       인덱스 위치의 항목을 교체하고 이전 항목 반환
//   void add(int index, E element)    인덱스 위치에 항목 삽입. 기존 항목은 뒤로 밀림
//   E remove(int index)               인덱스 위치의 항목을 제거하고 반환
//   int indexOf(Object o)             항목의 첫 번째 인덱스 반환. 없으면 -1
//   int lastIndexOf(Object o)         항목의 마지막 인덱스 반환. 없으면 -1
//   List<E> subList(int from, int to) from 이상 to 미만 구간의 부분 리스트 반환
//   ListIterator<E> listIterator()    양방향 순회가 가능한 ListIterator 반환

public class App {

  public static void main(String[] args) {

    List<String> list = new ArrayList<>();

    // 1. add() - 순서 유지, 중복 허용
    System.out.println("[add - 순서 유지, 중복 허용]");
    list.add("사과");
    list.add("바나나");
    list.add("딸기");
    list.add("바나나"); // 중복 허용
    System.out.println(list); // [사과, 바나나, 딸기, 바나나]

    // 2. get() / set() - 인덱스로 접근
    System.out.println("\n[get / set - 인덱스 접근]");
    System.out.println("get(1): " + list.get(1)); // 바나나
    String old = list.set(1, "포도"); // 인덱스 1 교체
    System.out.println("set(1, \"포도\") 이전 값: " + old); // 바나나
    System.out.println(list); // [사과, 포도, 딸기, 바나나]

    // 3. add(index, element) - 특정 위치에 삽입
    System.out.println("\n[add(index) - 특정 위치 삽입]");
    list.add(1, "망고"); // 인덱스 1에 삽입, 기존 항목은 뒤로 밀림
    System.out.println(list); // [사과, 망고, 포도, 딸기, 바나나]

    // 4. remove(int index) - 인덱스로 제거
    System.out.println("\n[remove(index) - 인덱스로 제거]");
    String removed = list.remove(0); // 인덱스 0 제거
    System.out.println("제거된 항목: " + removed); // 사과
    System.out.println(list); // [망고, 포도, 딸기, 바나나]

    // 5. indexOf() / lastIndexOf()
    System.out.println("\n[indexOf / lastIndexOf]");
    list.add("망고"); // 중복 추가
    System.out.println(list); // [망고, 포도, 딸기, 바나나, 망고]
    System.out.println("indexOf(\"망고\"): " + list.indexOf("망고")); // 0
    System.out.println("lastIndexOf(\"망고\"): " + list.lastIndexOf("망고")); // 4
    System.out.println("indexOf(\"사과\"): " + list.indexOf("사과")); // -1

    // 6. subList()
    System.out.println("\n[subList(1, 4)]");
    List<String> sub = list.subList(1, 4); // 인덱스 1 이상 4 미만
    System.out.println(sub); // [포도, 딸기, 바나나]
  }
}
