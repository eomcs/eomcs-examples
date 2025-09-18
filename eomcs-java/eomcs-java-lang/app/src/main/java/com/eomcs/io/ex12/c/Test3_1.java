// Serialize - trainsient 필드
package com.eomcs.io.ex12.c;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Test3_1 {

  public static void main(String[] args) throws Exception {
    FileOutputStream out0 = new FileOutputStream("temp/score.data");
    ObjectOutputStream out = new ObjectOutputStream(out0);

    Score3 score = new Score3();
    score.setName("홍길동");
    score.setKor(100); // setter 내부에서 compute()를 호출하여 sum과 aver를 자동 계산한다.
    score.setEng(100);
    score.setMath(100);
    
    // 출력하기 전에 현재 Score3 인스턴스의 값을 출력해 보자!
    System.out.println(score);
    
    // 인스턴스 출력하기
    // => 내부 필드의 값을 가지고 계산한 값을 보관하는 필드는 
    //    가능한 serialize 대상에서 제외해야 한다.
    // => 왜?
    //    출력 후 데이터의 왜곡을 막기 위함이다.
    //    출력한 파일을 조작해서 의도와 다른 값을 가지게 할 수 있기 때문이다.
    // => 방법?
    //    필드에 serialize 대상이 아니라고 표시하라!
    //    transient 한정자(modifier)를 붙인다.
    //    transient => 일시적인 
    // => Score3 클래스의 sum 필드와 aver 필드에 transient를 붙이고 
    //    출력해보라!
    //    해당 필드의 값이 출려되지 않을 것이다.
    out.writeObject(score);

    System.out.println("출력 완료!");

    out.close();

  }

}
