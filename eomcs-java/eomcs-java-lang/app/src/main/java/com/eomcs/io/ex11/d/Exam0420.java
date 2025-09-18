// 인스턴스 입출력 - serialVersionUID의 사용법
package com.eomcs.io.ex11.d;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Exam0420 {

  public static void main(String[] args) throws Exception {
    FileInputStream fileIn = new FileInputStream("temp/member4.data");
    ObjectInputStream in = new ObjectInputStream(fileIn);

    // 테스트1:
    // - Member 클래스를 변경하지 않은 상태에서 데이터 읽기
    // - 결과 => OK!
    // 테스트2:
    // - Member 클래스에 tel 필드를 추가한 후 데이터 읽기
    // - 결과 => OK!
    // 테스트3:
    // - Member 클래스에서 age 필드를 제거한 후 데이터 읽기
    // - 결과 => OK!
    Member member = (Member) in.readObject();

    in.close();

    System.out.println(member);

    // 결론!
    // - Member 객체를 serialize 한 후,
    //   필드를 추가하거나 삭제하더라도
    //   serialVersionUID 값만 같으면
    //   JVM은 같은 형식으로 판단한다.
  }
}

