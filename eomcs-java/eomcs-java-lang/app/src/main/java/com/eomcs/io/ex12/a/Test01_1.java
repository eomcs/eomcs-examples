// 인스턴스 출력하기
package com.eomcs.io.ex12.a;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class Test01_1 {

  public static void main(String[] args) throws Exception {

    // 다음 세 학생의 성적 정보를 score.data 파일에 바이너리 형식으로 저장하라!
    // => java.io.BufferedOutputStream 클래스를 사용하라.
    // => java.io.DataOutputStream 클래스를 사용하라.
    //
    Score s1 = new Score("홍길동", 100, 100, 100);
    Score s2 = new Score("임꺽정", 90, 90, 90);
    Score s3 = new Score("유관순", 80, 80, 80);

    FileOutputStream out0 = new FileOutputStream("temp/score.data");
    BufferedOutputStream out1 = new BufferedOutputStream(out0);
    DataOutputStream out = new DataOutputStream(out1);

    out.writeUTF(s1.getName());
    out.writeInt(s1.getKor());
    out.writeInt(s1.getEng());
    out.writeInt(s1.getMath());

    out.writeUTF(s2.getName());
    out.writeInt(s2.getKor());
    out.writeInt(s2.getEng());
    out.writeInt(s2.getMath());

    out.writeUTF(s3.getName());
    out.writeInt(s3.getKor());
    out.writeInt(s3.getEng());
    out.writeInt(s3.getMath());

    // 버퍼를 사용할 때 주의하라!
    // => 버퍼는 꽉 찼을 때 자동으로 출력된다.
    // => 따라서 출력을 마무리할 때는 반드시 버퍼에 남아 있는 데이터를 강제로 출력해야 한다.
    out.flush();

    out.close();

    System.out.println("출력 완료!");
  }

}






