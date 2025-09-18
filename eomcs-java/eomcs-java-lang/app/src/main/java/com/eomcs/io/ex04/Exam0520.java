// FileInputStream: float, double 값 읽기
package com.eomcs.io.ex04;

import java.io.FileInputStream;

public class Exam0520 {

  public static void main(String[] args) throws Exception {
    FileInputStream in = new FileInputStream("test6.data");

    // float 값에 해당하는 바이트 읽기
    int temp = (in.read() << 24) 
        + (in.read() << 16) 
        + (in.read() << 8) 
        + in.read();

    // int 변수에 저장된 것을 float 변수에 담기
    float f = Float.intBitsToFloat(temp);
    System.out.printf("%f\n", f);


    // double 값에 해당하는 바이트 읽기
    long temp2 = (((long) in.read()) << 56) 
        + (((long) in.read()) << 48) 
        + (((long) in.read()) << 40)
        + (((long) in.read()) << 32) 
        + (((long) in.read()) << 24) 
        + (((long) in.read()) << 16) 
        + (((long) in.read()) << 8)
        + in.read();

    // long 변수에 저장된 것을 double 변수에 담기
    double d = Double.longBitsToDouble(temp2);
    System.out.printf("%f\n", d);

    in.close();

    System.out.println("실행 완료!");
  }

}
