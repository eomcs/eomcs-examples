// 객체 --> JSON 문자열 : 배열 다루기
package com.eomcs.openapi.json.gson;

import java.sql.Date;
import com.google.gson.Gson;

public class Exam0210 {
  public static void main(String[] args) {

    // 1) 배열 준비
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

    Member[] members = {m1, m2, m3};

    String jsonStr = new Gson().toJson(members);

    System.out.println(jsonStr);
  }
}

// JSON 배열 형식 - [{ 객체 정보 },{ 객체 정보}, ...]
// => [
//      {"프로퍼티명":값,"프로퍼티명":값, ...},
//      {"프로퍼티명":값,"프로퍼티명":값, ...},
//      {"프로퍼티명":값,"프로퍼티명":값, ...},
//      ...
//    ]
//




