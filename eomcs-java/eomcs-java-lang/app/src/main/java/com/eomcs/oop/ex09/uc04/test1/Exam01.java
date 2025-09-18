package com.eomcs.oop.ex09.uc04.test1;

import com.eomcs.oop.ex09.uc04.Printer;

public class Exam01 {

  public static void main(String[] args) {
    PaperPrinter p1 = new PaperPrinter();
    FilmPrinter p2 = new FilmPrinter();

    display(p1, "Hello!");
    display(p2, "World!");

  }

  private static void display(Printer printer, String text) {
    printer.print(text);
  }

}
