// 포함 관계로 기능 확장하기 - FileOutputStream + DataOutputStream
package com.eomcs.io.ex08;

import java.io.FileOutputStream;

public class Exam0210 {

  public static void main(String[] args) throws Exception {

    // FileInputStream
    // - 파일 저장소에서 데이터를 읽는 일을 한다.
    FileOutputStream out1 = new FileOutputStream("temp/member.data");

    // FileOutputStream + DataOutputStream
    // - 문자열이나 자바 기본 타입의 데이터를 좀 더 쉽게 읽기
    // - FileOutputStream 객체에 타입에 맞춰 데이터를 읽는 기능을 덧붙인다.
    DataOutputStream out2 = new DataOutputStream(out1);

    Member member = new Member();
    member.name = "AB가각간";
    member.age = 27;
    member.gender = true;

    out2.writeUTF(member.name);
    out2.writeInt(member.age);
    out2.writeBoolean(member.gender);

    out2.close();

    System.out.println("출력 완료!");
  }

}
