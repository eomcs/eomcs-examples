// 포함 관계로 기능 확장하기 - 현재 구조의 문제점
package com.eomcs.io.ex08;

import java.io.FileInputStream;

public class Exam0420 {

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
    // - 그러나 안타깝게도 이런 식으로 기능을 확장할 수 없다.
    //   왜?
    //   - DataInputStream 생성자에는 InputStream 객체만 넘겨줄 수 있다.
    //   - 즉 DataInputStream은 InputStream 객체에만 연결할 수 있다.
    //   - BufferedInputStream은 InputStream 의 자식이 아니기 때문에
    //     DataInputStream에 연결할 수 없다.
    DataInputStream in3 = new DataInputStream(in2); // 컴파일 오류!

    // 만약 위의 코드처럼 DataInputStream을 BufferedInputStream에 연결할 수 있다면,
    // 회원 정보를 읽을 때 버퍼를 사용하기 때문에 속도가 빠를텐데...
    // 안타깝게도 현재의 클래스 구조로는 불가능하다!
    //
    long start = System.currentTimeMillis();

    for (int i = 0; i < 1000000; i++) {
      Member member = new Member();
      member.name = in3.readUTF();
      member.age = in3.readInt();
      member.gender = in3.readBoolean();
    }

    long end = System.currentTimeMillis();

    in3.close();

    System.out.println("읽기 완료!");
    System.out.printf("경과된 시간: %d\n", end - start);

    // 해결책?
    // => 이렇게 상속이 아닌 포함 관계를 통해 기능을 확장하는 방식인 경우
    //    Decorator 패턴으로 구조화시키면
    //    좀 더 기능 확장이 쉬워진다.
    // => ex09 예제를 확인하라!

  }

}
