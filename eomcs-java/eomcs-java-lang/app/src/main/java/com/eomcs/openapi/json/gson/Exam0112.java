// 객체 --> JSON 문자열 : 객체의 필드 값을 json 형식의 문자열로 만들기
package com.eomcs.openapi.json.gson;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class Exam0112 {
  public static void main(String[] args) {

    // 1) 객체 준비
    Member m = new Member();
    m.setNo(100);
    m.setName("홍길동");
    m.setEmail("hong@test.com");
    m.setPassword("1111");
    m.setPhoto("hong.gif");
    m.setTel("010-2222-1111");
    m.setRegisteredDate(new Date(System.currentTimeMillis()));
    m.setSchool(new School("학사", "비트대학교"));

    // 2) JSON 처리 객체 준비
    // Date 타입의 값을 내보내고 가져올 때 사용할 변환 도구를 준비
    class GsonDateFormatAdapter implements JsonSerializer<Date> {

      private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

      @Override
      public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        // 객체를 JSON 문자열로 변환할 때 호출된다.
        // 그 중 Date 타입의 프로퍼티 값을 JSON 문자열로 바꿀 때 호출된다.
        System.out.println(src.getTime());
        return new JsonPrimitive(dateFormat.format(src));
      }
    }

    class GsonSchoolFormatAdapter implements JsonSerializer<School> {

      private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

      @Override
      public JsonElement serialize(School src, Type typeOfSrc, JsonSerializationContext context) {
        // 객체를 JSON 문자열로 변환할 때 호출된다.
        // 그 중 School 타입의 프로퍼티 값을 JSON 문자열로 바꿀 때 호출된다.
        return new JsonPrimitive(String.format("%s(%s)", src.level, src.name));
      }
    }

    // Gson 객체를 만들어 줄 도우미 객체
    GsonBuilder builder = new GsonBuilder();

    // Date 타입의 프로퍼티 값을 JSON 형식의 문자열로 바꿔줄 변환기를 등록한다.
    builder.registerTypeAdapter(
        Date.class, // 원래 데이터의 타입
        new GsonDateFormatAdapter() // Date 형식의 데이터를 JSON 문자열로 바꿔줄 변환기
        );

    builder.registerTypeAdapter(
        School.class, // 원래 데이터의 타입
        new GsonSchoolFormatAdapter() // Date 형식의 데이터를 JSON 문자열로 바꿔줄 변환기
        );
    Gson gson = builder.create();

    // 3) 객체의 값을 JSON 문자열로 얻기
    String jsonStr = gson.toJson(m);

    System.out.println(jsonStr);
  }
}

// JSON 객체 형식 - { 객체 정보 }
// => { "프로퍼티명" : 값, "프로퍼티명": 값, ...}
//
// 값:
// - 문자열 => "값"
// - 숫자   => 값
// - 논리   => true, false
//
// 프로퍼티명은 반드시 문자열로 표현해야 한다.




