package com.eomcs.io.ex06;

public class Exam0230 {
  public static void main(String[] args) throws Exception {
    BufferedFileOutputStream out = new BufferedFileOutputStream("temp/data.bin");

    System.out.println("데이터 쓰는 중...");

    long start = System.currentTimeMillis();

    for (int i = 0; i < 1000000; i++) {
      out.write(0x55);
      // BufferedFileOutputStream은
      // 내부적으로 1바이트를 파일로 바로 출력하지 않고
      // 일단 byte[] 배열에 저장한다.
      // 바이트 배열이 꽉 찼을 때 파일로 출력한다.
      // 그래서 1바이트씩 파일로 바로 출력하는 것 보다 더 빠르다.
    }

    // BufferedFileOutputStream은 바이트 배열이 꽉 찼을 때만 파일로 출력하기 때문에
    // 바이트 배열에 데이터가 남아 있을 경우 강제적으로 출력해야 한다.
    out.flush(); // 버퍼에 남아 있는 것을 방출한다.

    long end = System.currentTimeMillis();

    out.close();

    System.out.println("출력 완료!");
    System.out.printf("경과된 시간: %d\n", end - start);
  }
}
