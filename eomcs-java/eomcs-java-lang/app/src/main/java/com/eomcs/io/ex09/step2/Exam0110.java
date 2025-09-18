// 데코레이터 패턴 적용 II - 데코레이터 객체의 공통 기능을 수퍼 클래스로 추출하여 추상 클래스로 정의한다.
package com.eomcs.io.ex09.step2;

import java.io.FileInputStream;

public class Exam0110 {

  public static void main(String[] args) throws Exception {

    // FileInputStream
    // - 파일 저장소에서 데이터를 읽는 일을 한다.
    FileInputStream in = new FileInputStream("temp/test4.data");

    // FileInputStream + BufferedInputStream
    // - 버퍼를 이용하여 일정량의 데이터를 왕창 읽어온 다음에 바이트를 꺼낸다.
    // - 읽기 속도를 높이는 일을 한다.
    BufferedInputStream in2 = new BufferedInputStream(in);

    // FileInputStream + BufferedInputStream + DataInputStream
    // - 문자열이나 자바 기본 타입의 데이터를 좀 더 쉽게 읽기
    // - 이제 BufferedInputStream과 DataInputStream도 
    //   InputStream의 자식이기 때문에 
    //   다른 객체에 연결할 수 있다.
    DataInputStream in3 = new DataInputStream(in2); // OK!

    Member member = new Member();
    member.name = in3.readUTF();
    member.age = in3.readInt();
    member.gender = in3.readBoolean();

    // 객체를 닫을 때는 마지막에 연결된 부품부터 닫는다.
    in3.close();
    in2.close();
    in.close();

    System.out.println(member);
  }

}
