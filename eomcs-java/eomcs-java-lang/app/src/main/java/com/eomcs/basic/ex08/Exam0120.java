// java.util.Map 사용법 - 사용자 정의 데이터 타입을 key로 사용할 경우
package com.eomcs.basic.ex08;

import java.util.HashMap;

public class Exam0120 {

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

    System.out.println(map.get(k6));
    // k6로 값을 찾으면 없다고 나온다.
    // 이유?
    // - 값을 저장할 때,
    //   key 객체의 hashCode() 리턴 값으로 위치를 계산하여 저장한다.
    // - 값을 꺼낼 때,
    //   key 객체의 equals()의 리턴 값으로 같은 Key 인지 검사한다.
    //   key 객체의 hashCode() 리턴 값으로 위치를 계산한다.
    // - 따라서 hashCode()의 리턴 값과
    //   equals()의 비교 결과가 다르다고 나오면
    //   같은 key가 아니기 때문에 값을 꺼낼 수 없다.
    // - 우리가 사용한 MyKey 클래스는 hashCode()와 equals()를
    //   오버라이딩 하지 않았기 때문에,
    //   인스턴스 필드의 값이 같더라도 인스턴스가 다르면
    //   hashCode()의 리턴 값이 다르게 나온다.
    //   equals() 또한 false를 리턴한다.
    //
    // 해결책?
    // - 인스턴스가 다르더라도 인스턴스 필드의 값이 같을 때는
    //   hashCode()의 리턴 값이 같게 오버라이딩 하라!
    //   또한 equals()의 리턴 값이 true가 되게 오버라이딩 하라!
  }

}


