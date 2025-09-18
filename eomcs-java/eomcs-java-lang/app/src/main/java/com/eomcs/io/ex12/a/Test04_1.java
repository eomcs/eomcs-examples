// 인스턴스 출력하기 - ObjectOutputStream 데코레이터 사용하기
package com.eomcs.io.ex12.a;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Test04_1 {

  public static void main(String[] args) throws Exception {

    ArrayList<Score> students = new ArrayList<>();
    students.add(new Score("홍길동", 100, 100, 100));
    students.add(new Score("임꺽정", 90, 90, 90));
    students.add(new Score("유관순", 80, 80, 80));

    // ObjectOutputStream
    // => DataOutputStream의 기능을 포함한다.
    // => 인스턴스를 바이트 배열로 만들어 출력하는 기능이 있다.
    // => 단 java.io.Serializable 인터페이스를 구현한 클래스에 대해서만 가능하다.
    //
    FileOutputStream out0 = new FileOutputStream("temp/score.data");
    BufferedOutputStream out1 = new BufferedOutputStream(out0);
    ObjectOutputStream out = new ObjectOutputStream(out1);

    out.writeInt(students.size());

    for (Score s : students) {
      out.writeObject(s);
    }

    out.flush();

    out.close();

    System.out.println("출력 완료!");
  }

}






