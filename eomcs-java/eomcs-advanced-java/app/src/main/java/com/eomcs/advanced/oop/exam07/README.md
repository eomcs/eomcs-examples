# Exam07 - 인스턴스 메서드 호출

## 개념

인스턴스 메서드는 객체를 통해 호출하기 때문에 메서드 호출에 필요한 객체를 파라미터로 전달할 수 있다.

- exam06의 스태틱 메서드는 클래스 이름이 코드에 직접 박혀 있어 교체가 어렵다.
- 인스턴스 메서드로 변경하면 `new Sorter()` 또는 `new QuickSorter()`처럼 생성할 인스턴스만 바꿔 다른 구현체를 손쉽게 전달할 수 있다. (단, 상속이나 인터페이스를 사용한다면)

## Sorter - 버블 정렬 클래스 (인스턴스 메서드)

```java
public class Sorter {

  void sort(int[] arr) {
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

- exam06의 `static void sort()`에서 `void sort()`로 변경하여 인스턴스 메서드로 선언한다.
- 시간 복잡도: 평균/최악 O(n²)

## QuickSorter - 퀵 정렬 클래스 (인스턴스 메서드)

```java
public class QuickSorter {

  void sort(int[] arr) {
    sort(arr, 0, arr.length - 1);
  }

  private void sort(int[] arr, int low, int high) { ... }

  private int partition(int[] arr, int low, int high) { ... }
}
```

- exam06의 `private static` 메서드들을 `private` 인스턴스 메서드로 변경한다.
- 시간 복잡도: 평균 O(n log n), 최악 O(n²)

## App - Sorter를 내부에서 생성하여 사용하는 클래스

`play()` 내부에서 `Sorter` 인스턴스를 직접 생성하여 버블 정렬한다.

```java
static void play(int[] arr) {
  // 정렬 전 출력 ...

  Sorter sorter = new Sorter();
  sorter.sort(arr);

  // 정렬 후 출력 ...
}
```

- `play()` 안에 `Sorter` 클래스 이름이 박혀 있으므로 다른 정렬 알고리즘으로 교체하려면 `play()` 코드를 수정해야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam07.App
  ```

## App2 - QuickSorter를 내부에서 생성하여 사용하는 클래스

`play()` 내부에서 `QuickSorter` 인스턴스를 직접 생성하여 퀵 정렬한다.

```java
static void play(int[] arr) {
  // 정렬 전 출력 ...

  QuickSorter sorter = new QuickSorter();
  sorter.sort(arr);

  // 정렬 후 출력 ...
}
```

- App과 구조는 동일하나 `Sorter` 대신 `QuickSorter`를 사용한다.
- `play()` 내부에 클래스 이름이 박혀 있어 알고리즘 교체 시 코드 수정이 필요하다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam07.App2
  ```

## App3 - Sorter 인스턴스를 파라미터로 받는 클래스

`play()`가 `Sorter` 인스턴스를 파라미터로 전달받아 버블 정렬한다.

```java
static void play(int[] arr, Sorter sorter) {
  // 정렬 전 출력 ...

  sorter.sort(arr);

  // 정렬 후 출력 ...
}

public static void main(String[] args) {
  // 배열 생성 ...
  play(numbers, new Sorter());
}
```

- `play()` 내부에서 인스턴스를 생성하지 않고 외부에서 주입받으므로 `main()`에서 전달할 인스턴스만 바꾸면 된다.
- 단, 파라미터 타입이 `Sorter`로 고정되어 있어 `QuickSorter`를 전달하려면 파라미터 타입도 변경해야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam07.App3
  ```

## App4 - QuickSorter 인스턴스를 파라미터로 받는 클래스

`play()`가 `QuickSorter` 인스턴스를 파라미터로 전달받아 퀵 정렬한다.

```java
static void play(int[] arr, QuickSorter sorter) {
  // 정렬 전 출력 ...

  sorter.sort(arr);

  // 정렬 후 출력 ...
}

public static void main(String[] args) {
  // 배열 생성 ...
  play(numbers, new QuickSorter());
}
```

- App3와 구조는 동일하나 파라미터 타입이 `QuickSorter`로 고정되어 있다.
- App3와 App4는 파라미터 타입만 다를 뿐 `play()` 코드가 중복된다. 공통 타입(인터페이스 또는 상위 클래스)이 있다면 하나의 `play()`로 통합할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam07.App4
  ```
