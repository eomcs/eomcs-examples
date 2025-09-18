// 부품 연결 예 : FileOutputStream --> BufferedOutputStream --> DataOutputStream
package com.eomcs.io.ex09.step3;

import java.io.FileOutputStream;

public class Exam0410 {

  public static void main(String[] args) throws Exception {

    FileOutputStream fileOut = new FileOutputStream("temp/test7.data");

    // 데코레이터 설계 기법의 장점은
    // 다음과 같이 필요한 기능을 쉽게 삽입할 수 있다는 것이다.
    BufferedOutputStream bufOut = new BufferedOutputStream(fileOut);

    DataOutputStream out = new DataOutputStream(bufOut);

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

    out.flush();

    long end = System.currentTimeMillis();

    System.out.println(end - start);

    out.close();

    System.out.println("데이터 출력 완료!");

  }

}
