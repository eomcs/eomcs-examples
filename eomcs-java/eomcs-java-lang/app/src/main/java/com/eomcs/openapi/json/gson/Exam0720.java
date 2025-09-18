// JSON 문자열 --> 객체 : 다른 객체를 여러 개 포함하는 경우
package com.eomcs.openapi.json.gson;

import java.util.Map;
import com.google.gson.Gson;

public class Exam0720 {
  public static void main(String[] args) {

    String jsonStr = "{\"no\":100,\"manager\":{\"position\":\"대리\",\"fax\":\"02-1111-2222\",\"no\":101,\"name\":\"홍길동\",\"email\":\"hong@test.com\",\"registeredDate\":\"10월 5, 2021\"},\"title\":\"Hello\"}";

    Map<String,Object> map = new Gson().fromJson(jsonStr, Map.class);

    Object v1 = map.get("no");
    Object v2 = map.get("title");
    Object v3 = map.get("manager");

    System.out.println(v1.getClass().getName());
    System.out.println(v2.getClass().getName());
    System.out.println(v3.getClass().getName());

    System.out.println(map.get("no"));
    System.out.println(map.get("title"));
    System.out.println(map.get("manager"));
  }
}





