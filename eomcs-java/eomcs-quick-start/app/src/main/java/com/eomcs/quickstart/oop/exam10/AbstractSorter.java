package com.eomcs.quickstart.oop.exam10;

public abstract class AbstractSorter implements Sorter {
  int[] arr;

  AbstractSorter(int[] arr) {
    this.arr = arr;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < arr.length; i++) {
      if (i > 0) {
        sb.append(",");
      }
      sb.append(arr[i]);
    }
    sb.append("]");
    return sb.toString();
  }
}
