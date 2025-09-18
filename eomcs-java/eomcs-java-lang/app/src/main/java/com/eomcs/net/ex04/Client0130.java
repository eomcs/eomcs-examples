// 통신 방식 - Stateful + 서버 종료
package com.eomcs.net.ex04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client0130 {
  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);

    try (Socket socket = new Socket("localhost", 8888);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

      System.out.println("서버와 연결되었음!");

      String name = null;
      do {
        System.out.print("이름? ");
        name = keyScan.nextLine();

        out.println(name);
        out.flush();

        String str = in.readLine();
        System.out.println(str);

      } while (!name.equalsIgnoreCase("quit") && !name.equalsIgnoreCase("stop"));

    } catch (Exception e) {
      e.printStackTrace();
    }

    keyScan.close();
  }
}


