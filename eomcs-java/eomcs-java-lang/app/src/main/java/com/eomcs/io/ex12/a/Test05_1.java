// 인스턴스 출력하기 - ObjectOutputStream 데코레이터 사용하기 II
package com.eomcs.io.ex12.a;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Test05_1 {

  public static void main(String[] args) throws Exception {

    ArrayList<Score> students = new ArrayList<>();
    students.add(new Score("홍길동", 100, 100, 100));
    students.add(new Score("임꺽정", 90, 90, 90));
    students.add(new Score("유관순", 80, 80, 80));

    FileOutputStream out0 = new FileOutputStream("temp/score.data");
    BufferedOutputStream out1 = new BufferedOutputStream(out0);
    ObjectOutputStream out = new ObjectOutputStream(out1);

    // 컬렉션 객체 통째로 출력하기
    out.writeObject(students);

    out.flush();

    out.close();

    System.out.println("출력 완료!");
  }

}






