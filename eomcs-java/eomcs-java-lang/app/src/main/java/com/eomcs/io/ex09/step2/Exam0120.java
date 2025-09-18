// 포함 관계로 기능 확장하기 - FileInputStream + DataInputStream + 대량 데이터 읽기
package com.eomcs.io.ex09.step2;

import java.io.FileInputStream;

public class Exam0120 {

  public static void main(String[] args) throws Exception {

    FileInputStream in0 = new FileInputStream("temp/members.data");
    BufferedInputStream in1 = new BufferedInputStream(in0);
    DataInputStream in2 = new DataInputStream(in1);

    Member member = null;

    long start = System.currentTimeMillis();

    for (int i = 0; i < 1000000; i++) {
      member = new Member();
      member.name = in2.readUTF();
      member.age = in2.readInt();
      member.gender = in2.readBoolean();
    }

    long end = System.currentTimeMillis();

    in2.close();

    System.out.println("읽기 완료!");
    System.out.printf("경과된 시간: %d\n", end - start);
  }
}


