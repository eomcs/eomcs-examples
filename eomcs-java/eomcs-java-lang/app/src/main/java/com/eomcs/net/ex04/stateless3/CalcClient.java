// stateless 에서 다중 클라이언트 요청 처리하기
package com.eomcs.net.ex04.stateless3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class CalcClient {
  public static void main(String[] args) throws Exception {
    Scanner keyScan = new Scanner(System.in);

    // 서버에서 이 클라이언트를 구분할 때 사용하는 번호이다.
    // => 0 번으로 서버에 요청하면, 서버가 신규 번호를 발급해 줄 것이다.
    long clientId = 0;

    while (true) {
      System.out.print("연산자? ");
      String op = keyScan.nextLine();

      System.out.print("값? ");
      int value = Integer.parseInt(keyScan.nextLine());

      try (Socket socket = new Socket("localhost", 8888);
          DataOutputStream out = new DataOutputStream(socket.getOutputStream());
          DataInputStream in = new DataInputStream(socket.getInputStream())) {

        // => 서버에 클라이언트 아이디를 보낸다.
        out.writeLong(clientId);

        // => 서버에 연산자와 값을 보낸다.
        out.writeUTF(op);
        out.writeInt(value);
        out.flush();

        // => 서버에서 보낸 클라이언트 아이디를 읽는다.
        clientId = in.readLong();

        // => 서버에서 보낸 결과를 읽는다.
        int result = in.readInt();
        System.out.printf("계산 결과: %d\n", result);

      } catch (Exception e) {
        System.out.println("서버와 통신 중 오류 발생!");
      }

      System.out.print("계속하시겠습니까?(Y/n)");
      if (keyScan.nextLine().equalsIgnoreCase("n")) {
        break;
      }
    }

    keyScan.close();
  }
}


