// Serialize - private 인스턴스 필드의 값 출력
package com.eomcs.io.ex12.c;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Test2_1 {

  public static void main(String[] args) throws Exception {
    FileOutputStream out0 = new FileOutputStream("temp/score.data");
    ObjectOutputStream out = new ObjectOutputStream(out0);

    Score2 score = new Score2();
    score.setName("홍길동");
    score.setKor(100);
    score.setEng(100);
    score.setMath(100);
    score.setSum(score.getKor() + score.getEng() + score.getMath());
    score.setAver(score.getSum() / 3f);

    // 인스턴스 출력하기
    // => 필드가 접근 범위가 private이라도 상관없이 serialize 된다.
    // => serialize는 setter/getter의 존재여부와 상관없다.
    out.writeObject(score);

    System.out.println("출력 완료!");

    out.close();

  }

}
