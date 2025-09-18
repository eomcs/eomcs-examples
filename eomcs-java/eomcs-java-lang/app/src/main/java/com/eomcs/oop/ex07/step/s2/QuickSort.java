package com.eomcs.oop.ex07.step.s2;

public class QuickSort extends Sorter {

  @Override
  public void sort(int[] values) {
    start(values, 0, values.length - 1);
  }

  private int partition(int[] arr, int low, int high)
  {
    int pivot = arr[high];
    int i = (low-1); // index of smaller element
    for (int j=low; j<high; j++)
    {
      // If current element is smaller than or
      // equal to pivot
      if (arr[j] <= pivot)
      {
        i++;

        // swap arr[i] and arr[j]
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
      }
    }

    // swap arr[i+1] and arr[high] (or pivot)
    int temp = arr[i+1];
    arr[i+1] = arr[high];
    arr[high] = temp;

    return i+1;
  }

  private void start(int[] arr, int low, int high) {
    if (low < high)
    {
      /* pi is partitioning index, arr[pi] is
          now at right place */
      int pi = partition(arr, low, high);

      // Recursively sort elements before
      // partition and after partition
      start(arr, low, pi-1);
      start(arr, pi+1, high);
    }
  }
}
