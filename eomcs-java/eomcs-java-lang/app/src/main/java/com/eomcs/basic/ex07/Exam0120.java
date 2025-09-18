// java.util.HashSet vs ArrayList
package com.eomcs.basic.ex07;

import java.util.ArrayList;

// ArrayList는 중복을 허용한다.
public class Exam0120 {
  public static void main(String[] args) {

    ArrayList<String> list = new ArrayList<>();

    list.add("aaa");
    list.add("bbb");
    list.add("ccc");

    // ArrayList는 값을 중복해서 넣을 수 있다.
    list.add("aaa"); // 위에서 입력한 "aaa"와 같은 인스턴스라도 중복 저장.
    list.add("bbb"); // 위에서 입력한 "bbb"와 같은 인스턴스라도 중복 저장.

    // null을 넣을 수 있다.
    // 여러 번 넣을 수 있다.
    list.add(null);
    list.add(null);

    System.out.println(list);
  }
}









