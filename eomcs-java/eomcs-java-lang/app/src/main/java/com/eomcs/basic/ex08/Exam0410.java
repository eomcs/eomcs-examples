// HashMap vs Hashtable
package com.eomcs.basic.ex08;

import java.util.HashMap;
import java.util.Hashtable;

public class Exam0410 {
  public static void main(String[] args) {

    HashMap<String,Object> map = new HashMap<>();

    map.put(null, "홍길동"); // 1) null을 key로 사용할 수 있다.
    map.put("s01", null); // 2) value가 null이 될 수 있다.
    // 3) 동기화를 지원하지 않는다.
    // => 멀티스레드가 동시에 사용할 때 문제가 발생할 수 있다.
    // => 대신 실행 속도가 빠르다.

    System.out.println(map.get(null));
    System.out.println(map.get("s01"));
    System.out.println("--------------------------------");

    Hashtable<String,Object> table = new Hashtable<>();

    //    table.put(null, "홍길동"); // 실행 오류! 1) key가 null이 될 수 없다.
    //    table.put("s02", null); // 실행 오류! 2) value가 null이 될 수 없다.
    // 3) 동기화 지원

    // 결론!
    // => key나 value에 null을 사용하고 싶다면 HashMap을 사용하라.
    // => key나 value가 null이 되어서는 안된다면 Hashtable을 사용하라.

  }

}


