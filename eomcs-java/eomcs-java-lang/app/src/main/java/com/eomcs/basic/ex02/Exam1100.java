// String - chatAt()과 codePointAt()
package com.eomcs.basic.ex02;

public class Exam1100 {
  public static void main(String[] args) throws Exception {
    char[] chars = Character.toChars(128149); // 128149 = 0x0001F495
    String str = String.valueOf(chars);
    System.out.printf("문자열: %s\n", str);

    System.out.println("UTF-16(surrogate pair) --------------------");
    System.out.printf("char[].length: %d\n", chars.length);
    System.out.printf("char[]: %x %x\n", (int)chars[0], (int)chars[1]);
    System.out.printf("String.length(): %d\n", str.length());
    System.out.printf("String.codePointCount(): %d\n", str.codePointCount(0, str.length()));

    System.out.println("UTF-8 --------------------");
    byte[] bytes = str.getBytes("UTF-8");
    System.out.printf("byte[].length: %d\n", bytes.length);
    System.out.printf("byte[]: %02x %02x %02x %02x\n",
        bytes[0] & 0xff,
        bytes[1] & 0xff,
        bytes[2] & 0xff,
        bytes[3] & 0xff);

  }
}

// ASCII(American Standard Code for Information Interchange)
// - 0 ~ 31: 출력할 수 없는 제어코드
// - 32 ~ 127: 출력할 수 있는 문자코드
// - 128 ~ 255: 확장 아스키 코드
//
// 유니코드(Unicode, 2022년 기준 버전 15.0)
// - code point = codepoint = code position
//   - 특정 문자에 부여한 정수 값이다.
//   - code point의 최대 크기는 공식적으로 21비트(0x10FFFF)로 제한되었다.
//   - 항상 맨앞에 'U+'를 붙여서 16진수로 표현한다.
//   - 최소 2바이트에서 최대 3바이트로 표현: U+0000 ~ U+10FFFF
//   - 예1) A : U+0041
//   - 예2) 가: U+AC00
//   - 예3) 💕: U+1F494
// - 평면(plane)
//   - 구획을 나누는 개념
//   - 0번 ~ 16번까지 총 17개 평면으로 나누고 있다.
//   - 0번(U+0000 ~ U+FFFF)
//     - 기본 다국어 평면(BMP; Basic Multilingual Plane)
//     - 거의 모든 근대 문자와 특수 문자 포함.
//     - 대부분 한글과 한중일 통합 한자들로 이루어져 있다.
//     - U+1100 ~ U+11FF : 한글 자모
//     - U+AC00 ~ U+D7AF : 한글 소리 마디
//   - 1번(U+10000 ~ U+1FFFF)
//     - 보조 다국어 평면(SMP; Supplementary Multilingual Plane)
//     - 옛문자, 음악기호, 수학기호 등
//   - 2번(U+20000 ~ U+2FFFF)
//     - 보조 상형문자 평면(SIP; Supplementary Ideographic Plane)
//     - 초기 유니코드에 포함되지 않은 한중일 통합 한자.
//   - 3번(U+30000 ~ U+3FFFF)
//     - 보조 상형문자 3평면(TIP; Tertiary Ideographic Plane)
//     - 갑골문자, 금문, 소전 등 한중일 통합한자. 기타 옛 상형문자를 위해 예약된 영역.
//   - 4번 ~ 13번(U+40000 ~ U+DFFFF)
//     - 미지정 평면
//   - 14번(U+E0000 ~ U+EFFFF)
//     - 보조 특수목적 평면(SSP; Supplementary Special-purpose Plane)
//     - 약간의 제어용 문자
//   - 15번 ~ 16번(U+F0000 ~ U+10FFFF)
//     - 사용자 영역. 특정 업체나 사용자 별로 할당하는 영역.
//     - 따라서 소프트웨어간 글꼴간 호환성이 보장되지 않는다.
// - 자바에서 유니코드를 다루는 방법
//   - 대부분의 문자는 기본 다국어 평면(U+0000 ~ U+FFFF)에 속해 있기 때문에
//     char(16비트) 타입 메모리를 사용하여 UTF-16BE 으로 인코딩 한다.
//   - 자바는 UTF-32를 지원하지 않는다.
//   - 메모리 효율성을 위해 UTF-16으로 표현할 수 없는 경우에만
//     "Surrogate Pair" 방식으로 인코딩(4byte) 한다.
//   - 즉 65536(0x010000) ~ 1,114,111(0x10FFFF) 사이의 유니코드를 저장할 때만
//     "Surrogate Pair" 방식으로 인코딩(4byte) 한다.
// Surrogate Pair(유니코드 대리 쌍)
// - 대리 쌍: code point가 65536(0x010000) ~ 1,114,111(0x10FFFF) 사이의 값을 4바이트로 표현
//   - 1,048,576 개의 문자
// - 16비트 두 쌍을 이용하여 인코딩 한다.
//   - high surrogate + low surrogate = 4바이트
//   - high surrogate: U+D800 ~ U+DBFF(1024개)
//     - 110110uu uuyyyyyy
//     - uuuu = 원래 값(zzzz) - 1 => code point - 65536
//   - low surrogate: U+DC00 ~ U+DFFF(1024개)
//     - 110111yy xxxxxxxx
// - 예) 💕(128149 = 0x01f495 = 0001 11110100 10010101)
//   - code point가 0 ~ 65535 까지의 값은 UTF-16으로 저장하고
//     그 이후의 값을 4바이트 대리쌍으로 저장하므로
//     code point 65536을 0으로 시작하여 대리쌍으로 저장한다.
//   - 따라서 code point 128149(0x1f495)을 저장할 때는 먼저 65536을 빼야 한다.
//     128149(0x1f495) - 65536(0x10000) = 62613(0xf495)
//   - 2진수 계산법
//     원래 값: zzzz yyyyyyyy xxxxxxxx
//     계산 값: uuuu yyyyyyyy xxxxxxxx (uuuu = zzzz - 1 => 즉 65536을 뺀다)
//     대리 쌍: 110110uu uuyyyyyy 110111yy xxxxxxxx
//   - 계산 예) 💕(128149 = 0x1f495)
//     원래 값: 0001 11110100 10010101
//     계산 값: 0000 11110100 10010101
//     대리 쌍: 11011000 00111101 11011100 10010101 (d8 3d dc 95)
// UTF-8
// -  U+0000  ~    U+007F: 0xxxxxxx
// -  U+0080  ~    U+07FF: 110xxxxx 10xxxxxx
// -  U+0800  ~    U+FFFF: 1110xxxx 10xxxxxx 10xxxxxx
// - U+10000  ~  U+10FFFF: 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
// - 예) 💕(128149 = 0x01f495 = 0001 11110100 10010101)
//   원래 값: 0001 11110100 10010101
//     UTF-8: 11110000 10011111 10010010 10010101 (f0 9f 92 95)
// Modified UTF-8
// - 자바 언어에서 사용
// - U+0000 : 0xc080(11000000 1000000)
//   - 전통적으로 문자열을 다루는 함수는 null을 문자열의 끝으로 인식한다.
//     이런 함수들이 문자열을 다루는데 문제가 없도록 0000 대신에 C080을 사용한다.
// -  U+0000  ~    U+007F: 0xxxxxxx
// -  U+0080  ~    U+07FF: 110xxxxx 10xxxxxx
// -  U+0800  ~    U+FFFF: 1110xxxx 10xxxxxx 10xxxxxx
// - U+10000  ~  U+10FFFF: 11101101 1010xxxx 10xxxxxx 11101101 1011xxxx 10xxxxxx
// - 예) 💕(128149 = 0x01f495 = 0001 11110100 10010101)
//          원래 값: 0001 11110100 10010101
//   Modified UTF-8: 11101101 10100001 10111101 11101101 10110010 10010101 (ed a1 bd ed b2 95)


