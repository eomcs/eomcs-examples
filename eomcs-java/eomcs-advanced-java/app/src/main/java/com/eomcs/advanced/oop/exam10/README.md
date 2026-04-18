# Exam10 - 추상 클래스의 용도

## 개념

추상 클래스를 사용하면 여러 구현 클래스의 공통 필드와 기능을 한 곳에 모아 코드 중복을 줄일 수 있다.

- `BubbleSorter`와 `QuickSorter`는 정렬 대상 배열(`arr`)과 `toString()`이 동일하다.
- 공통 부분을 `AbstractSorter` 추상 클래스에 정의하면 각 구현 클래스는 정렬 로직(`sort()`)만 작성하면 된다.
- `AbstractSorter`는 `Sorter` 인터페이스를 구현하므로 `BubbleSorter`와 `QuickSorter`도 `Sorter` 타입으로 다룰 수 있다.

## Sorter - 정렬 인터페이스

```java
public interface Sorter {
  void sort();
}
```

- exam09와 달리 `sort()` 메서드에 파라미터가 없다.
- 정렬 대상 배열은 `AbstractSorter`의 필드로 보관하므로 파라미터로 전달할 필요가 없다.

## AbstractSorter - 공통 기능을 담은 추상 클래스

```java
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
```

- `int[] arr` 필드에 정렬 대상 배열을 저장한다.
- 생성자에서 배열을 전달받아 `this.arr`에 저장한다.
- `toString()`을 오버라이딩하여 배열을 `[100,200,300]` 형식으로 출력한다.
- `sort()`는 구현 클래스마다 다르므로 추상 메서드로 남겨둔다(`Sorter` 인터페이스에 선언).

## BubbleSorter - 버블 정렬 구현 클래스

```java
public class BubbleSorter extends AbstractSorter {

  BubbleSorter(int[] arr) {
    super(arr);
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
```

- `super(arr)`로 부모 생성자를 호출하여 배열을 전달한다.
- `sort()`만 구현하면 되고, `arr` 필드와 `toString()`은 `AbstractSorter`에서 상속받는다.
- 시간 복잡도: 평균/최악 O(n²)

## QuickSorter - 퀵 정렬 구현 클래스

```java
public class QuickSorter extends AbstractSorter {

  QuickSorter(int[] arr) {
    super(arr);
  }

  @Override
  public void sort() {
    sort(0, arr.length - 1);
  }

  private void sort(int low, int high) { ... }

  private int partition(int low, int high) { ... }
}
```

- `super(arr)`로 부모 생성자를 호출하여 배열을 전달한다.
- 내부 메서드에서 `arr` 필드를 파라미터 없이 직접 사용한다.
- 시간 복잡도: 평균 O(n log n), 최악 O(n²)

## App - AbstractSorter 타입 파라미터로 통합한 클래스

`play()`의 파라미터 타입을 `AbstractSorter`로 선언하여 `arr` 필드와 `toString()`을 활용한다.

```java
static void play(AbstractSorter sorter) {
  System.out.println("정렬 전: " + sorter);
  sorter.sort();
  System.out.println("정렬 후: " + sorter);
}

public static void main(String[] args) {
  // 배열 생성 ...

  System.out.println("[버블 정렬]");
  play(new BubbleSorter(numbers.clone()));

  System.out.println("[퀵 정렬]");
  play(new QuickSorter(numbers.clone()));
}
```

- `"정렬 전: " + sorter`처럼 문자열 연결 시 `toString()`이 자동 호출되어 `[100,200,300]` 형식으로 출력된다.
- `sorter.sort()` 호출 시 실제 인스턴스의 `sort()`가 실행된다. (**다형성**)
- `numbers.clone()`으로 동일한 배열을 복사하여 두 알고리즘에 같은 데이터를 전달한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam10.App
  ```
