// java.util.Hashtable - value 목록 꺼내기
package com.eomcs.basic.ex08;

import java.util.Enumeration;
import java.util.Hashtable;

public class Exam0420 {
  public static void main(String[] args) {

    Hashtable<String,Member> table = new Hashtable<>();
    table.put("s01", new Member("홍길동", 20));
    table.put("s02", new Member("임꺽정", 30));
    table.put("s03", new Member("유관순", 16));
    table.put("s04", new Member("안중근", 20));
    table.put("s05", new Member("윤봉길", 30));

    // Hashtable 에서는 value 목록을 뽑아주는 values() 외에
    // elements() 도 있다.
    // 사용법은 Iterator 와 유사하다.
    Enumeration<Member> values = table.elements();
    while (values.hasMoreElements()) {
      System.out.println(values.nextElement());
    }

  }

}


/*

List vs Set vs Map

항목                     List         Set                     Map
1) 중복 저장        |    가능   |    불가능         | key 불가능, value 가능
2) null 허용 여부   |    가능   |    가능(한 개만)  | HashMap(key/value 가능),
                                                    | Hashtable(key/value 불가능)
 */