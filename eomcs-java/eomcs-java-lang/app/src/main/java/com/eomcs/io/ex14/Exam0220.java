// 파일 입출력 객체에 데코레이터 연결하기
package com.eomcs.io.ex14;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Exam0220 {
  public static void main(String[] args) {
    
    DataOutputStream out = null;
    
    try {
      out = new DataOutputStream(
              new BufferedOutputStream(
                  new FileOutputStream("temp3/data.bin")));
      out.writeInt(0x11223344);
      
    } catch (IOException e) {
      System.out.println("파일 입출력 예외 발생!");
      e.printStackTrace();
      
    } finally {
      // 실제 가장 바깥 입출력 객체를 close() 하면
      // 안쪽의 객체를 찾아가면서 close()를 호출하도록 되어 있다.
      try {out.close();} catch (Exception e2) {}
    }

    System.out.println("출력 완료!");
  }
}








