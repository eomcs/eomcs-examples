// Serialize - private 인스턴스 필드의 값을 읽어서 채우기 : getter/setter 있는 경우
package com.eomcs.io.ex12.c;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Test2_2 {
  public static void main(String[] args) throws Exception {

    FileInputStream in0 = new FileInputStream("temp/score.data");
    ObjectInputStream in = new ObjectInputStream(in0);

    // deserialize 할 때 
    // private 접근 제어에 상관없이 파일에서 읽은 값을 필드에 바로 저장한다.
    // 생성자나 setter를 호출하지 않는다.
    // 인스턴스를 생성한 후 직접 인스턴스의 필드 값을 저장한다.
    Score2 score = (Score2) in.readObject();
    System.out.println(score);

    in.close();
  }
}
