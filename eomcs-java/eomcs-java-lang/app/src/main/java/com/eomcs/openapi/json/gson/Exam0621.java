// JSON 문자열 --> 객체 : 다른 객체를 여러 개 포함하는 경우
package com.eomcs.openapi.json.gson;

import java.lang.reflect.Type;
import java.util.Collection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class Exam0621 {
  public static void main(String[] args) {

    String jsonStr = "[{\"position\":\"대리\",\"fax\":\"02-1111-2222\",\"no\":101,\"name\":\"홍길동\",\"email\":\"hong@test.com\",\"registeredDate\":\"9월 16, 2021\"},{\"major\":\"컴퓨터공학\",\"hourPay\":10000,\"no\":103,\"name\":\"안창호\",\"email\":\"ahn@test.com\",\"registeredDate\":\"9월 16, 2021\"}]";

    // JSON 데이터를 가지고 객체를 생성할 때 특정 타입의 객체로 만들어주는 어댑터
    // => 프로퍼티의 존재 유무에 따라 Manager를 생성하든가 Teacher를 생성하는 일을 한다.
    class MyJsonDeserializer implements JsonDeserializer<Member> {
      @Override
      public Member deserialize(JsonElement json, Type typeOfT,
          JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.get("position") != null) {
          return context.deserialize(jsonObject, Manager.class);
        } else {
          return context.deserialize(jsonObject, Teacher.class);
        }
      }
    }

    // Gson 객체를 만들어주는 공장
    GsonBuilder gsonBuilder = new GsonBuilder();

    // 공장에 객체 생성기(JsonDeserializer)를 꼽는다.
    gsonBuilder.registerTypeAdapter(Member.class, new MyJsonDeserializer());

    // 공장을 통해 Gson 객체를 준비한다.
    Gson gson = gsonBuilder.create();

    // Gson 객체가 JSON 데이터를 가지고 객체를 생성할 때 알아야 하는 객체 타입 정보 준비
    Type collectionType = TypeToken.getParameterized(Collection.class, Member.class).getType();

    // JSON 데이터를 읽어서 주어진 타입의 객체를 생성한다.
    // => 단 내부에 설정된 객체 어댑터(JsonDeserializer)를 이용하여 객체를 생성한다.
    Collection<Member> list = gson.fromJson(jsonStr, collectionType);

    for (Member m : list) {
      System.out.println(m);
    }
  }
}





