// Java I/O API 사용하기 - BufferedOutputStream
package com.eomcs.io.ex10;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class Exam0120 {

  public static void main(String[] args) throws Exception {
    FileOutputStream fileOut = new FileOutputStream("temp/members.data");
    BufferedOutputStream bufOut = new BufferedOutputStream(fileOut);
    DataOutputStream out = new DataOutputStream(bufOut);

    Member member = new Member();
    member.name = "AB가각간";
    member.age = 27;
    member.gender = true;

    // 장신구/보조장치/플러그인(decorator)를 사용하여 String,int,boolean 값을 출력한다.
    long startTime = System.currentTimeMillis();

    for (int i = 0; i < 100000; i++) {
      out.writeUTF(member.name);
      out.writeInt(member.age);
      out.writeBoolean(member.gender);
    }

    long endTime = System.currentTimeMillis();

    out.close();

    System.out.println("데이터 출력 완료!");
    System.out.println(endTime - startTime);
  }

}
