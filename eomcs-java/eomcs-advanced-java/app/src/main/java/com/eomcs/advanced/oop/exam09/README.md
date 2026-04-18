# Exam09 - 인터페이스 활용

## 개념

상속(`extends`) 대신 인터페이스(`implements`)를 사용하면 클래스 간의 결합도를 낮추고 더 유연한 구조를 만들 수 있다.

- exam08의 `QuickSorter extends Sorter`는 `Sorter` 클래스의 구현에 종속된다.
- 인터페이스로 분리하면 `BubbleSorter`와 `QuickSorter`가 서로 독립적인 클래스 계층을 가질 수 있다.
- 자바는 단일 상속만 허용하지만, 인터페이스는 여러 개를 구현할 수 있다.
- `play()`의 파라미터 타입을 `Sorter` 인터페이스로 선언하면 이를 구현한 모든 클래스의 인스턴스를 전달할 수 있다.

## Sorter - 정렬 인터페이스

```java
public interface Sorter {
  void sort(int[] arr);
}
```

- 정렬 기능의 규격(계약)을 정의한다.
- 구현 클래스는 반드시 `sort(int[] arr)`를 구현해야 한다.
- `BubbleSorter`와 `QuickSorter`를 같은 `Sorter` 타입으로 다룰 수 있게 해주는 공통 타입 역할을 한다.

## BubbleSorter - 버블 정렬 구현 클래스

```java
public class BubbleSorter implements Sorter {

  @Override
  public void sort(int[] arr) {
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

- `implements Sorter`로 `Sorter` 인터페이스를 구현한다.
- exam08의 `Sorter` 클래스에서 이름만 `BubbleSorter`로 변경하고 `implements Sorter`를 추가한다.
- 시간 복잡도: 평균/최악 O(n²)

## QuickSorter - 퀵 정렬 구현 클래스

```java
public class QuickSorter implements Sorter {

  @Override
  public void sort(int[] arr) {
    sort(arr, 0, arr.length - 1);
  }

  private void sort(int[] arr, int low, int high) { ... }

  private int partition(int[] arr, int low, int high) { ... }
}
```

- `extends Sorter` 대신 `implements Sorter`로 변경한다.
- `BubbleSorter`와 상속 관계 없이 독립적인 클래스이지만 `Sorter` 타입으로 다룰 수 있다.
- 시간 복잡도: 평균 O(n log n), 최악 O(n²)

## App - 인터페이스 타입 파라미터로 통합한 클래스

`play()`의 파라미터 타입을 `Sorter` 인터페이스로 선언하여 구현 클래스에 무관하게 동작한다.

```java
static void play(int[] arr, Sorter sorter) {
  // 정렬 전 출력 ...

  sorter.sort(arr);

  // 정렬 후 출력 ...
}

public static void main(String[] args) {
  // 배열 생성 ...

  System.out.println("[버블 정렬]");
  play(numbers.clone(), new BubbleSorter());

  System.out.println("[퀵 정렬]");
  play(numbers.clone(), new QuickSorter());
}
```

- `play()`는 `Sorter` 인터페이스만 알면 되고, 어떤 구현체가 전달되는지 알 필요가 없다.
- `sorter.sort(arr)` 호출 시 실제 인스턴스의 `sort()`가 실행된다. (**다형성**)
- 새로운 정렬 알고리즘을 추가할 때 `Sorter`를 구현하는 클래스만 만들면 `play()` 코드는 변경할 필요가 없다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam09.App
  ```
