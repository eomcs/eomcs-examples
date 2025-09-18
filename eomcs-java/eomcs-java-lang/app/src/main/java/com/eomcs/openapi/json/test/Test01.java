package com.eomcs.openapi.json.test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Test01 {

  public static void main(String[] args) throws Exception {
    String jsonStr = "{\"name\":\"소보루\",\"maker\":\"안데르센\"}";

    ObjectMapper mapper = new ObjectMapper();

    Bread obj = mapper.readValue(jsonStr, Bread.class);

    System.out.println(obj);

  }

}
