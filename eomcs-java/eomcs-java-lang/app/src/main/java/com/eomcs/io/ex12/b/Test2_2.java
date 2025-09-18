// 인스턴스 읽기 - Serializable 구현체의 인스턴스를 읽기
package com.eomcs.io.ex12.b;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Test2_2 {
  public static void main(String[] args) throws Exception {

    // deserialize
    // => 저장할 때 사용한 클래스를 가지고 인스턴스를 만든 후에 
    //    바이트 배열을 읽어 인스턴스 필드에 그대로 저장한다.
    // => 생성자를 호출하지 않는다.
    // => 저장할 때 사용한 클래스가 변경되었다면, 
    //    deserialize 할 때 예외가 발생한다.
    // => 예) Score2 인스턴스를 저장한 후,
    //       Score2 클래스를 변경하라.
    //       예를 들어 인스턴스 필드를 추가해보라.
    //       그런 후에 다시 읽기를 시도해보라.
    //
    FileInputStream in0 = new FileInputStream("temp/score.data");
    ObjectInputStream in = new ObjectInputStream(in0);

    Score2 score = (Score2) in.readObject();
    System.out.println(score);

    // Score2 클래스를 변경한 후 이 클래스를 이용하여 
    // serialize 된 데이터를 읽어들이면 예외가 발생한다.
    // 예외 분석:
    // => java.io.Serializable 구현체는 내부적으로 
    //    serialVersionUID라는 스태틱 변수가 자동으로 생긴다.
    //    serialize 되는 데이터의 파일 버전 역할을 한다.
    // => serialVersionUID 값을 개발자가 지정하지 않으면 
    //    컴파일할 때 자동으로 부여된다.
    // => Score2 클래스 또한 이 변수가 존재하고,
    //    score2.data 파일로 인스턴스를 출력할 때 
    //    이 변수의 값이 함께 저장된다.
    // => Score2 클래스를 변경하게 되면 이 변수의 값이 자동으로 새로 부여된다.
    // => ObjectInputStream.readObject()는 
    //    score2.data 파일을 읽을 때 함께 저장된 serialVersionUID 값과 
    //    deserialize 할 때 사용할 Score2 클래스의 serialVersionUID 값을 
    //    비교하여 같다면 파일의 데이터를 읽어 인스턴스를 생성하여 리턴하고,
    //    다르다면 예외를 발생시킨다.

    in.close();
  }
}
