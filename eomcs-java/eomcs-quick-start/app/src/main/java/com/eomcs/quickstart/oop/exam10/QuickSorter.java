package com.eomcs.quickstart.oop.exam10;

public class QuickSorter extends AbstractSorter {

  QuickSorter(int[] arr) {
    super(arr);
  }

  @Override
  public void sort() {
    sort(0, arr.length - 1);
  }

  private void sort(int low, int high) {
    if (low >= high) {
      return;
    }
    int pivotIndex = partition(low, high);
    sort(low, pivotIndex - 1);
    sort(pivotIndex + 1, high);
  }

  private int partition(int low, int high) {
    int pivot = arr[high];
    int i = low - 1;

    for (int j = low; j < high; j++) {
      if (arr[j] <= pivot) {
        i++;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
      }
    }

    int temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;

    return i + 1;
  }
}
