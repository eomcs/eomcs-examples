// 객체 --> JSON 문자열 : Date 값을 yyyy-MM-dd 형식으로 출력하기
package com.eomcs.openapi.json.jackson;

import java.sql.Date;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Exam0310 {
  public static void main(String[] args) throws Exception {

    Project project = new Project();
    project.setNo(100);
    project.setTitle("자바 최종 프로젝트");
    project.setContent("자바 웹 애플리케이션");
    project.setStartDate(Date.valueOf("2024-4-1"));
    project.setEndDate(Date.valueOf("2024-5-8"));

    ObjectMapper mapper = new ObjectMapper();
    String jsonStr = mapper.writeValueAsString(project);

    System.out.println(jsonStr);
  }
}


