// # 아이템 33. 타입 안전 이종 컨테이너를 고려하라
// [일반적인 제네릭 컨테이너]
// - 일반적인 제네릭 컨테이너는 동일한 타입의 원소만 저장할 수 있다.
// - 컬렉션
//   - Set<E>: 원소의 타입을 뜻하는 한 개의 타입 파라미터를 갖는다.
//   - Map<K,V>: 키와 값의 타입을 뜻하는 두 개의 타입 파라미터를 갖는다.
// - 단일원소 컨테이너
//   - ThreadLocal<T>: 스레드별로 다른 값을 저장하는 컨테이너
//   - AtomicReference<T>: 원자적으로 참조를 교체하는 컨테이너
//
// [타입 안전 이종 컨테이너(heterogeneous container)]
// - 서로 다른 타입의 객체를 저장할 수 있는 컨테이너
// - Class<T> 객체를 키(key)로 사용해 값을 저장하고 검색하는 컨테이너
// - 이 패턴을 사용하면 컴파일 시점과 런타임에서 타입 안전성을 보장할 수 있다.

package effectivejava.ch05.item33.exam05;

// [주제] 메서드가 받아들이는 타입을 제한하기: 한정적 타입 토큰 사용 II
// - 한정적 타입 파라미터나 한정적 와일드카드를 사용해 타입을 제한할 수 있다.

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Favorites {
  private Map<Class<?>, Object> favorites = new HashMap<>();

  // 한정적 와일드카드를 사용하여 한정적 타입 토큰을 구현한다.
  // - 명시적 제네릭 선언이 필요없다.
  public void putFavorite(Class<? extends Number> type, Number instance) {
    favorites.put(Objects.requireNonNull(type), type.cast(instance));
  }

  // 단, 반환 타입이 제네릭이 아닌 Number로 제한된다.
  public Number getFavorite(Class<? extends Number> type) {
    return type.cast(favorites.get(type));
  }
}

public class Test {

  public static void main(String[] args) throws Exception {
    Favorites f = new Favorites();

    f.putFavorite(Integer.class, 0xcafebabe);
    f.putFavorite(Float.class, 3.14f);
    f.putFavorite(Long.class, 300L);

    // 컴파일 오류: 타입 제한에 걸림
    //    f.putFavorite(String.class, "Java");
    //    f.putFavorite(Class.class, Favorites.class);

    // 한정적 와일드카드인 경우 반환 값에 대해 형변환이 필요하다.
    int favoriteInteger = (int) f.getFavorite(Integer.class);
    float favoriteFloat = (float) f.getFavorite(Float.class);
    long favoriteLong = (long) f.getFavorite(Long.class);

    System.out.println(favoriteInteger);
    System.out.println(favoriteFloat);
    System.out.println(favoriteLong);
  }
}
