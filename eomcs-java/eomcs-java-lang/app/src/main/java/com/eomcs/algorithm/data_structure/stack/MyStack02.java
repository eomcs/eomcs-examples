package com.eomcs.algorithm.data_structure.stack;

import com.eomcs.algorithm.data_structure.linkedlist.MyLinkedList;

// 1) Stack 을 구현하기 위해 기존에 작성한 MyLinkedList를 상속 받는다.
// 2) 스택에 값을 추가하는 push() 메서드를 정의한다.
public class MyStack02 extends MyLinkedList {

  public Object push(Object item) {
    add(item);
    return item;
  }
}
