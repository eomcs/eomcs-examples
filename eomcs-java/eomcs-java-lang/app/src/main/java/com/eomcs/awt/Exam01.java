package com.eomcs.awt;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Exam01 {

  static class MyButton extends Button {
    public MyButton(String title) {
      super("**" + title + "**");
    }
  }

  public static void main(String[] args) {
    Frame f = new Frame();
    f.setTitle("Hello!");
    f.setBounds(0, 0, 400, 300);
    f.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    f.setLayout(new FlowLayout());

    f.add(new MyButton("Hello!!!"));
    f.add(new MyButton("Hello!!!"));
    f.add(new MyButton("Hello!!!"));
    f.add(new MyButton("Hello!!!"));
    f.add(new MyButton("Hello!!!"));
    f.add(new MyButton("Hello!!!"));
    f.add(new MyButton("Hello!!!"));
    f.add(new MyButton("Hello!!!"));


    f.setVisible(true);
  }
}
