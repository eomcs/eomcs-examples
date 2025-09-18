// Base64 인코딩 : byte[] 인코딩 및 디코딩
package com.eomcs.net.ex10;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    String str = "ABC012가각";

    byte[] bytes = str.getBytes("UTF-8");
    for (byte b : bytes) {
      System.out.printf("%x ", b);
    }
    System.out.println();

    System.out.println("------------------------------");
    // Base64 인코딩
    // => 바이너리 데이터를 문자화시킨다.
    // => 바이너리 값을 6비트식 잘라서(2의 6승) 
    //    64진수(0 ~ 63)으로 만든 후 Base64 표에 정의된 대로
    //    해당 값을 문자로 변환한다.
    // => 보통 바이너리 데이터를 텍스트로 전송하고 싶을 때 많이 사용한다.
    // => "ABC012가각" 문자열
    //    414243303132EAB080EAB081(UTF-8 코드)
    //    4142 ==> 0100000101000010... (2진수)
    //    010000 010100 0010... (6비트씩 자른 것)
    //    6비트로 자른 것을 다시 10진수로 표현하면 ==> 16 20 ...
    //    16을 Base64 코드표에 따라 문자로 바꾸면 ==> Q
    //    20을 Base64 코드표에 따라 문자로 바꾸면 ==> U
    //    ...
    //    이런 식으로 문자열을 Base64로 바꾸면 결과는 다음과 같다.
    //    QUJDMDEy6rCA6rCB6rCE
    Encoder encoder = Base64.getEncoder();
    byte[] base64Bytes = encoder.encode(bytes);
    System.out.println(new String(base64Bytes));

    System.out.println("------------------------------");

    // Base64 디코딩
    // => Base64 코드를 원래의 바이너리 값으로 변환한다.
    //
    Decoder decoder = Base64.getDecoder();
    byte[] bytes2 = decoder.decode(base64Bytes);

    System.out.println(new String(bytes2, "UTF-8"));


  }

}
/*
1) 이미지 파일 URL
https://i.namu.wiki/i/-hrTmBmZA-jvaJhhQXstLk4tzCDFxMMB0c0uSmOQr4BSiC305Ohbqmat8ohErjpLL_AlY1M3iHZnjIzXuTdXlA.webp

2) 이미지 파일을 base64 포맷으로 바꾼 데이터
data:image/jpeg;base64,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
 */







