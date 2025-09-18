// 인스턴스 입출력 - serialVersionUID의 사용법
package com.eomcs.io.ex11.d;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Exam0410 {

  public static void main(String[] args) throws Exception {
    FileOutputStream fileOut = new FileOutputStream("temp/member4.data");
    ObjectOutputStream out = new ObjectOutputStream(fileOut);

    Member member = new Member();
    member.name = "AB가각간";
    member.age = 27;
    member.gender = true;

    // serialize 하는 Member의 버전은 1280 이다.
    out.writeObject(member);

    out.close();

    System.out.println("출력 완료!");
  }

}



