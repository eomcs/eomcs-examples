// 바이너리 파일을 텍스트 형식으로 저장하기
package com.eomcs.io.ex15;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Base64;
import java.util.Base64.Decoder;

public class Exam0120 {
  public static void main(String[] args) throws Exception {

    Decoder decoder = Base64.getDecoder();

    FileReader in = new FileReader("./temp/photo.txt");
    FileOutputStream out = new FileOutputStream("./temp/photox.jpg");

    char[] buf = new char[1000000];
    int len = in.read(buf);
    System.out.printf("읽은 문자 수: %d\n", len);

    // 문자 배열에 저장된 Base64 텍스트를 바이너리 데이터로 변환하기
    byte[] decodedBytes = decoder.decode(String.valueOf(buf, 0, len));

    // 텍스트로 변환된 데이터를 파일로 출력하기
    out.write(decodedBytes);

    in.close();
    out.close();
  }
}
