// 인스턴스 입출력 - 직렬화 유효성 검사
package com.eomcs.io.ex11.c;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Exam0320 {

  public static void main(String[] args) throws Exception {
    FileInputStream fileIn = new FileInputStream("temp/member3.data");
    ObjectInputStream in = new ObjectInputStream(fileIn);


    Member member = (Member) in.readObject();

    in.close();

    System.out.println(member);

    // 테스트1:
    // - Exam0310 에서 Member 객체를 출력한다.
    // - Exam0320 에서 Member 객체를 읽는다.
    // - 결과
    //   정상적으로 읽을 수 있다.
    //
    // 테스트2:
    // - Exam0310 에서 Member 객체를 출력한다.
    // - Member 클래스에 인스턴스 필드 'tel'을 추가한다.
    // - toString() 도 변경한다.
    // - Exam0320 에서 Member 객체를 읽는다.
    // - 결과
    //   InvalidClassException 실행 오류가 발생한다.
    // - 이유?
    //   deserialize 할 때,
    //   즉 readObject()를 통해 바이트 배열을 읽어 객체를 생성할 때,
    //   같은 클래스인지 검사한다.
    //   만약 다른 클래스라면 InvalidClassException 오류가 발생한다.
    //
    // serialize 할 때 사용할 클래스와 deserialize 할 때 사용할 클래스가
    // 같은지 검사하는 방법?
    // (serialize 할 때 사용한 Member 클래스와
    // deserialize 할 때 사용할 Member 클래스가 동일한 클래스인지 검사하는 방법)
    // - 클래스에 내장된 스태틱 변수인 serialVersionUID 의 값을 비교한다.
    //
    // serialVersionUID 스태틱 변수?
    // - java.io.Serializable 이 붙은 클래스는 항상 이 변수를 내장한다.
    // - 개발자가 이 변수의 값을 명시적으로 지정하지 않으면
    //   컴파일러가 자동 부여한다.
    // - 컴파일러는 클래스가 다르면 이 변수의 값도 다르게 한다.
    //
    // 결론!
    // - 처음에 Member 클래스를 작성하였다.
    // - 컴파일러는 이 클래스의 serialVersionUID 변수에 대해 임의의 값을 부여한다.
    // - Exam0310에서 Member 객체를 출력할 때 이 변수의 값도 출력하였다.
    // - Member 클래스를 변경한다.
    // - 컴파일러 또한 이 클래스의 serialVersionUID 변수의 값을 변경한다.
    // - Exam0320에서 바이트 배열을 읽어서 Member 객체를 생성하려 하였다.
    // - 그러나 파일에 저장된 Member 클래스의 serialVersionUID의 값과
    //   실제 객체를 생성하기 위해 사용할 Member 클래스의 serialVersionUID의 값이 다르다.
    // - JVM은 저장할 때 형식과 읽을 때 메모리 형식이 다르다고 판단하여
    //   오류를 띄운다.
    //
  }

}

