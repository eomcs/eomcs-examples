// 맵객체 --> JSON 문자열 
package com.eomcs.openapi.json.gson;

import java.sql.Date;
import com.google.gson.Gson;

public class Exam0712 {
  public static void main(String[] args) {

    // 자바 기본 타입과 객체를 묶어서 JSON으로 내보내기
    Manager mgr = new Manager();
    mgr.setNo(101);
    mgr.setName("홍길동");
    mgr.setEmail("hong@test.com");
    mgr.setRegisteredDate(new Date(System.currentTimeMillis()));
    mgr.setPosition("대리");
    mgr.setFax("02-1111-2222");

    ManagerParam param = new ManagerParam();
    param.no = 100;
    param.title = "Hello";
    param.manager = mgr;

    String jsonStr = new Gson().toJson(param);

    System.out.println(jsonStr);
  }
}

