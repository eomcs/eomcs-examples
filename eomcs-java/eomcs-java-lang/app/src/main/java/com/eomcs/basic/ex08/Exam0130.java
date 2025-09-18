// java.util.Map - 사용자 정의 데이터 타입을 key로 사용할 경우 II
package com.eomcs.basic.ex08;

import java.util.HashMap;
import java.util.Objects;

public class Exam0130 {

  public static void main(String[] args) {

    class MyKey {
      String major;
      int no;

      public MyKey(String major, int no) {
        this.major = major;
        this.no = no;
      }

      @Override
      public String toString() {
        return "MyKey [major=" + major + ", no=" + no + "]";
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (getClass() != obj.getClass())
          return false;
        MyKey other = (MyKey) obj;
        return Objects.equals(major, other.major) && no == other.no;
      }
    }

    MyKey k1 = new MyKey("컴공", 1);
    MyKey k2 = new MyKey("컴공", 2);
    MyKey k3 = new MyKey("컴공", 3);
    MyKey k4 = new MyKey("컴공", 4);
    MyKey k5 = new MyKey("컴공", 5);

    HashMap<MyKey, Member> map = new HashMap<>();

    map.put(k1, new Member("홍길동", 20));
    map.put(k2, new Member("임꺽정", 30));
    map.put(k3, new Member("유관순", 16));
    map.put(k4, new Member("안중근", 30));
    map.put(k5, new Member("윤봉길", 25));

    System.out.println(map.get(k1));
    System.out.println(map.get(k2));
    System.out.println(map.get(k3));
    System.out.println(map.get(k4));
    System.out.println(map.get(k5));

    System.out.println("-----------------------------");


    // k3 와 같은 값을 갖는 키를 생성한다.
    MyKey k6 = new MyKey("컴공", 3);

    System.out.println(k3 == k6);
    System.out.printf("equals(): %b\n", k3.equals(k6));
    System.out.printf("hashCode(): %d, %d\n", k3.hashCode(), k6.hashCode());
    System.out.println("-----------------------------------");

    // equals()의 리턴 값이 true 이더라도
    // hashCode()의 리턴 값이 다르면 
    // 다른 key로 간주한다.
    System.out.println(map.get(k6));
  }

}


