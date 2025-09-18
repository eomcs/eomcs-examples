// stateful 방식의 이점 활용 - 계산기 클라이언트 만들기
package com.eomcs.net.ex04.stateful2;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class CalcClient {
  public static void main(String[] args) throws Exception {
    Scanner keyScan = new Scanner(System.in);

    Socket socket = new Socket("localhost", 8888);
    Scanner in = new Scanner(socket.getInputStream());
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

    while (true) {
      System.out.print("연산자? ");
      out.writeUTF(keyScan.nextLine());

      System.out.print("값? ");
      out.writeInt(Integer.parseInt(keyScan.nextLine()));

      String str = in.nextLine();
      System.out.println(str);

      if (str.equals("Goodbye!"))
        break;
    }

    in.close();
    out.close();
    socket.close();
    keyScan.close();
  }
}


