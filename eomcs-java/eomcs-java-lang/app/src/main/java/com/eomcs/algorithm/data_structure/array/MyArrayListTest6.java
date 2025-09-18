package com.eomcs.algorithm.data_structure.array;

public class MyArrayListTest6 {

  public static void main(String[] args) {

    MyArrayList<String> list1 = new MyArrayList<>();
    list1.add("aaa"); // aaa
    list1.add("bbb"); // aaa,bbb
    list1.add("ccc"); // aaa,bbb,ccc
    list1.add("ddd"); // aaa,bbb,ccc,ddd
    list1.add("eee"); // aaa,bbb,ccc,ddd,eee
    list1.add(1, "fff"); // aaa,fff,bbb,ccc,ddd,eee
    list1.set(3, "ggg"); // aaa,fff,bbb,ggg,ddd,eee
    System.out.println("==> " + list1.remove(2)); // // aaa,fff,ggg,ddd,eee
    print2(list1);
    print3(list1);
  }

  static void print(MyArrayList<String> list) {
    for (int i = 0; i < list.size(); i++) {
      String str = list.get(i);
      System.out.print(str + ",");
    }
    System.out.println();
  }

  static void print2(MyArrayList<String> list) {
    String[] arr = new String[list.size()];
    String[] arr2 = list.toArray(arr);
    System.out.println(arr == arr2);
    for (int i = 0; i < list.size(); i++) {
      System.out.print(arr[i] + ",");
    }
    System.out.println();
  }

  static void print3(MyArrayList<String> list) {
    String[] arr = new String[1];
    String[] arr2 = list.toArray(arr);
    System.out.println(arr == arr2);
    for (int i = 0; i < list.size(); i++) {
      System.out.print(arr2[i] + ",");
    }
    System.out.println();
  }
}
