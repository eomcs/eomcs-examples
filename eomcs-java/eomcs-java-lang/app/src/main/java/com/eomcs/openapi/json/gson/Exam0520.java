// JSON 문자열 --> 객체 : 다른 객체를 여러 개 포함하는 경우
package com.eomcs.openapi.json.gson;

import com.google.gson.Gson;

public class Exam0520 {
  public static void main(String[] args) {

    String jsonStr = "{\"no\":11,\"title\":\"제목\",\"content\":\"내용\",\"startDate\":\"1월 1, 2021\",\"endDate\":\"2월 2, 2021\",\"owner\":{\"no\":102,\"name\":\"임꺽정\",\"email\":\"leem@test.com\",\"registeredDate\":\"9월 16, 2021\"},\"members\":[{\"no\":101,\"name\":\"홍길동\",\"email\":\"hong@test.com\",\"registeredDate\":\"9월 16, 2021\"},{\"no\":102,\"name\":\"임꺽정\",\"email\":\"leem@test.com\",\"registeredDate\":\"9월 16, 2021\"},{\"no\":103,\"name\":\"안창호\",\"email\":\"ahn@test.com\",\"registeredDate\":\"9월 16, 2021\"}],\"tasks\":[]}";

    Project p = new Gson().fromJson(jsonStr, Project.class);

    System.out.println(p);
  }
}





