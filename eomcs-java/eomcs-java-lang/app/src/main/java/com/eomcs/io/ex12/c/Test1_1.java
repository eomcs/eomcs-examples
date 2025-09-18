// Serialize - 인스턴스 필드의 값 출력 : getter/setter 없는 경우
package com.eomcs.io.ex12.c;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Test1_1 {

  public static void main(String[] args) throws Exception {
    FileOutputStream out0 = new FileOutputStream("temp/score.data");
    ObjectOutputStream out = new ObjectOutputStream(out0);

    Score1 score = new Score1();
    score.name = "홍길동";
    score.kor = 100;
    score.eng = 100;
    score.math = 100;
    score.sum = score.kor + score.eng + score.math;
    score.aver = score.sum / 3f;

    // 인스턴스 출력하기
    out.writeObject(score);

    System.out.println("출력 완료!");

    // getter/setter 없어도 필드 값을 출력하는데 문제 없다.

    out.close();

  }

}
