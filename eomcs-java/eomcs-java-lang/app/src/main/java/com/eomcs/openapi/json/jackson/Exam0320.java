// 객체 --> JSON 문자열 : Date 값을 yyyy-MM-dd 형식으로 출력하기
package com.eomcs.openapi.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Exam0320 {
  public static void main(String[] args) throws Exception {

    String jsonStr = "{\"no\":100,\"title\":\"자바 최종 프로젝트\",\"content\":\"자바 웹 애플리케이션\",\"startDate\":\"2024-04-01\",\"endDate\":\"2024-05-08\",\"owner\":null,\"members\":[],\"tasks\":[],\"memberNames\":\"\"}";

    ObjectMapper mapper = new ObjectMapper();
    Project project = mapper.readValue(jsonStr, Project.class);

    System.out.println(project);
  }
}


