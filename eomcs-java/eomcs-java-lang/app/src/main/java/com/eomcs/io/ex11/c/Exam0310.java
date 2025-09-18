// 인스턴스 입출력 - 직렬화 유효성 검사
package com.eomcs.io.ex11.c;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Exam0310 {

  public static void main(String[] args) throws Exception {
    FileOutputStream fileOut = new FileOutputStream("temp/member3.data");
    ObjectOutputStream out = new ObjectOutputStream(fileOut);

    Member member = new Member();
    member.name = "AB가각간";
    member.age = 27;
    member.gender = true;

    out.writeObject(member);

    out.close();

    System.out.println("출력 완료!");
  }

}


