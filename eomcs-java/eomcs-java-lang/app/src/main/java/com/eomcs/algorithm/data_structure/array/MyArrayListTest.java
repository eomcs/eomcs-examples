package com.eomcs.algorithm.data_structure.array;

public class MyArrayListTest {

  public static void main(String[] args) {
    MyArrayList.add("aaa"); // aaa
    MyArrayList.add("bbb"); // aaa,bbb
    MyArrayList.add("ccc"); // aaa,bbb,ccc
    print();

    MyArrayList.add(1, "xxx"); // aaa,xxx,bbb,ccc
    print();

    Object old = MyArrayList.set(2, "yyy"); // aaa,xxx,yyy,ccc
    print();
    System.out.println(old);

    old = MyArrayList.remove(1);
    print();
    System.out.println(old);
  }

  static void print() {
    for (int i = 0; i < MyArrayList.size; i++) {
      String str = (String) MyArrayList.get(i);
      System.out.print(str + ",");
    }
    System.out.println();
  }
}
