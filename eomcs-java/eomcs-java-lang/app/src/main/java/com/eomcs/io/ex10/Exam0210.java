// Java I/O API : FileInputStream --> BufferedInputStream --> DataInputStream
package com.eomcs.io.ex10;

import java.io.BufferedInputStream;
import java.io.DataInputStream; // Data Processing Stream Class = Decorator
import java.io.FileInputStream; // Data Sink Stream Class = Concrete Component

public class Exam0210 {

  public static void main(String[] args) throws Exception {

    FileInputStream fileIn = new FileInputStream("temp/members.data");
    BufferedInputStream bufIn = new BufferedInputStream(fileIn);
    DataInputStream in = new DataInputStream(bufIn);

    Member member = new Member();

    long start = System.currentTimeMillis();

    for (int i = 0; i < 100000; i++) {
      member.name = in.readUTF();
      member.age = in.readInt();
      member.gender = in.readBoolean();
    }

    long end = System.currentTimeMillis();

    System.out.println(end - start);

    in.close();
  }
}


