// Serialize - transient 필드
package com.eomcs.io.ex12.c;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Test3_2 {
  public static void main(String[] args) throws Exception {

    FileInputStream in0 = new FileInputStream("temp/score.data");
    ObjectInputStream in = new ObjectInputStream(in0);

    Score3 score = (Score3) in.readObject();
    System.out.println(score);

    // transient 필드는 보안상 serialize 되지 않기 때문에 
    // deserialize 한 후 계산을 다시 해야 한다.
    // => 파일을 조작해서 합계와 평균을 왜곡하는 상황을 막을 수 있다. 
    score.compute();
    System.out.println(score);

    in.close();
  }
}
