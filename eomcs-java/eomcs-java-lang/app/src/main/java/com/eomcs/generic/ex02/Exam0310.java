// 제네릭(Generic) - 응용 
package com.eomcs.generic.ex02;

import java.util.HashMap;

public class Exam0310 {
  public static void main(String[] args) {
    // 제네릭 정보가 필요한 클래스를 사용할 때는 
    // 어떤 클래스를 다룰 것인지 타입이름을 알려줘야 한다.
    // 그런데 다음 코드처럼 "key 값으로 어떤 클래스를 사용할 것이고"
    // "value 값으로 어떤 클래스를 사용할 것인지" 알려주지 않으면
    // 컴파일러가 경고를 띄운다. 물론 경고시 무시해도 실행에 문제가 없다.
    // Object 타입으로 지정하더라도 클래스 이름을 명시하는 것이 좋다.
    HashMap map = new HashMap(); // Object key, Object value를 다룬다.
    map.put("aaa", "문자열");
    map.put(new Integer(100), new Member("홍길동", 20));

    System.out.println(map.get("aaa"));
    System.out.println(map.get(100));
    System.out.println("------------------------------------------");

    // 위와 같이 제네릭 정보를 넘기지 않는 것 보다,
    // 차라리 다음과 같이 명확하게 Object 타입을 지정하는 것이 좋다. 
    // => 어떤 타입의 key와 어떤 타입의 value를 저장할 것인지
    //    다른 개발자에게 명확하게 알려주는 효과가 있기 때문에 다음을 권장한다.
    HashMap<Object,Object> map2 = new HashMap<>();
    map2.put("aaa", "문자열");
    map2.put(new Integer(100), new Member("홍길동", 20));

    // key: String
    // value: Member
    HashMap<String,Member> map3 = new HashMap<>();
    //    map3.put("aaa", "문자열"); // 컴파일 오류!
    //    map3.put(new Integer(100), new Member("홍길동", 20)); // 컴파일 오류!
    map3.put("aaa", new Member("홍길동", 20)); // OK!

    // 제네릭을 지정하면 값을 꺼낼 때 형변환할 필요가 없다.
    Member m = map3.get("aaa"); // OK!

  }
}








