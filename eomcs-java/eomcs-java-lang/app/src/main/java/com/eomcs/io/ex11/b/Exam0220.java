// 인스턴스 입출력 - ObjectInputStream으로 인스턴스 읽기
package com.eomcs.io.ex11.b;

import java.io.FileInputStream; // Data Sink Stream Class = Concrete Component
import java.io.ObjectInputStream; // Data Processing Stream Class = Decorator

public class Exam0220 {

  public static void main(String[] args) throws Exception {
    // ObjectInputStream
    // => DataInputStream의 기능을 포함한다.
    // => ObjectOutputStream.writeObject()로 출력한 바이트 배열을 읽어
    //    인스턴스를 생성하는 기능이 있다.
    // => 단 java.io.Serializable 인터페이스를 구현한 클래스여야 한다.
    //    출력할 때 클래스 버전과 읽을 때 클래스의 버전이 같아야 한다.
    // => 인스턴스 필드를 생성한 후 직접 필드에 값을 저장한다.
    //    그래서 역직렬화를 통해 인스턴스를 만들 때는 생성자가 호출되지 않는다.
    //    따라서 초기화시킬 것이 있다면 따로 메서드를 호출해야 한다.
    //
    FileInputStream fileIn = new FileInputStream("temp/member2.data");
    ObjectInputStream in = new ObjectInputStream(fileIn);

    // Serialize로 출력된 데이터를 읽어 다시 원래의 객체로 만들기
    Member member = (Member) in.readObject();

    in.close();

    System.out.println(member);
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

