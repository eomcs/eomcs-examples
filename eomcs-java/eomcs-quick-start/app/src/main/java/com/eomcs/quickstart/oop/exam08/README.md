# Exam08 - 인스턴스 메서드 호출 + 상속 + 다형성

## 개념

상속을 사용하면 두 클래스를 같은 타입으로 묶어서 다룰 수 있다. 즉 자식 클래스는 부모 클래스 타입으로 다룰 수 있기 때문에, 파라미터 타입을 부모 클래스로 선언하면 자식 클래스 인스턴스도 전달할 수 있다. 즉 메서드 교체가 쉬워진다.

- exam07의 App3와 App4는 `play(Sorter sorter)`, `play(QuickSorter sorter)`로 파라미터 타입만 다른 중복 코드가 존재했다.
- `QuickSorter extends Sorter`로 선언하면 `QuickSorter`는 `Sorter` 타입으로 다룰 수 있다.
- `play(Sorter sorter)` 하나로 `Sorter`와 `QuickSorter` 인스턴스를 모두 받을 수 있어 코드 중복이 사라진다.
- 메서드 호출 시 실제 인스턴스의 `sort()`가 실행된다. 이를 **다형성(Polymorphism)** 이라 한다.

## Sorter - 버블 정렬 클래스 (부모 클래스)

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

- 버블 정렬을 구현한 `sort()` 인스턴스 메서드를 제공한다.
- `QuickSorter`의 부모 클래스로, `Sorter` 타입 변수에 `QuickSorter` 인스턴스를 담을 수 있다.

## QuickSorter - 퀵 정렬 클래스 (자식 클래스)

```java
public class QuickSorter extends Sorter {

  @Override
  void sort(int[] arr) {
    sort(arr, 0, arr.length - 1);
  }

  private void sort(int[] arr, int low, int high) { ... }

  private int partition(int[] arr, int low, int high) { ... }
}
```

- `extends Sorter`로 `Sorter`를 상속한다.
- `@Override`로 부모의 `sort()`를 퀵 정렬로 재정의한다.
- `QuickSorter` 인스턴스를 `Sorter` 타입 변수에 담을 수 있다.

## App - 부모 클래스 타입 파라미터로 통합한 클래스

`play()`의 파라미터 타입을 `Sorter`로 선언하여 `Sorter`와 `QuickSorter` 인스턴스를 모두 전달받는다.

```java
static void play(int[] arr, Sorter sorter) {
  // 정렬 전 출력 ...

  sorter.sort(arr);

  // 정렬 후 출력 ...
}

public static void main(String[] args) {
  // 배열 생성 ...

  System.out.println("[버블 정렬]");
  play(numbers.clone(), new Sorter());

  System.out.println("[퀵 정렬]");
  play(numbers.clone(), new QuickSorter());
}
```

- `play()`는 `Sorter` 타입만 알면 되고, 실제로 어떤 정렬 알고리즘이 실행될지는 전달된 인스턴스에 따라 결정된다.
- `sorter.sort(arr)` 호출 시 `new Sorter()`를 전달하면 버블 정렬이, `new QuickSorter()`를 전달하면 퀵 정렬이 실행된다.
- `numbers.clone()`으로 동일한 배열을 복사하여 두 알고리즘의 결과를 비교할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.quickstart.oop.exam08.App
  ```
