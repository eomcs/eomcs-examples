// 인스턴스 출력 - Serializable 구현체의 인스턴스를 출력하기
package com.eomcs.io.ex12.b;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Test2_1 {

  public static void main(String[] args) throws Exception {
    FileOutputStream out0 = new FileOutputStream("temp/score.data");
    ObjectOutputStream out = new ObjectOutputStream(out0);

    // 인스턴스 출력하기

    // Score2 클래스는 java.io.Serializable을 구현하였다.
    // 즉 serialize가 허락된 클래스이다.
    // 이제 writeObject()를 사용하여 객체를 serialize 할 수 있다.
    // => Score2 클래스에 tel 필드와 getter/setter 가 없는 상태에서 
    //    이 클래스를 실행하라!
    // 
    out.writeObject(new Score2("홍길동", 100, 100, 100));

    out.close();

    System.out.println("출력 완료!");

  }

}
