// java.util.Map 사용법 - put(), get() II
package com.eomcs.basic.ex08;

import java.util.HashMap;

public class Exam0111 {
  public static void main(String[] args) {

    HashMap<Integer, Member> map = new HashMap<>();

    // 값을 저장할 때 String을 key로 많이 사용하지만,
    // 다른 어떤 객체라도 key로 사용할 수 있다.
    //
    map.put(100, new Member("홍길동", 20));
    // 100은 객체가 아니다.
    // 그러나 auto-boxing을 통해서 Integer 객체로 전환된다.
    // => map.put(Integer.valueOf(100), new Member("홍길동", 20));

    map.put(200, new Member("임꺽정", 30));
    map.put(300, new Member("유관순", 16));
    map.put(400, new Member("안중근", 20));

    System.out.println(map.get(100));
    System.out.println(map.get(200));
    System.out.println(map.get(300));
    System.out.println(map.get(400));
  }

}


