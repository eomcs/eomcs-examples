// 인스턴스 읽기
package com.eomcs.io.ex12.a;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class Test01_2 {

  public static void main(String[] args) throws Exception {

    // score.data 파일에서 세 학생의 데이터를 읽어 Score 인스턴스로 생성하라.
    // => java.io.BufferedInputStream 클래스를 사용하라.
    // => java.io.DataInputStream 클래스를 사용하라.
    //
    Score s1 = null;
    Score s2 = null;
    Score s3 = null;

    FileInputStream in0 = new FileInputStream("temp/score.data");
    BufferedInputStream in1 = new BufferedInputStream(in0);
    DataInputStream in = new DataInputStream(in1);

    // 데이터를 읽을 때는 반드시 파일에 출력한 형식(file format)에 맞춰서 읽어야 한다.
    s1 = new Score();
    s1.setName(in.readUTF());
    s1.setKor(in.readInt());
    s1.setEng(in.readInt());
    s1.setMath(in.readInt());

    s2 = new Score();
    s2.setName(in.readUTF());
    s2.setKor(in.readInt());
    s2.setEng(in.readInt());
    s2.setMath(in.readInt());

    s3 = new Score();
    s3.setName(in.readUTF());
    s3.setKor(in.readInt());
    s3.setEng(in.readInt());
    s3.setMath(in.readInt());


    // 그리고 세 학생의 정보를 다음과 같은 형식으로 출력하라.
    // =>   홍길동, 100, 100, 100, 300, 100 
    // 

    System.out.printf("%s, %d, %d, %d, %d, %.1f\n", 
        s1.getName(), s1.getKor(), s1.getEng(), s1.getMath(),
        s1.getSum(), s1.getAver());

    System.out.printf("%s, %d, %d, %d, %d, %.1f\n", 
        s2.getName(), s2.getKor(), s2.getEng(), s2.getMath(),
        s2.getSum(), s2.getAver());

    System.out.printf("%s, %d, %d, %d, %d, %.1f\n", 
        s3.getName(), s3.getKor(), s3.getEng(), s3.getMath(),
        s3.getSum(), s3.getAver());
    
    // 읽을 데이터가 없는 파일에서 학생의 성적 정보를 읽으로 한다면 
    Score s4 = new Score();
    s4.setName(in.readUTF());
    s4.setKor(in.readInt());
    s4.setEng(in.readInt());
    s4.setMath(in.readInt());

    in.close();
  }

}
