# Exam01 - Iterable과 Iterator

## 개념

컬렉션의 항목을 순서대로 꺼내는 방법을 표준화하기 위해 두 가지 인터페이스를 사용한다.

| 인터페이스 | 패키지 | 메서드 | 설명 |
|---|---|---|---|
| `Iterable<T>` | `java.lang` | `Iterator<T> iterator()` | for-each 문 사용 가능 조건 |
| `Iterator<T>` | `java.util` | `boolean hasNext()` | 꺼낼 항목이 남아있으면 `true` 반환 |
| | | `T next()` | 다음 항목을 반환하고 커서를 이동 |
| | | `void remove()` | `next()`로 마지막 반환한 항목을 제거 (선택적 구현) |

- `Iterable<T>`는 `java.lang` 패키지에 소속되어 있어 `import` 없이 사용할 수 있다.
- 클래스가 `Iterable<T>`를 구현하면 for-each 문(향상된 for 문)으로 순회할 수 있다.
- for-each 문은 컴파일러가 내부적으로 `iterator()` / `hasNext()` / `next()` 호출하는 while 문으로 변환한다.

### Iterator 디자인 패턴

Iterator 패턴은 컬렉션에서 항목을 꺼내는 방법을 표준화하는 디자인 패턴이다.

- **내부 구조 은닉**: 사용자는 컬렉션이 배열인지, 연결 리스트인지 알 필요 없이 동일한 방법(`hasNext()` / `next()`)으로 순회할 수 있다.
- **역할 분리**: `Iterable`은 "Iterator를 제공하는 컬렉션"의 역할을, `Iterator`는 "현재 순회 위치(커서)를 관리하는 객체"의 역할을 각각 담당한다.
- **독립적인 커서**: `iterator()`를 호출할 때마다 새로운 `Iterator` 객체가 생성되므로, 같은 컬렉션을 여러 곳에서 동시에 독립적으로 순회할 수 있다.

## App - Iterable과 Iterator 직접 구현

```java
class IntList implements Iterable<Integer> {

  private int[] data;
  private int size;

  public IntList(int capacity) {
    data = new int[capacity];
  }

  public void add(int value) {
    if (size < data.length) {
      data[size++] = value;
    }
  }

  @Override
  public Iterator<Integer> iterator() {
    return new Iterator<Integer>() {
      int cursor = 0;

      @Override
      public boolean hasNext() {
        return cursor < size;
      }

      @Override
      public Integer next() {
        return data[cursor++];
      }
    };
  }
}

public static void main(String[] args) {
  IntList list = new IntList(5);
  list.add(10);
  list.add(20);
  list.add(30);
  list.add(40);
  list.add(50);

  // Iterator를 직접 사용하여 순회
  Iterator<Integer> it = list.iterator();
  while (it.hasNext()) {
    int value = it.next();
    System.out.println(value);
  }

  // for-each 문으로 순회 (컴파일러가 위의 while 문으로 변환)
  for (int value : list) {
    System.out.println(value);
  }
}
```

- `IntList`는 `Iterable<Integer>`를 구현하므로 for-each 문으로 순회할 수 있다.
- `iterator()` 메서드는 익명 클래스로 `Iterator<Integer>`를 구현하여 반환한다.
- `cursor` 필드로 현재 순회 위치를 추적하며, `hasNext()`는 남은 항목 여부를, `next()`는 현재 항목을 반환한 뒤 커서를 이동한다.
- for-each 문은 `Iterator`를 직접 사용하는 while 문과 동일하게 동작한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam01.App
  ```
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam01.App
  ```