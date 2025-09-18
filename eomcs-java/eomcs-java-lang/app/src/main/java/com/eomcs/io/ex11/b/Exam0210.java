// 인스턴스 입출력 - ObjectOutputStream으로 인스턴스 출력하기
package com.eomcs.io.ex11.b;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Exam0210 {

  public static void main(String[] args) throws Exception {
    // ObjectOutputStream
    // => DataOutputStream의 기능을 포함한다.
    // => 인스턴스를 바이트 배열로 만들어 출력하는 기능(writeObject())이 있다.
    // => 단 java.io.Serializable 인터페이스를 구현한 클래스에 대해서만 가능하다.
    //
    FileOutputStream fileOut = new FileOutputStream("temp/member2.data");
    ObjectOutputStream out = new ObjectOutputStream(fileOut);

    Member member = new Member();
    member.name = "AB가각간";
    member.age = 27;
    member.gender = true;

    // ObjectOutputStream에는 인스턴스의 값을 바이트 배열로 만들어 출력하는 기능이 있다.
    //
    // writeObject()
    // - java.io.Serializable 이 선언된 클래스의 객체만 출력할 수 있다.
    // - 인스턴스의 필드 값을 바이트 배열로 만들어(직렬화) 출력한다.
    // - 인스턴스의 필드의 값만 출력하는 것이 아니다.
    // - 클래스 이름과 인스턴 변수의 정보도 함께 출력한다.
    //       출력데이터 = 클래스이름 + 인스턴스 변수 정보 + 인스턴스 변수 값
    //
    out.writeObject(member);
    // 그러나 실행하면 오류가 발생한다.
    // => java.io.NotSerializableException
    // => 인스턴스의 값을 자동으로 바이트 배열로 만들 수 있도록 허락되지 않아서
    //    발생한 실행 오류이다.
    //
    // Member 클래스가 java.io.Serializable을 구현하면
    // 바이트 배열로 자동 변환하는 것이 가능하다.
    // writeObject()를 호출하여 인스턴스의 값을 출력할 수 있다.
    //

    out.close();
    System.out.println("데이터 출력 완료!");
  }

}


//용어 정리!
//1) Serialize(직렬화)
// - 객체(인스턴스) ===> 바이트 배열
// - marshalling 이라고도 부른다.
//
//2) Deserialize(객체 복원)
// - 바이트 배열 ===> 객체
// - unmarshalling 이라고도 부른다.
//

