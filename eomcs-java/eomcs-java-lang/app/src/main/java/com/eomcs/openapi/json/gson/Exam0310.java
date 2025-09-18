// 객체 --> JSON 문자열 : 컬렉션 다루기
package com.eomcs.openapi.json.gson;

import java.sql.Date;
import java.util.ArrayList;
import com.google.gson.Gson;

public class Exam0310 {
  public static void main(String[] args) {

    // 1) ArrayList 준비
    Member m1 = new Member();
    m1.setNo(101);
    m1.setName("홍길동");
    m1.setEmail("hong@test.com");
    m1.setRegisteredDate(new Date(System.currentTimeMillis()));

    Member m2 = new Member();
    m2.setNo(102);
    m2.setName("임꺽정");
    m2.setEmail("leem@test.com");
    m2.setRegisteredDate(new Date(System.currentTimeMillis()));

    Member m3 = new Member();
    m3.setNo(103);
    m3.setName("안창호");
    m3.setEmail("ahn@test.com");
    m3.setRegisteredDate(new Date(System.currentTimeMillis()));

    ArrayList<Member> list = new ArrayList<>();
    list.add(m1);
    list.add(m2);
    list.add(m3);

    String jsonStr = new Gson().toJson(list);

    System.out.println(jsonStr);
  }
}

// JSON 컬렉션 형식 - [{ 객체 정보 },{ 객체 정보 }, ...]
// => 배열을 출력한 것과 같다.
//    JSON은 배열과 컬렉션을 구분하지 않는다.
// => [
//      {"프로퍼티명":값,"프로퍼티명":값, ...},
//      {"프로퍼티명":값,"프로퍼티명":값, ...},
//      {"프로퍼티명":값,"프로퍼티명":값, ...},
//      ...
//    ]
//




