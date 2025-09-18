package com.eomcs.oop.ex07.step.s4;

// 기존의 정렬 클래스처럼 동일한 방법으로 사용하려면
// Sorter를 상속 받아 같은 타입으로 묶여야 한다.
// 그래서 Sorter 를 상속 받았다.
//
public class MergeSort extends Sorter {

  // Sorter에서 상속 받은 메서드가 추상 메서드이기 때문에
  // 이 서브 클래스는 반드시 구현해야 한다.
  // 구현하지 않으면 이 클래스도 추상 클래스가 될 수 밖에 없다.
  @Override
  public void sort(int[] values) {
    sort(values, 0, values.length - 1);
  }

  //Merges two subarrays of arr[].
  // First subarray is arr[l..m]
  // Second subarray is arr[m+1..r]
  void merge(int arr[], int l, int m, int r)
  {
    // Find sizes of two subarrays to be merged
    int n1 = m - l + 1;
    int n2 = r - m;

    /* Create temp arrays */
    int L[] = new int [n1];
    int R[] = new int [n2];

    /*Copy data to temp arrays*/
    for (int i=0; i<n1; ++i)
      L[i] = arr[l + i];
    for (int j=0; j<n2; ++j)
      R[j] = arr[m + 1+ j];


    /* Merge the temp arrays */

    // Initial indexes of first and second subarrays
    int i = 0, j = 0;

    // Initial index of merged subarry array
    int k = l;
    while (i < n1 && j < n2)
    {
      if (L[i] <= R[j])
      {
        arr[k] = L[i];
        i++;
      }
      else
      {
        arr[k] = R[j];
        j++;
      }
      k++;
    }

    /* Copy remaining elements of L[] if any */
    while (i < n1)
    {
      arr[k] = L[i];
      i++;
      k++;
    }

    /* Copy remaining elements of R[] if any */
    while (j < n2)
    {
      arr[k] = R[j];
      j++;
      k++;
    }
  }

  // Main function that sorts arr[l..r] using
  // merge()
  void sort(int arr[], int l, int r)
  {
    if (l < r)
    {
      // Find the middle point
      int m = (l+r)/2;

      // Sort first and second halves
      sort(arr, l, m);
      sort(arr , m+1, r);

      // Merge the sorted halves
      merge(arr, l, m, r);
    }
  }
}
