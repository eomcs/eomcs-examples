# Exam06 - 스태틱 메서드의 한계

## 개념

스태틱 메서드는 클래스 이름으로 호출하기 때문에 교체하기 힘들다.

- `Sorter.sort(arr)`처럼 클래스 이름이 코드에 직접 박혀 있으므로 다른 정렬 알고리즘으로 교체하려면 호출하는 코드를 모두 수정해야 한다.
- 반면 인스턴스 메서드로 구현하면 인스턴스 변수만 교체하여 다른 구현체로 쉽게 바꿀 수 있다.

## Sorter - 버블 정렬 클래스

인접한 두 요소를 비교하여 교환하는 버블 정렬(Bubble Sort)을 구현한다.

```java
public class Sorter {

  static void sort(int[] arr) {
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
```

- 한 번의 내부 루프가 끝나면 가장 큰 값이 맨 뒤로 이동한다.
- 외부 루프가 반복될수록 정렬된 요소가 뒤에서 확정되므로 내부 루프 범위를 `arr.length - 1 - i`로 줄인다.
- 시간 복잡도: 평균/최악 O(n²)

## QuickSorter - 퀵 정렬 클래스

피벗을 기준으로 배열을 분할하는 퀵 정렬(Quick Sort)을 재귀로 구현한다.

```java
public class QuickSorter {

  static void sort(int[] arr) {
    sort(arr, 0, arr.length - 1);
  }

  private static void sort(int[] arr, int low, int high) {
    if (low >= high) {
      return;
    }
    int pivotIndex = partition(arr, low, high);
    sort(arr, low, pivotIndex - 1);
    sort(arr, pivotIndex + 1, high);
  }

  private static int partition(int[] arr, int low, int high) {
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
```

- 마지막 요소를 피벗으로 선택한다.
- `partition()`은 피벗보다 작은 요소를 왼쪽, 큰 요소를 오른쪽으로 분리하고 피벗의 최종 인덱스를 반환한다.
- 피벗 기준 왼쪽/오른쪽 부분 배열에 대해 재귀 호출한다.
- 시간 복잡도: 평균 O(n log n), 최악 O(n²)

## App - Sorter(버블 정렬)를 사용하는 클래스

1~100 사이의 난수 20개를 생성하고 `Sorter.sort()`로 정렬한다.

```java
static void play(int[] arr) {
  System.out.print("정렬 전: ");
  for (int n : arr) {
    System.out.printf("%d ", n);
  }
  System.out.println();

  Sorter.sort(arr);

  System.out.print("정렬 후: ");
  for (int n : arr) {
    System.out.printf("%d ", n);
  }
  System.out.println();
}
```

- 정렬 전/후 배열을 출력하여 결과를 확인한다.
- `play()` 스태틱 메서드로 출력과 정렬 로직을 분리한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam06.App
  ```

## App2 - QuickSorter(퀵 정렬)를 사용하는 클래스

App과 동일한 구조이나 `Sorter.sort()` 대신 `QuickSorter.sort()`를 사용한다.

```java
QuickSorter.sort(arr);
```

- 정렬 알고리즘을 교체하려면 호출 코드(`Sorter.sort` → `QuickSorter.sort`)를 직접 수정해야 한다.
- 스태틱 메서드 방식의 한계: 호출하는 쪽의 코드가 구체적인 클래스에 종속된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam06.App2
  ```
