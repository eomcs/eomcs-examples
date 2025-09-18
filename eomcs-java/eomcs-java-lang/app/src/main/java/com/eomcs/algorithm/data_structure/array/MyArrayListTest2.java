package com.eomcs.algorithm.data_structure.array;

public class MyArrayListTest2 {

  public static void main(String[] args) {
    MyArrayList.add("aaa"); // aaa
    print();
    MyArrayList.add("bbb"); // aaa,bbb
    print();
    MyArrayList.add("ccc"); // aaa,bbb,ccc
    print();
    MyArrayList.add("ddd"); // aaa,bbb,ccc,ddd
    print();
    MyArrayList.add("eee"); // aaa,bbb,ccc,ddd,eee
    print();
    MyArrayList.add("fff"); // aaa,bbb,ccc,ddd,eee,fff
    print();
    MyArrayList.add("ggg"); // aaa,bbb,ccc,ddd,eee,fff,ggg
    print();
    MyArrayList.add("hhh"); // aaa,bbb,ccc,ddd,eee,fff,ggg,hhh
    print();

    MyArrayList.add(8, "iii"); // aaa,bbb,ccc,ddd,eee,fff,ggg,hhh,iii
    print();

    Object old = MyArrayList.remove(5); // aaa,bbb,ccc,ddd,eee,ggg,hhh,iii
    print();
    System.out.println(old);

    MyArrayList.set(7, "xxx");
    print();

    System.out.println(MyArrayList.get(7));

  }

  static void print() {
    for (int i = 0; i < MyArrayList.size; i++) {
      String str = (String) MyArrayList.get(i);
      System.out.print(str + ",");
    }
    System.out.println();
  }
}
