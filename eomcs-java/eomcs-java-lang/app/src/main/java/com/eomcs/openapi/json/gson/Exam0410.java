// 객체 --> JSON 문자열 : 다른 객체를 포함하는 경우
package com.eomcs.openapi.json.gson;

import java.sql.Date;
import com.google.gson.Gson;

public class Exam0410 {
  public static void main(String[] args) {

    // 1) 객체 준비
    Member m = new Member();
    m.setNo(100);
    m.setName("홍길동");
    m.setEmail("hong@test.com");
    m.setPassword("1111");
    m.setPhoto("hong.gif");
    m.setTel("010-2222-1111");
    m.setRegisteredDate(new Date(System.currentTimeMillis()));

    Board b = new Board();
    b.setNo(1);
    b.setTitle("제목");
    b.setContent("내용");
    b.setWriter(m);
    b.setViewCount(98);
    b.setLike(5);
    b.setRegisteredDate(new Date(System.currentTimeMillis()));

    String jsonStr = new Gson().toJson(b);

    System.out.println(jsonStr);
  }
}

// 다른 객체를 포함했을 때 JSON 형식
// {
//    프로퍼티명 : 값,
//    프로퍼티명 : {프로퍼티명:값,프로퍼티명:값,...},
//    ...
// }
//
// 예)
//{ 
//  "no":1,
//  "title":"제목",
//  "content":"내용",
//  "writer":{
//    "no":100,
//    "fullname":"홍길동",
//    "email":"hong@test.com",
//    "password":"1111",
//    "photo":"hong.gif",
//    "tel":"010-2222-1111",
//    "registeredDate":"1월 8, 2024"
//  },
//  "registeredDate":"1월 8, 2024",
//  "viewCount":98,
//  "like":5
//}

