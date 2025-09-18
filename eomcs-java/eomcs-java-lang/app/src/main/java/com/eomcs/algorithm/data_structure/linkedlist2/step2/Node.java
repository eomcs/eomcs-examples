package com.eomcs.algorithm.data_structure.linkedlist2.step2;

public class Node {
  Node prev;
  Object value;
  Node next;

  public Node() {}

  public Node(Object value) {
    this.value = value;
  }

  public Node(Object value, Node prev, Node next) {
    this.value = value;
    this.prev = prev;
    this.next = next;
  }
}
