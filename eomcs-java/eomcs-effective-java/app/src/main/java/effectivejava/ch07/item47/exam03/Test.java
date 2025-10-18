// # 아이템 47. 반환 타입으로 스트림보다 컬렉션이 낫다
// [원소 시퀀스; 일련의 원소를 반환하는 메서드]
// - 목록을 다룰 때 기존 방식:
//   1) 기본: 컬렉션 인터페이스 사용
//   2) Collection 메서드를 구현할 수 없을 때: Iterable 사용
//   3) 기본 타입이거나 성능에 민감한 상황일 때: 배열 사용.
// [스트림 등장 후]
// - for-each 루프에 직접사용할 수 없는 불편함이 있다.
//   왜? Stream은 Iterable의 하위 타입이 아니기 때문이다.
// - 해결책? 어댑터 메서드를 만들어 Iterable 객체로 변환한 후 사용하면 된다.
//
package effectivejava.ch07.item47.exam03;

// [주제] 컬렉션 반환하기: AbstractList를 활용하여 전용 컬렉션 구현하기

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 입력 집합의 멱집합 만들기
class PowerSet {

  public static final <E> List<Set<E>> of(Set<E> s) {
    // 집합에 들어 있는 값을 인덱스로 접근하기 위해 리스트로 복사한다.
    List<E> src = new ArrayList<>(s);
    if (src.size() > 30) {
      throw new IllegalArgumentException("원소가 너무 많다(최대 30개): " + s);
    }

    return new AbstractList<Set<E>>() {
      @Override
      public int size() {
        return 1 << src.size(); // 멱집합의 원소 개수 = 2^n
      }

      @Override
      public boolean contains(Object o) {
        return o instanceof Set && src.containsAll((Set) o);
      }

      @Override
      public Set<E> get(int index) {
        Set<E> result = new HashSet<>();
        for (int i = 0; index != 0; i++, index >>= 1) {
          if ((index & 1) == 1) {
            result.add(src.get(i));
          }
        }
        return result;
      }
    };
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Set s = new HashSet();
    s.add("A");
    s.add("B");
    s.add("C");

    System.out.println(PowerSet.of(s));
  }
}
