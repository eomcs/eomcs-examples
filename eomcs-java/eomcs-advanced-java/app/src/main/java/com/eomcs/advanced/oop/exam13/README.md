# Exam13 - 제네릭

## 개념

타입별로 별도의 클래스를 만들지 않고 다양한 타입을 처리하는 방법을 학습한다.

- **타입별 클래스**: 타입마다 별도의 클래스를 정의한다. 코드가 중복되고 타입이 늘어날수록 클래스도 늘어난다.
- **Object 타입 사용**: 필드를 `Object` 타입으로 선언하면 모든 타입을 저장할 수 있다. 단, 꺼낼 때 형변환이 필요하며 잘못된 형변환은 런타임 오류를 발생시킨다.
- **제네릭(Generic)**: 타입 파라미터(`<T>`)를 사용하여 클래스 정의 시 타입을 지정하지 않고, 인스턴스 생성 시 타입을 지정한다. 형변환이 필요 없고 타입 오류를 컴파일 시점에 잡을 수 있다.

## App - 타입별 클래스 사용

```java
class StringBox {
  String value;
  void set(String value) { this.value = value; }
  String get() { return value; }
}

class IntBox {
  int value;
  void set(int value) { this.value = value; }
  int get() { return value; }
}

class FloatBox {
  float value;
  void set(float value) { this.value = value; }
  float get() { return value; }
}
```

```java
StringBox strBox = new StringBox();
strBox.set("Hello");
String strValue = strBox.get();

IntBox intBox = new IntBox();
intBox.set(100);
int intValue = intBox.get();

FloatBox floatBox = new FloatBox();
floatBox.set(3.14f);
float floatValue = floatBox.get();
```

- 타입마다 별도의 클래스(`StringBox`, `IntBox`, `FloatBox`)를 정의한다.
- 각 클래스의 구조가 동일하므로 코드가 중복된다.
- 새로운 타입이 필요할 때마다 클래스를 추가해야 한다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam13.App
  ```

## App2 - Object 타입 사용

```java
class ObjectBox {
  Object value;
  void set(Object value) { this.value = value; }
  Object get() { return value; }
}
```

```java
ObjectBox strBox = new ObjectBox();
strBox.set("Hello");
String strValue = (String) strBox.get();

ObjectBox intBox = new ObjectBox();
intBox.set(100);
int intValue = (int) intBox.get();

ObjectBox floatBox = new ObjectBox();
floatBox.set(3.14f);
float floatValue = (float) floatBox.get();
```

- 필드 타입을 `Object`로 선언하여 모든 타입을 하나의 클래스로 처리한다.
- `get()` 호출 시 원래 타입으로 **형변환(캐스팅)** 이 필요하다.
- `int`, `float` 같은 기본 타입은 저장 시 자동으로 **박싱(boxing)** 되고, 꺼낼 때 **언박싱(unboxing)** 된다.
- 잘못된 형변환은 컴파일 시점이 아닌 **런타임에 `ClassCastException`** 을 발생시킨다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam13.App2
  ```

## App3 - 제네릭 사용

```java
class GenericBox<T> {
  T value;
  void set(T value) { this.value = value; }
  T get() { return value; }
}
```

```java
GenericBox<String> strBox = new GenericBox<>();
strBox.set("Hello");
String strValue = strBox.get();

GenericBox<Integer> intBox = new GenericBox<>();
intBox.set(100);
int intValue = intBox.get();

GenericBox<Float> floatBox = new GenericBox<>();
floatBox.set(3.14f);
float floatValue = floatBox.get();
```

- 타입 파라미터 `<T>`를 사용하여 클래스를 정의한다.
- 인스턴스 생성 시 `<String>`, `<Integer>`, `<Float>`처럼 실제 타입을 지정한다.
- `get()` 호출 시 **형변환이 필요 없다**.
- 잘못된 타입을 넣으면 **컴파일 시점에 오류**가 발생한다.
- 제네릭의 타입 파라미터에는 `int`, `float` 같은 **기본 타입(primitive type)을 직접 지정할 수 없다**. 대신 `Integer`, `Float` 같은 **래퍼 클래스(Wrapper Class)** 를 사용한다. 값을 넣고 꺼낼 때는 오토박싱/언박싱이 자동으로 처리된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.oop.exam13.App3
  ```
