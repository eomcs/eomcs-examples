// 서버와 입출력 테스트 - character stream
package com.eomcs.net.ex03;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client0210 {
  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);

    try (Socket socket = new Socket("localhost", 8888);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

      System.out.println("서버와 연결되었음!");

      // 서버에 데이터를 보내기 전에 잠깐 멈춤!
      System.out.print("데이터를 보내기 전에 잠깐!>");
      keyScan.nextLine(); // blocking : 키보드에서 한 줄을 읽을 때까지

      out.println("ABC가각간");
      out.flush();
      // character stream 클래스의 경우
      // 출력 데이터를 내부 버퍼에 보관하고 있다가
      // 버퍼가 꽉차거나 flush()를 호출할 때 출력을 수행한다.
      // 따라서 위 출력 문자열은 서버에 보내지 않는다.
      // - BufferedWriter를 붙이지 않아도 이렇게 동작하기 때문에 주의하라!
      System.out.println("서버에 데이터를 보냈음!");

      String str = in.readLine();
      System.out.println(str);

    } catch (Exception e) {
      e.printStackTrace();
    }

    keyScan.close();
  }
}


