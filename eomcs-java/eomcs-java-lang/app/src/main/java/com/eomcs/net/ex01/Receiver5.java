// 파일 받기 + 버퍼
package com.eomcs.net.ex01;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Receiver5 {

  public static void main(String[] args) throws Exception {
    System.out.println("서버 실행 중...");

    ServerSocket serverSocket = new ServerSocket(8888);
    Socket socket = serverSocket.accept();
    System.out.println("클라이언트가 연결됨.");

    PrintStream out = new PrintStream(
        new BufferedOutputStream(socket.getOutputStream()));
    DataInputStream in = new DataInputStream(
        new BufferedInputStream(socket.getInputStream()));

    System.out.println("클라이언트로부터 데이터 수신 중...");

    //1) 파일 크기 읽기
    long filesize = in.readLong();

    //2) 파일 이름 읽기
    String filename = in.readUTF();

    //3) 파일 데이터 읽기
    File file = new File("temp/ok_" + filename);
    BufferedOutputStream fileOut = new BufferedOutputStream( 
        new FileOutputStream(file));

    for (long i = 0; i < filesize; i++) {
      fileOut.write(in.read());
    }
    System.out.println("클라이언트로부터 데이터 수신 완료!");

    //4) 클라이언트에게 응답하기
    out.println("OK!");
    out.flush(); // 버퍼의 남아있는 데이터 방출하기

    in.close();
    out.close();
    socket.close();
    serverSocket.close();
    fileOut.close();
  }

}





