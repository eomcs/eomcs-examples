package com.eomcs.netty.ex01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class DiscardClient {
  public static void main(String[] args) throws Exception {

    Scanner keyIn = new Scanner(System.in);
    try (
        Socket socket = new Socket("localhost", 8888);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));) {

      while (true) {
        System.out.print("입력> ");
        String input = keyIn.nextLine();
        if (input.equals("exit"))
          break;
        out.println(input);
        out.flush();

        System.out.println(in.readLine());
      }
    }
    keyIn.close();

  }
}
