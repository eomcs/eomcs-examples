// 객체 --> JSON 문자열 : 다양한 타입의 객체를 목록에 포함하는 경우
package com.eomcs.openapi.json.gson;

import java.sql.Date;
import java.util.ArrayList;
import com.google.gson.Gson;

public class Exam0610 {
  public static void main(String[] args) {

    // 1) 객체 준비
    Manager mgr = new Manager();
    mgr.setNo(101);
    mgr.setName("홍길동");
    mgr.setEmail("hong@test.com");
    mgr.setRegisteredDate(new Date(System.currentTimeMillis()));
    mgr.setPosition("대리");
    mgr.setFax("02-1111-2222");

    Teacher teacher = new Teacher();
    teacher.setNo(103);
    teacher.setName("안창호");
    teacher.setEmail("ahn@test.com");
    teacher.setRegisteredDate(new Date(System.currentTimeMillis()));
    teacher.setMajor("컴퓨터공학");
    teacher.setHourPay(10000);

    ArrayList<Member> members = new ArrayList<>();
    members.add(mgr);
    members.add(teacher);

    String jsonStr = new Gson().toJson(members);

    System.out.println(jsonStr);
  }
}

//// 예)
//[
//  {"position":"대리","fax":"02-1111-2222","no":101,"fullname":"홍길동","email":"hong@test.com","registeredDate":"1월 8, 2024"},
//  {"major":"컴퓨터공학","hourPay":10000,"no":103,"fullname":"안창호","email":"ahn@test.com","registeredDate":"1월 8, 2024"}
//]

