// DataFileInputStream으로 객체 읽기 - 파일이 데이터를 읽어 인스턴스로 만들기 
package com.eomcs.io.ex05;

public class Exam0220 {

  public static void main(String[] args) throws Exception {
    DataFileInputStream in = new DataFileInputStream("temp/test4_2.data");

    Member member = null;

    member = new Member();

    // 1) 이름 읽기
    member.name = in.readUTF();

    // 2) 나이(int) 읽기
    member.age = in.readInt();

    // 3) 성별 읽기
    member.gender = in.readBoolean();

    in.close();

    System.out.printf("%s\n", member);
  }
}






