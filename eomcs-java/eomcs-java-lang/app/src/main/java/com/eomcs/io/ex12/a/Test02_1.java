// 인스턴스 출력하기 - 배열 활용
package com.eomcs.io.ex12.a;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class Test02_1 {

  public static void main(String[] args) throws Exception {

    // 다음 세 학생의 성적 정보를 score.data 파일에 바이너리 형식으로 저장하라!
    // => java.io.BufferedOutputStream 클래스를 사용하라.
    // => java.io.DataOutputStream 클래스를 사용하라.
    //

    Score[] students = {    
        new Score("홍길동", 100, 100, 100),
        new Score("임꺽정", 90, 90, 90),
        new Score("유관순", 80, 80, 80)
    };

    FileOutputStream out0 = new FileOutputStream("temp/score.data");
    BufferedOutputStream out1 = new BufferedOutputStream(out0);
    DataOutputStream out = new DataOutputStream(out1);

    out.writeInt(students.length);

    for (Score s : students) {
      out.writeUTF(s.getName());
      out.writeInt(s.getKor());
      out.writeInt(s.getEng());
      out.writeInt(s.getMath());
    }

    out.flush(); // 버퍼에 남아 있는 잔여 데이터 추출을 잊지말라!

    out.close();

    System.out.println("출력 완료!");
  }

}






