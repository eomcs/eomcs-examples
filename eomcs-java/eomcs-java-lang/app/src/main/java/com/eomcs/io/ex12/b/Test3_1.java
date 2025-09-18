// 인스턴스 출력 - serialVersionUID 스태틱 필드 사용하기
package com.eomcs.io.ex12.b;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Test3_1 {

  public static void main(String[] args) throws Exception {
    FileOutputStream out0 = new FileOutputStream("temp/score.data");
    ObjectOutputStream out = new ObjectOutputStream(out0);

    // 인스턴스 출력하기

    // Score3 클래스는 java.io.Serializable을 구현하였다.
    // serialVersionUID의 값은 100으로 설정하였다.
    // => Score3 클래스에 tel 필드와 getter/setter 가 없는 상태에서 
    //    이 클래스를 실행하라!
    out.writeObject(new Score3("홍길동", 100, 100, 100));

    System.out.println("출력 완료!");

    out.close();
  }

}
