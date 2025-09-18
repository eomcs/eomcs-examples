// JSON 문자열 --> 객체 : 다른 객체를 여러 개 포함하는 경우
package com.eomcs.openapi.json.gson;

import java.util.Map;
import com.google.gson.Gson;

public class Exam0721 {
  public static void main(String[] args) {

    String jsonStr = "{\"no\":\"100\",\"manager\":\"{\\\"position\\\":\\\"대리\\\",\\\"fax\\\":\\\"02-1111-2222\\\",\\\"no\\\":101,\\\"name\\\":\\\"홍길동\\\",\\\"email\\\":\\\"hong@test.com\\\",\\\"registeredDate\\\":\\\"10월 5, 2021\\\"}\",\"title\":\"Hello\"}";

    Map<String,String> map = new Gson().fromJson(jsonStr, Map.class);

    String v1 = map.get("no");
    String v2 = map.get("title");
    String v3 = map.get("manager");

    System.out.println(v1);
    System.out.println(v2);
    System.out.println(v3);

    Manager mgr = new Gson().fromJson(v3, Manager.class);
    System.out.println(mgr);
  }
}





