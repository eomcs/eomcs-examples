// 인스턴스 읽기 - ObjectInputStream II
package com.eomcs.io.ex12.a;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Test05_2 {

  public static void main(String[] args) throws Exception {

    FileInputStream in0 = new FileInputStream("temp/score.data");
    BufferedInputStream in1 = new BufferedInputStream(in0);
    ObjectInputStream in = new ObjectInputStream(in1);

    // 컬렉션 객체 통째로 읽기
    @SuppressWarnings("unchecked")
    ArrayList<Score> students = (ArrayList<Score>) in.readObject();

    in.close();

    for (Score s : students) {
      System.out.printf("%s, %d, %d, %d, %d, %.1f\n",
          s.getName(), s.getKor(), s.getEng(), s.getMath(),
          s.getSum(), s.getAver());
    }
  }

}
