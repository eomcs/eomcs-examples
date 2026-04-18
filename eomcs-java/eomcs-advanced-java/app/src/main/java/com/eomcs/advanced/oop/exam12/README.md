# Exam12 - 스태틱/논스태틱 중첩 클래스, 로컬 클래스, 익명 클래스, 람다

## 개념

`Sorter` 인터페이스를 구현하는 클래스를 다양한 방식으로 정의하는 방법을 학습한다.

- **톱레벨 클래스**: 독립적인 파일로 정의. 여러 곳에서 재사용할 때 적합하다.
- **스태틱 중첩 클래스**: 바깥 클래스 안에 `static`으로 정의. 바깥 클래스와 관련이 있지만 독립적으로 사용 가능하다.
- **논스태틱 중첩 클래스(이너 클래스)**: 클래스 내부에서 바깥 클래스의 인스턴스를 참조하는 경우, 컴파일러가 바깥 클래스의 인스턴스 주소를 전달하는 코드를 자동 생성한다. 따라서 바깥 클래스의 필드에 직접 접근할 수 있다.
- **로컬 클래스**: 로컬 클래스 내부에서 바깥 클래스의 인스턴스를 참조하거나 로컬 변수를 사용하는 경우, 컴파일러가 바깥 클래스의 인스턴스 주소나 로컬 변수의 값을 전달하는 코드를 자동 생성한다. 따라서 바깥 클래스의 인스턴스 필드에 직접 접근할 수 있으며, 로컬 변수의 값을 사용할 수 있다.
- **익명 클래스**: 로컬 클래스와 마찬가지로 동작한다.
- **람다**: 로컬 클래스와 마찬가지로 동작한다.

## Sorter - 함수형 인터페이스

```java
public interface Sorter {
  void sort();
}
```

- 추상 메서드가 `sort()` 하나뿐인 함수형 인터페이스이다.
- 람다로 대체할 수 있다.

## BubbleSorter - 톱레벨 클래스

```java
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
```

- 독립적인 파일로 정의한 `Sorter` 구현 클래스이다.
- 정렬할 배열을 생성자 파라미터로 받아 `arr` 필드에 저장한다.
- 버블 정렬 알고리즘으로 배열을 오름차순 정렬한다.

## App - 톱레벨 클래스 사용

```java
BubbleSorter sorter = new BubbleSorter(numbers);
sorter.sort();
```

- 별도 파일에 정의된 `BubbleSorter`를 `new`로 생성하여 사용한다.
- `numbers` 배열을 생성자에 전달하여 정렬한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam12.App
  ```

## App2 - 스태틱 중첩 클래스 사용

```java
public class App2 {

  static class BubbleSorter implements Sorter {
    int[] arr;

    BubbleSorter(int[] arr) {
      this.arr = arr;
    }

    @Override
    public void sort() { /* 버블 정렬 */ }
  }

  void play() {
    BubbleSorter sorter = new BubbleSorter(numbers);
    sorter.sort();
  }
}
```

- `BubbleSorter`를 `App2` 안에 `static class`로 정의한다.
- 바깥 클래스 인스턴스 없이 `new BubbleSorter(numbers)`로 생성할 수 있다.
- 정렬할 배열을 생성자 파라미터로 받아야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam12.App2
  ```

## App3 - 논스태틱 중첩 클래스(이너 클래스) 사용

```java
public class App3 {

  class BubbleSorter implements Sorter {
    @Override
    public void sort() {
      for (int i = 0; i < App3.this.numbers.length - 1; i++) {
        for (int j = 0; j < App3.this.numbers.length - 1 - i; j++) {
          if (App3.this.numbers[j] > App3.this.numbers[j + 1]) {
            int temp = App3.this.numbers[j];
            App3.this.numbers[j] = App3.this.numbers[j + 1];
            App3.this.numbers[j + 1] = temp;
          }
        }
      }
    }
  }

  void play() {
    BubbleSorter sorter = new BubbleSorter();
    sorter.sort();
  }
}
```

