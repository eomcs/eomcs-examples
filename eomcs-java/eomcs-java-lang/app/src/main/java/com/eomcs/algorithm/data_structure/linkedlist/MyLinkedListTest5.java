package com.eomcs.algorithm.data_structure.linkedlist;

public class MyLinkedListTest5 {
  public static void main(String[] args) throws Exception {
    MyLinkedList<String> list = new MyLinkedList<>();

    list.add("aaa"); // aaa
    list.add("bbb"); // aaa, bbb
    list.add("ccc"); // aaa, bbb, ccc
    list.add("ddd"); // aaa, bbb, ccc, ddd

    print(list); // aaa, bbb, ccc, ddd,
    System.out.println("---------------------");

    MyLinkedList<String> list2 = list.clone();
    list2.remove(1); // aaa, ccc, ddd
    print(list2); // aaa, ccc, ddd,
    System.out.println("---------------------");

    // 원본 연결 리스트의 값을 출력하면?
    print(list); // aaa, bbb, ccc, ddd, OK!
    System.out.println("---------------------");

  }

  static void print(MyLinkedList<String> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.print(list.get(i) + ",");
    }
    System.out.println();
  }
}
