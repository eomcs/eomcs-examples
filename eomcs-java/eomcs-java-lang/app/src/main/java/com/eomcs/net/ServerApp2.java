package com.eomcs.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp2 {

  public static void main(String[] args) throws Exception {
    System.out.println("서버 실행 중...");
    ServerSocket serverSocket = new ServerSocket(8888);

    Socket socket = serverSocket.accept();
    System.out.println("클라이언트와 연결됨!");

    DataInputStream in = new DataInputStream(socket.getInputStream());
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

    // 클라이언트가 보낸 파일의 이름을 읽는다.
    String filename = in.readUTF();

    // 클라이언트가 보낸 파일의 크기를 읽는다.
    long length = in.readLong();

    // 클라이언트가 보낸 사진 바이트를 파일로 출력한다.
    FileOutputStream fileOut = new FileOutputStream(filename);
    for (long i = 0; i < length; i++) {
      fileOut.write(in.read());
    }
    fileOut.close();

    // 클라이언트에게 응답한다.
    out.writeUTF("완료!");

    in.close();
    out.close();

    socket.close();
    serverSocket.close();

    System.out.println("서버 종료!");
  }

}
