// JSON 문자열 --> 객체 : JSON 문자열을 해석하여 객체를 생성하기
package com.eomcs.openapi.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Exam0120 {
  public static void main(String[] args) throws Exception {

    // 1) JSON 문자열 준비
    String jsonStr = "{\"no\":100,\"name\":\"홍길동\",\"email\":\"hong@test.com\",\"password\":\"1111\",\"photo\":\"hong.gif\",\"tel\":\"010-2222-1111\",\"registeredDate\":1642991105179}";

    // 2) JSON 처리 객체 준비
    ObjectMapper mapper = new ObjectMapper();

    // 3) JSON 문자열을 가지고 객체 만들기
    Member m = mapper.readValue(jsonStr, Member.class);

    System.out.println(m);
  }
}





