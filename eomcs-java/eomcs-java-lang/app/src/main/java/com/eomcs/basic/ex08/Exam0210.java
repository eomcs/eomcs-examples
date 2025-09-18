// java.util.HashMap - key 목록을 사용하여 맵에서 값을 꺼내기
package com.eomcs.basic.ex08;

import java.util.HashMap;
import java.util.Iterator;

public class Exam0210 {
  public static void main(String[] args) {

    HashMap<String,Member> map = new HashMap<>();
    map.put("s01", new Member("홍길동", 20));
    map.put("s02", new Member("임꺽정", 30));
    map.put("s03", new Member("유관순", 16));
    map.put("s04", new Member("안중근", 20));
    map.put("s05", new Member("윤봉길", 30));

    // - HashMap에서 key 목록을 꺼내기
    java.util.Set<String> keys = map.keySet(); // key 객체들이 들어있는 집합(컬렉션)을 리턴한다.

    // key가 들어있는 Set에서 값을 꺼내는 방법
    // 1) Iterator 사용
    Iterator<String> 키를꺼내주는객체 = keys.iterator();
    while (키를꺼내주는객체.hasNext()) {
      String key = 키를꺼내주는객체.next();
      System.out.println(map.get(key)); // key를 사용하여 값을 꺼낸다.
    }
    System.out.println("---------------------");

    // 2) 배열을 사용
    String[] keyArray = new String[keys.size()];
    keys.toArray(keyArray);
    for (String key : keyArray) {
      System.out.println(map.get(key)); // key를 사용하여 값을 꺼낸다.
    }
    System.out.println("---------------------");

    // 3) 만약 컬렉션 객체가 java.util.Iterable 규칙을 따라 만든 클래스라면
    //    for(:) 반복문을 바로 사용할 수 있다.
    for (String key : keys/* 배열 또는 Iterable 객체 */) {
      System.out.println(map.get(key));
    }
    System.out.println("---------------------");

  }

}







