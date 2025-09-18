package com.eomcs.oop;

public class Test1 {

  public static void main(String[] args) {
    //    Board b = new Board();
    //    b.setNo(100);
    //    b.setTitle("okok");
    //    b.setContent("content..ok");

    Board b2 = Board.builder()
        .no(100)
        .title("okok")
        .content("nono")
        .build();

  }


}
