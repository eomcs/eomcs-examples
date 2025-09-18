// 바이너리 파일을 텍스트 형식으로 저장하기
package com.eomcs.io.ex15;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Base64;
import java.util.Base64.Encoder;

public class Exam0110 {
  public static void main(String[] args) throws Exception {

    Encoder encoder = Base64.getEncoder();

    File file = new File("./temp/photo.jpg");
    FileInputStream in = new FileInputStream(file);
    FileWriter out = new FileWriter("./temp/photo.txt");

    byte[] buf = new byte[(int)file.length()];
    int len = in.read(buf);
    System.out.printf("읽은 바이트 수: %d\n", len);

    // 바이트 배열에 저장된 바이너리 데이터를 텍스트로 변환하기
    String encodedStr = encoder.encodeToString(buf);

    // 텍스트로 변환된 데이터를 파일로 출력하기
    out.write(encodedStr);


    in.close();
    out.close();
  }
}
