package com.eomcs.oop.ex07.step.s3;

// 기존의 정렬 클래스처럼 동일한 방법으로 사용하려면
// Sorter를 상속 받아 같은 타입으로 묶어야 한다.
// 그래서 Sorter 를 상속 받았다.
//
public class MergeSort extends Sorter {

  // Sorter 클래스를 상속 받았지만
  // Sorter 클래스의 sort() 메서드를 재정의하지 않았다.
  // 아래의 sort(int[], int, int) 메서드는
  // 내부적으로 사용하기 위해 추가한 메서드이다.
  // 이렇게 Sorter를 상속 받아 서브 클래스를 만드는 것에는 문제가 없지만,
  // 이 클래스를 사용하는 쪽에서는 문제가 발생할 것이다!
  // 왜?
  // 이 클래스를 사용하는 쪽에서는 Sorter 에서 정의한
  // sort()라는 메서드가 정렬을 수행할 것이라고 믿기 때문이다.
  //

  // Merges two subarrays of arr[].
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
