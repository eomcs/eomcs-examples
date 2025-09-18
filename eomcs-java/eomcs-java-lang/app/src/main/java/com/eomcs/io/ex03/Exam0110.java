// character stream - 문자 단위로 출력하기
package com.eomcs.io.ex03;

import java.io.FileWriter;

public class Exam0110 {
  public static void main(String[] args) throws Exception {

    // JVM의 문자열을 파일로 출력할 때
    // FileOutputStream 과 같은 바이트 스트림 클래스를 사용하면
    // 문자집합을 지정해야 하는 번거로움이 있었다.
    // => 이런 번거로움을 해결하기 위해 만든 스트림 클래스가 있으니,
    //    문자 스트림 클래스이다.
    // => 즉 Reader/Writer 계열의 클래스이다.

    // 1) 문자 단위로 출력할 도구 준비
    FileWriter out = new FileWriter("temp/test2.txt");

    // 2) 문자 출력하기
    // - JVM은 문자 데이터를 다룰 때 UCS2(UTF16BE, 2바이트) 유니코드를 사용한다.
    // - character stream 클래스 FileWriter는 문자 데이터를 출력할 때
    //   UCS2 코드를 JVM 환경변수 file.encoding 에 설정된 character set 코드로 변환하여 출력한다.
    // - JVM을 실행할 때 -Dfile.encoding=문자집합 옵션으로 기본 문자 집합을 설정한다. 
    //   만약 file.encoding 옵션을 설정하지 않으면 OS의 기본 문자집합으로 자동 설정된다.
    // 
    //   Linux, macOS 의 기본 character set => UTF-8
    //   Windows 의 기본 character set => MS-949
    // 
    // - file.encoding 옵션을 설정하지 않은 상태에서는 
    //   어느 OS에서 실행하느냐에 따라 출력되는 코드 값이 달라진다.
    // 
    // - 이클립스에서 실행할 경우, 
    //   이클립스가 file.encoding 옵션의 문자집합을 UTF-8로 설정한다.
    // 
    // - 명령창(콘솔창)에서 실행한다면,
    //   위에서 설명한대로 OS에 따라 다르게 인코딩 된다.
    //
    // - OS에 상관없이 동일한 character set으로 출력하고 싶다면
    //   JVM을 실행할 때 file.encoding 프로퍼티에 character set 이름을 지정하라.
    //
    // - JVM을 실행할 때 출력 데이터의 문자 코드표를 지정하는 방법
    //       java -Dfile.encoding=문자코드표 -cp 클래스경로 클래스명
    //   예) java -Dfile.encoding=UTF-8 -cp bin/main com.eomcs.io.ex03.Exam0110
    //
    // - 단, character set을 지정할 때는 해당 OS에서 사용가능한 문자표이어야 한다.
    //   MS Windows에서는 ms949 문자표를 사용할 수 있지만,
    //   리눅스나 macOS에서는 ms949 문자표를 사용할 수 없다.
    //   왜?
    //   국제 표준이 아니기 때문이다.
    //
    // [이클립스에서 실행]
    // - JVM을 실행할 때 '-Dfile.encoding=UTF-8' 옵션을 자동으로 붙인다.
    // - 그래서 출력할 때, UCS2를 UTF-8 코드로 변환하여 파일에 쓴다.
    //
    // [콘솔창에서 실행]
    // 1) file.encoding 환경 변수를 지정하지 않으면,
    // - OS가 기본으로 사용하는 문자 코드로 변환하여 파일에 쓴다.
    // - windows : MS949
    // - linux/unix/macOS : UTF-8
    //
    // 2) JVM을 실행할 때 '-Dfile.encoding=문자코드표' 옵션을 지정한다면,
    // - 해당 옵션에 지정된 문자 코드로 변환하여 파일에 쓴다.
    //
    // [결론]
    // - OS에 영향 받지 않으려면,
    //   JVM을 실행할 때 반드시 file.encoding JVM 환경 변수를 설정하라.
    // - 문자집합은 UTF-8을 사용하라.
    //   - 국제 표준이다.
    //   - linux, macOS의 기본 문자 집합이다.
    //

    // 현재 JVM 환경 변수 'file.encoding' 값 알아내기
    System.out.printf("file.encoding=%s\n", System.getProperty("file.encoding"));

    // => 이 예제를 이클립스에서 실행한다면,
    // 앞의 2바이트는 버리고,
    // 뒤의 2바이트(UCS2)를 UTF-8 코드표에 따라
    // 1 ~ 4 바이트 값으로 변환하여 파일에 쓴다.
    // => 즉 이클립스에서 자바 프로그램을 실행할 때
    //    -Dfile.encoding=UTF-8 옵션을 붙여 실행하기 때문이다.
    // => OS의 기본 문자표로 출력하고 싶다면,
    //    콘솔창에서 위 옵션 없이 직접 이 클래스를 실행하라.
    //
    // UCS2에서 한글 '가'는 ac00이다.
    out.write(0x7a6bac00);
    // - 앞의 2바이트(7a6b)는 버린다.
    // - 뒤의 2바이트(ac00)은 UTF-8(eab080) 코드 값으로 변환되어 파일에 출력된다.

    // UCS2에서 영어 A는 0041이다.
    // 출력하면, UTF-8 코드 값(41)이 파일에 출력된다.
    out.write(0x7a5f0041);  //
    // - 앞의 2바이트(7a5f)는 버린다.
    // - 뒤의 2바이트(0041)는 UTF-8(41) 코드 값으로 변환되어 파일에 출력된다.

    out.close();

    System.out.println("출력 완료!");

  }
}
