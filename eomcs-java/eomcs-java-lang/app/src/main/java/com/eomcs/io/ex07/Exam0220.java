// DataBufferedFileInputStream으로 객체 읽기
package com.eomcs.io.ex07;

public class Exam0220 {

  public static void main(String[] args) throws Exception {
    DataBufferedFileInputStream in = new DataBufferedFileInputStream("temp/members.data");

    Member member = null;

    long start = System.currentTimeMillis();

    for (int i = 0; i < 1000000; i++) {
      member = new Member();
      member.name = in.readUTF();
      member.age = in.readInt();
      member.gender = in.readBoolean();
    }

    long end = System.currentTimeMillis();

    in.close();

    System.out.println("읽기 완료!");
    System.out.printf("경과된 시간: %d\n", end - start);
  }
}






