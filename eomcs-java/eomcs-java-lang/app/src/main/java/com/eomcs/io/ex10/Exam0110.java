// Java I/O API 사용하기 - DataOutputStream
package com.eomcs.io.ex10;

import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    FileOutputStream fileOut = new FileOutputStream("temp/members.data");
    DataOutputStream out = new DataOutputStream(fileOut);

    Member member = new Member();
    member.name = "AB가각간";
    member.age = 27;
    member.gender = true;

    // 장신구/보조장치/플러그인(decorator)를 사용하여 String,int,boolean 값을 출력한다.
    long startTime = System.currentTimeMillis();

    for (int i = 0; i < 100000; i++) {
      out.writeUTF(member.name);
      out.writeInt(member.age);
      out.writeBoolean(member.gender);
    }

    long endTime = System.currentTimeMillis();

    out.close();
    // DataOutputStream 객체를 close()하면
    // 이 스트림 객체와 연결된 FileOutputStream에 대해서도
    // 내부적으로 close() 하기 때문에
    // FileOutputStream 객체를 따로 close() 할 필요가 없다.


    System.out.println("데이터 출력 완료!");
    System.out.println(endTime - startTime);
  }

}

// java.io 패키지의 클래스들
// 1) byte(binary) stream class
// - 입력: InputStream
// - data sink stream class
// - 실제 데이터를 꺼내는 일을 한다
// - 예) FileInputStream, ByteArrayInputStream, PipedInputStream
// - data processing stream class(=decorator)
// - 데이터를 중간에서 가공하는 일을 한다.
// - 예) BufferedInputStream, DataInputStream, ObjectInputStream
// - 출력: OutputStream
// - data sink stream class
// - 실제 데이터를 저장하는 일을 한다.
// - 예) FileOutputStream, ByteArrayOutputStream, PipedOutputStream
// - data processing stream class(=decorator)
// - 데이터를 중간에서 가공하는 일을 한다.
// - 예) BufferedOutputStream, DataOutputStream, ObjectOutputStream, PrintStream
// 2) character stream class
// - 입력: Reader
// - data sink stream class
// - 실제 데이터를 읽는 일을 한다.
// - 예) FileReader, CharArrayReader, StringReader, PipedReader
// - data processing stream class(=decorator)
// - 데이터를 중간에서 가공하는 일을 한다.
// - 예) BufferedReader, LineNumberReader
// - 출력: Writer
// - data sink stream class
// - 실제 데이터를 읽는 일을 한다.
// - 예) FileWriter, CharArrayWriter, StringWriter, PipedWriter
// - data processing stream class(=decorator)
// - 데이터를 중간에서 가공하는 일을 한다.
// - 예) BufferedWriter, PrintWriter


