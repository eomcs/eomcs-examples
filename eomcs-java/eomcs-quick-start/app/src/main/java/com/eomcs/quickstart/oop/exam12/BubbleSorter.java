package com.eomcs.quickstart.oop.exam12;

public class BubbleSorter implements Sorter {

  int[] arr;

  BubbleSorter(int[] arr) {
    this.arr = arr;
  }

  @Override
  public void sort() {
    for (int i = 0; i < arr.length - 1; i++) {
      for (int j = 0; j < arr.length - 1 - i; j++) {
        if (arr[j] > arr[j + 1]) {
          int temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
        }
      }
    }
  }
}
