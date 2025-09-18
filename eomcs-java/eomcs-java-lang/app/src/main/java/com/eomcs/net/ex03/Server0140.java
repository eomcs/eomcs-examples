// 클라이언트와 입출력 테스트 - byte stream : Data 주고 받기 II
package com.eomcs.net.ex03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server0140 {
  public static void main(String[] args) {
    try (Scanner keyboard = new Scanner(System.in);
        ServerSocket serverSocket = new ServerSocket(8888)) {

      System.out.println("클라이언트의 연결을 기다리고 있음.");

      try (Socket socket = serverSocket.accept();
          DataOutputStream out = new DataOutputStream(socket.getOutputStream());
          DataInputStream in = new DataInputStream(socket.getInputStream())) {

        System.out.println("클라이언트가 보낸 Data를 기다리고 있음!");

        int value = in.readInt();
        byte value2 = in.readByte();
        float value3 = in.readFloat();
        String value4 = in.readUTF();
        System.out.printf("%d, %d, %f, %s\n", value, value2, value3, value4);


        System.out.println("데이터를 보내기 전에 잠깐!");
        keyboard.nextLine();

        // 클라이언트에서 받은 Data를 그대로 리턴한다.
        out.writeInt(value);
        out.writeByte(value2);
        out.writeFloat(value3);
        out.writeUTF(value4);
        // out.flush();
        // byte stream 을 사용할 때는 바로 출력한다.
        // 따라서 flush()를 호출하지 않아도 된다.
        System.out.println("클라인트에게 데이터를 보냈음.");

      }
      System.out.println("클라이언트와의 연결을 끊었음.");

    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("서버 종료!");
  }

}

