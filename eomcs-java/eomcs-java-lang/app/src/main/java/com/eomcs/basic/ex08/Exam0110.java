// java.util.Map 사용법 - put(), get()
package com.eomcs.basic.ex08;

import java.util.HashMap;

public class Exam0110 {
  public static void main(String[] args) {
    // java.util.Map 구현체
    // - HashMap, Hashtable 이 있다.
    // - key를 가지고 value을 저장하고 꺼낸다.
    //
    HashMap<String,Member> map = new HashMap<>();

    // put(key,value) : 맵에 값 저장하기
    // - key 객체에 대해 hashCode()를 호출하여 정수 값을 얻는다.
    // - 이 해시값을 가지고 저장할 위치를 결정한다.
    //
    map.put("s01", new Member("홍길동", 20));
    // 1) "s01" String 객체에 대해 hashCode()를 호출하여 해시 값을 얻는다.
    // 2) 그 해시 값을 사용하여 저장할 위치를 결정한다.
    // 3) 해당 위치에 Member 객체(의 주소)를 저장한다.

    map.put("s02", new Member("임꺽정", 30));
    map.put("s03", new Member("유관순", 16));
    map.put("s04", new Member("안중근", 20));

    // 이전에 저장할 때 사용한 같은 키로 다른 값을 저장하면 기존 값을 덮어쓴다.
    map.put("s02", new Member("윤봉길", 30));

    // get(key) : 맵에서 값 꺼내기
    // - 저장할 때 사용한 키를 가지고 꺼낸다.
    System.out.println(map.get("s01"));
    // 1) key 객체에 대해 hashCode()를 호출한다.
    // 2) hashCode()의 리턴 값을 가지고 데이터를 찾을 위치를 결정한다.
    // 3) 해당 위치에 있는 key 객체에 대해 equals()를 호출하여 리턴값을 확인한다.
    // 4) equals()의 리턴 값이 true라면 같은 key로 간주하여 해당 위치의 값을 꺼낸다.

    System.out.println(map.get("s02"));
    System.out.println(map.get("s03"));
    System.out.println(map.get("s04"));

    // 존재하지 않는 key를 지정하면 null을 리턴한다.
    System.out.println(map.get("s05"));
  }

}







