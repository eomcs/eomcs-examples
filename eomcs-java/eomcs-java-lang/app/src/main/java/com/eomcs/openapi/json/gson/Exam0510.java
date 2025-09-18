// 객체 --> JSON 문자열 : 다른 객체를 목록으로 포함하는 경우
package com.eomcs.openapi.json.gson;

import java.sql.Date;
import java.util.ArrayList;
import com.google.gson.Gson;

public class Exam0510 {
  public static void main(String[] args) {

    // 1) 객체 준비
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

    ArrayList<Member> members = new ArrayList<>();
    members.add(m1);
    members.add(m2);
    members.add(m3);

    Project p = new Project();
    p.setNo(11);
    p.setTitle("제목");
    p.setContent("내용");
    p.setStartDate(Date.valueOf("2021-1-1"));
    p.setEndDate(Date.valueOf("2021-2-2"));
    p.setOwner(m2);
    p.setMembers(members);

    String jsonStr = new Gson().toJson(p);

    System.out.println(jsonStr);
  }
}

// 다른 객체를 목록으로 포함했을 때 JSON 형식
// {
//    프로퍼티명 : 값,
//    프로퍼티명 : {프로퍼티명:값,프로퍼티명:값,...},
//    프로퍼티명 : [{...},{...},{...},...],
//    ...
// }
//
// 예)
//{  
//  "no":11,
//  "title":"제목",
//  "content":"내용",
//  "startDate":"1월 1, 2021",
//  "endDate":"2월 2, 2021",
//  "owner":{
//    "no":102,
//    "fullname":"임꺽정",
//    "email":"leem@test.com",
//    "registeredDate":"1월 8, 2024"
//  },
//  "members":[
//    {
//      "no":101,
//      "fullname":"홍길동",
//      "email":"hong@test.com",
//      "registeredDate":"1월 8, 2024"
//    },
//    {
//      "no":102,
//      "fullname":"임꺽정",
//      "email":"leem@test.com",
//      "registeredDate":"1월 8, 2024"
//    },
//    {
//      "no":103,
//      "fullname":"안창호",
//      "email":"ahn@test.com",
//      "registeredDate":"1월 8, 2024"
//    }
//  ],
//  "tasks":[]
//}
