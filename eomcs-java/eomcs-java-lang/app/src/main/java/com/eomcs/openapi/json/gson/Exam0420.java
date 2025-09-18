// JSON 문자열 --> 객체 : 다른 객체를 포함하는 경우
package com.eomcs.openapi.json.gson;

import com.google.gson.Gson;

public class Exam0420 {
  public static void main(String[] args) {

    String jsonStr = "{\"no\":1,\"title\":\"제목\",\"content\":\"내용\",\"writer\":{\"no\":100,\"name\":\"홍길동\",\"email\":\"hong@test.com\",\"password\":\"1111\",\"photo\":\"hong.gif\",\"tel\":\"010-2222-1111\",\"registeredDate\":\"9월 16, 2021\"},\"registeredDate\":\"9월 16, 2021\",\"viewCount\":98,\"like\":5}";

    Board b = new Gson().fromJson(jsonStr, Board.class);

    System.out.println(b);
  }
}





