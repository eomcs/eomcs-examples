// JSON 문자열 --> 객체 : 다른 객체를 여러 개 포함하는 경우
package com.eomcs.openapi.json.gson;

import com.google.gson.Gson;

public class Exam0722 {
  public static void main(String[] args) {

    String jsonStr = "{\"no\":100,\"title\":\"Hello\",\"manager\":{\"position\":\"대리\",\"fax\":\"02-1111-2222\",\"no\":101,\"name\":\"홍길동\",\"email\":\"hong@test.com\",\"registeredDate\":\"10월 5, 2021\"}}";

    ManagerParam param = new Gson().fromJson(jsonStr, ManagerParam.class);

    System.out.println(param.no);
    System.out.println(param.title);
    System.out.println(param.manager);
  }
}