- `static` 없이 중첩 클래스를 정의하면 이너 클래스가 된다.
- 이너 클래스는 바깥 클래스의 인스턴스의 주소를 저장하는 필드가 자동으로 생성된다.
- 이너 클래스의 생성자는 바깥 클래스의 인스턴스 주소를 전달받을 수 있도록 파라미터가 자동으로 추가된다.
- 이너 클래스의 생성자를 호출할 때 바깥 클래스의 인스턴스 주소가 전달되는 코드로 자동 변경된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam12.App3
  ```

### 컴파일된 중첩 클래스에 추가된 필드와 생성자 확인

```bash
javap -c -classpath ./app/build/classes/java/main com.eomcs.advanced.oop.exam12.App3\$BubbleSorter
```

## App4 - 로컬 클래스 사용

```java
void play() {

  class BubbleSorter implements Sorter {
    @Override
    public void sort() {
      for (int i = 0; i < App4.this.numbers.length - 1; i++) {
        /* 버블 정렬 */
      }
    }
  }

  BubbleSorter sorter = new BubbleSorter();
  sorter.sort();
}
```

- 메서드 블록 안에 클래스를 정의한다.
- 이너 클래스와 마찬가지로 바깥 클래스의 인스턴스 주소를 저장하는 필드가 자동으로 생성된다.
- 여기에 더불어 로컬 클래스에서 바깥 메서드의 로컬 변수를 참조하는 경우, 그 로컬 변수의 값을 저장하는 필드도 자동으로 생성된다.
- 로컬 클래스의 생성자는 바깥 클래스의 인스턴스 주소와 로컬 변수의 값을 전달받을 수 있도록 파라미터가 자동으로 추가된다.
- 로컬 클래스의 생성자를 호출할 때 바깥 클래스의 인스턴스 주소와 로컬 변수의 값이 전달되는 코드로 자동 변경된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam12.App4
  ```

### 컴파일된 로컬 클래스에 추가된 필드와 생성자 확인

```bash
javap -c -classpath ./app/build/classes/java/main com.eomcs.advanced.oop.exam12.App4\$1BubbleSorter
```

## App5 - 익명 클래스 사용

```java
void play() {
  new Sorter() {
    @Override
    public void sort() {
      for (int i = 0; i < App5.this.numbers.length - 1; i++) {
        /* 버블 정렬 */
      }
    }
  }.sort();
}
```

- 클래스 이름 없이 `Sorter` 구현과 인스턴스 생성을 동시에 처리한다.
- 익명 클래스는 로컬 클래스와 마찬가지로 동작한다.
- 익명 클래스 인스턴스를 생성한 후 메서드(`.sort()`)를 바로 호출하는 것이 일반적이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam12.App5
  ```

### 컴파일된 로컬 클래스에 추가된 필드와 생성자 확인

```bash
javap -c -classpath ./app/build/classes/java/main com.eomcs.advanced.oop.exam12.App5\$1
```

## App6 - 람다 사용

```java
void play() {
  Sorter sorter = () -> {
    for (int i = 0; i < App6.this.numbers.length - 1; i++) {
      for (int j = 0; j < App6.this.numbers.length - 1 - i; j++) {
        if (App6.this.numbers[j] > App6.this.numbers[j + 1]) {
          int temp = App6.this.numbers[j];
          App6.this.numbers[j] = App6.this.numbers[j + 1];
          App6.this.numbers[j + 1] = temp;
        }
      }
    }
  };

  sorter.sort();
}
```

- `Sorter`는 추상 메서드가 `sort()` 하나뿐인 함수형 인터페이스이므로 람다로 대체할 수 있다.
- `new Sorter() { @Override public void sort() { ... } }` 대신 `() -> { ... }` 형태로 간결하게 표현한다.
- 람다도 로컬 클래스와 마찬가지로 동작한다.
- 람다 내부에서도 `App6.this.numbers`로 바깥 클래스 필드나 로컬 변수에 접근할 수 있다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam12.App6
  ```

### 컴파일된 람다에 추가된 필드와 생성자 확인

```bash
javap -c -p -v -classpath ./app/build/classes/java/main com.eomcs.advanced.oop.exam12.App6
```

## javap 옵션
- `-c`: 바이트코드 명령어 출력
- `-p`: private 멤버도 출력
- `-v`: 자세한 정보 출력 (constant pool 등)