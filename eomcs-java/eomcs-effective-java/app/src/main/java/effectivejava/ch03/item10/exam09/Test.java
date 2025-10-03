// # 아이템 10. equals() 는 일반 규약을 지켜 재정의하라
// [재정의하지 말아야 할 상황]
// - 각 인스턴스가 본질적으로 고유하다.
//   - 값을 표현하는 게 아니라 동작하는 객체를 표현하는 클래스인 경우
// - 인스턴스의 '논리적 동치성(logical equality)'을 검사할 일이 없다.
// - 상위 클래스에서 재정의한 equals()가 하위 클래스에도 딱 들어 맞는다.
// - 클래스가 private이거나, package-private이고 equals() 메서드를 호출할 일이 없다.
//
// [equals() 메서드는 동치관계(equivalence relation)를 구현하며, 다음을 만족한다.]
// 1. 반사성(reflexive):
//    - null 이 아닌 모든 참조 값 x에 대해,
//      x.equals(x)는 true여야 한다.
// 2. 대칭성(symmetric):
//    - null 이 아닌 모든 참조 값 x, y에 대해,
//      x.equals(y)가 true이면, y.equals(x)도 true여야 한다.
// 3. 추이성(transitive):
//    - null 이 아닌 모든 참조 값 x, y, z에 대해,
//      x.equals(y)가 true이고, y.equals(z)가 true이면, x.equals(z)도 true여야 한다.
// 4. 일관성(consistency):
//    - null 이 아닌 모든 참조 값 x, y에 대해,
//      x.equals(y)를 반복해서 호출해도 항상 같은 결과를 반환해야 한다.
// 5. null-아님:
//    - null 이 아닌 모든 참조 값 x에 대해,
//      x.equals(null)는 false여야 한다.

package effectivejava.ch03.item10.exam09;

// equals()의 null-아님 원칙

class MyObject {
  private final int value;

  public MyObject(int value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    // 비교하려는 객체가 null 인지 여부를 검사한다.
    if (o == null) return false;
    if (!(o instanceof MyObject)) return false;
    MyObject that = (MyObject) o;
    return this.value == that.value;
  }
}

// 다음은 개선된 MyObject 클래스이다.
class MyObject2 {
  private final int value;

  public MyObject2(int value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    // 굳이 null 검사를 할 필요가 없다. instanceof가 null 검사를 대신해 주기 때문이다.
    if (!(o instanceof MyObject2)) return false;
    MyObject2 that = (MyObject2) o;
    return this.value == that.value;
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    MyObject obj1 = new MyObject(100);
    MyObject obj2 = new MyObject(100);

    System.out.println(obj1.equals(obj2)); // true
    System.out.println(obj1.equals(null)); // false

    MyObject2 obj3 = new MyObject2(200);
    MyObject2 obj4 = new MyObject2(200);

    System.out.println(obj3.equals(obj4)); // true
    System.out.println(obj3.equals(null)); // false
  }
}
