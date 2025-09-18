// 부품 연결 예 : FileOutputStream --> DataOutputStream
package com.eomcs.io.ex09.step3;

import java.io.FileOutputStream;

public class Exam0310 {

  public static void main(String[] args) throws Exception {

    FileOutputStream fileOut = new FileOutputStream("temp/test7.data");
    DataOutputStream out = new DataOutputStream(fileOut);

    Member member = new Member();
    member.name = "AB가각간";
    member.age = 27;
    member.gender = true;

    long start = System.currentTimeMillis();

    for (int i = 0; i < 1000000; i++) {
      out.writeUTF(member.name);
      out.writeInt(member.age);
      out.writeBoolean(member.gender);
    }

    long end = System.currentTimeMillis();

    System.out.println(end - start);

    out.close();

    System.out.println("데이터 출력 완료!");

  }

}
