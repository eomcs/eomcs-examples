// 서버와 입출력 테스트 - byte stream + buffer
package com.eomcs.net.ex03;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client0160 {
  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);

    try (Socket socket = new Socket("localhost", 8888);
        PrintStream out = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));
        Scanner in = new Scanner(new BufferedInputStream(socket.getInputStream()))) {

      System.out.println("서버와 연결되었음!");

      // 서버에 데이터를 보내기 전에 잠깐 멈춤!
      System.out.print(">");
      keyScan.nextLine();

      out.println("ABC가각간");
      out.flush();
      // 버퍼를 사용할 때는
      // 데이터를 보내기 위해 반드시 flush()를 호출해야 한다.
      // => 버퍼에 남아 있는 데이터를 연결된 출력 스트림을 이용하여 내보낸다.
      System.out.println("서버에 데이터를 보냈음!");

      String str = in.nextLine();
      System.out.println(str);

    } catch (Exception e) {
      e.printStackTrace();
    }

    keyScan.close();
  }
}


