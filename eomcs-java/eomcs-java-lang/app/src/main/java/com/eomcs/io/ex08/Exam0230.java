// 포함 관계로 기능 확장하기 - ByteArrayInputStream + DataInputStream
package com.eomcs.io.ex08;

import java.io.ByteArrayInputStream;

public class Exam0230 {

  public static void main(String[] args) throws Exception {
    byte[] buf = {0x0b, 0x41, 0x42, (byte) 0xea, (byte) 0xb0, (byte) 0x80, (byte) 0xea, (byte) 0xb0,
        (byte) 0x81, (byte) 0xea, (byte) 0xb0, (byte) 0x84, 0x00, 0x00, 0x00, 0x1b, 0x01};

    // 바이트 배열을 읽는 것은 ByteArrayInputStream에 맡긴다.
    ByteArrayInputStream in1 = new ByteArrayInputStream(buf);

    // 문자열, int, long, boolean 값을 읽는 것은 DataInputStream 에 맡긴다.
    // =>ByteArrayInputStream에  DataInputStream 기능을 덧붙인다.
    DataInputStream in2 = new DataInputStream(in1);

    Member member = new Member();

    member.name = in2.readUTF();
    member.age = in2.readInt();
    member.gender = in2.readBoolean();

    in2.close();

    System.out.println(member);

    // DataInputStream 은 실제 데이터가 저장된 장소로부터 데이터를 읽는 일을 하지 않는다.
    // 단지 읽어 온 데이터를 중간에서 가공 처리하여 리턴하는 일을 한다.
    // 이런 류의 클래스를
    // "데이터 처리 프로세싱 스트림 클래스(data processsing stream class)" 라 부른다.
    //
    // 상속 관계를 포함 관계로 변경했을 때 이점
    // => 코드가 중복되지 않는다.
    // => 장식품처럼 여러 스트림 클래스에 붙여서 사용할 수 있다.
    // => 즉 재사용성이 높고 유지보수가 쉬워진다.

  }

}
