// 포함 관계로 기능 확장하기 - 현재 구조의 문제점
package com.eomcs.io.ex08;

import java.io.FileOutputStream;

public class Exam0410 {

  public static void main(String[] args) throws Exception {

    // FileOutputStream
    // - 파일 저장소에서 데이터를 출력하는 일을 한다.
    FileOutputStream out1 = new FileOutputStream("temp/members.data");

    // FileOutputStream + BufferedOutputStream
    // - 버퍼로 출력한 다음에 버퍼가 모두 차면 한번에 출력한다.
    // - 출력 속도를 높이는 일을 한다.
    BufferedOutputStream out2 = new BufferedOutputStream(out1);

    // FileOutputStream + BufferedOutputStream + DataOutputStream
    // - 문자열이나 자바 기본 타입의 데이터를 좀 더 쉽게 출력하기
    // - 그러나 안타깝게도 이런 식으로 기능을 확장할 수 없다.
    //   왜?
    //   - DataOutputStream 생성자에는 InputStream 객체만 넘겨줄 수 있다.
    //   - 즉 DataOutputStream은 OutputStream 객체에만 연결할 수 있다.
    //   - BufferedOutputStream은 OutputStream 의 자식이 아니기 때문에
    //     DataOutputStream에 연결할 수 없다.
    DataOutputStream out3 = new DataOutputStream(out2); // 컴파일 오류!

    Member member = new Member();
    member.name = "AB가각간";
    member.age = 27;
    member.gender = true;

    long start = System.currentTimeMillis();

    for (int i = 0; i < 1000000; i++) {
      out3.writeUTF(member.name);
      out3.writeInt(member.age);
      out3.writeBoolean(member.gender);
    }

    long end = System.currentTimeMillis();

    out3.close();

    System.out.println("출력 완료!");
    System.out.printf("경과된 시간: %d\n", end - start);

    // 해결책?
    // => 이렇게 상속이 아닌 포함 관계를 통해 기능을 확장하는 방식인 경우
    //    Decorator 패턴으로 구조화시키면
    //    좀 더 기능 확장이 쉬워진다.
    // => ex09 예제를 확인하라!
  }

}
