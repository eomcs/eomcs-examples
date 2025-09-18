// JSON 문자열 --> 객체 : 컬렉션 다루기
package com.eomcs.openapi.json.gson;

import java.util.Collection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Exam0321 {
  public static void main(String[] args) {

    String jsonStr = "[{\"no\":101,\"name\":\"홍길동\"},{\"no\":102,\"name\":\"임꺽정\"},{\"no\":103,\"name\":\"안창호\"}]";

    // Exam0320의 코드를 익명 클래스를 이용하여 간결하게 정리한 것이다.

    //    Type collectionType = new TypeToken<Collection<Member>>(){}.getType();
    //    Collection<Member> list = new Gson().fromJson(jsonStr, collectionType);

    Collection<Member> list = new Gson().fromJson(jsonStr, new TypeToken<Collection<Member>>(){}.getType());

    for (Member m : list) {
      System.out.println(m);
    }
  }
}





