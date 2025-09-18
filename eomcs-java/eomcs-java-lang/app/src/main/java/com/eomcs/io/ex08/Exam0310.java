// 포함 관계로 기능 확장하기 - FileOutputStream + DataOutputStream + 대량 데이터 출력
package com.eomcs.io.ex08;

import java.io.FileOutputStream;

public class Exam0310 {

  public static void main(String[] args) throws Exception {

    FileOutputStream out1 = new FileOutputStream("temp/members.data");
    DataOutputStream out2 = new DataOutputStream(out1);

    Member member = new Member();
    member.name = "AB가각간";
    member.age = 27;
    member.gender = true;

    long start = System.currentTimeMillis();

    for (int i = 0; i < 1000000; i++) {
      out2.writeUTF(member.name);
      out2.writeInt(member.age);
      out2.writeBoolean(member.gender);
    }

    long end = System.currentTimeMillis();

    out2.close();

    System.out.println("출력 완료!");
    System.out.printf("경과된 시간: %d\n", end - start);
  }

}
