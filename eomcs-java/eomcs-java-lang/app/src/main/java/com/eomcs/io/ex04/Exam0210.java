// 데이터 출력 - int 값 출력 II
package com.eomcs.io.ex04;

import java.io.FileOutputStream;

public class Exam0210 {

  public static void main(String[] args) throws Exception {
    FileOutputStream out = new FileOutputStream("temp/test3.data");

    int money = 1_3456_7890; // = 0x080557d2

    // int 메모리의 모든 바이트를 출력하려면,
    // 각 바이트를 맨 끝으로 이동한 후 write()로 출력한다.
    // 왜?
    // write()는 항상 변수의 마지막 1바이트만 출력하기 때문이다.
    out.write(money >> 24); // 00000008|0557d2
    out.write(money >> 16); // 00000805|57d2
    out.write(money >> 8);  // 00080557|d2
    out.write(money);       // 080557d2

    out.close();

    System.out.println("데이터 출력 완료!");

  }

}
