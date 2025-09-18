package com.eomcs.openapi.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Exam0220 {
  public static void main(String[] args) throws Exception {
    String jsonStr = "{\"no\":100,\"name\":\"홍길동\",\"email\":\"hong@test.com\",\"password\":\"1111\",\"photo\":\"hong.gif\",\"tel\":\"010-2222-1111\",\"registeredDate\":\"2023-07-03\"}";

    ObjectMapper mapper = new ObjectMapper();
    Member member = mapper.readValue(jsonStr, Member.class);

    System.out.println(member);
  }
}
