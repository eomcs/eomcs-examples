// Serialize - 인스턴스 필드의 값 읽기 : getter/setter 없는 경우
package com.eomcs.io.ex12.c;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Test1_2 {
  public static void main(String[] args) throws Exception {

    FileInputStream in0 = new FileInputStream("temp/score.data");
    ObjectInputStream in = new ObjectInputStream(in0);

    // readObject()가 Score1 객체를 만들 때 생성자를 호출하지 않는다.
    // 그냥 인스턴스 필드를 만들고 파일에서 읽은 값을 바로 설정한다.
    Score1 score = (Score1) in.readObject();
    System.out.println(score);

    // getter/setter 없어도 필드 값을 읽는데 문제 없다.

    in.close();
  }
}
