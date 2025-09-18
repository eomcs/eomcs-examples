// BufferedFileInputStream 사용 후 - 데이터 읽는데 걸린 시간 측정
package com.eomcs.io.ex06;

public class Exam0130 {

  public static void main(String[] args) throws Exception {
    BufferedFileInputStream in = new BufferedFileInputStream("temp/jls.pdf");

    int b;

    long startTime = System.currentTimeMillis(); // 밀리초

    int callCount = 0;
    while ((b = in.read()) != -1)
      callCount++; // 파일을 끝까지 읽는다.

    // => BufferedFileInputStream의 read() 메서드는
    //    FileInputStream에서 상속 받은 메서드를 이용하여
    //    바이트 배열로 데이터를 왕창 가져온 다음
    //    그 배열에서 1바이트를 리턴한다.
    // => 그 이후에는 바이트 배열의 데이터가 떨어질 때까지
    ///   계속 바이트 배열에서 값을 꺼내 리턴해 준다.
    // => 바이트 배열의 값이 떨어지면
    //    다시 바이트 배열 단위로 데이터를 읽어 온다.
    //    그래서 1바이트 씩 읽더라도 그렇게 속도가 떨어지지 않는 것이다.

    long endTime = System.currentTimeMillis();

    System.out.println(endTime - startTime);
    System.out.println(callCount);

    in.close();
  }

}
