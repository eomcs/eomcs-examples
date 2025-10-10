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

package effectivejava.ch05.item33.exam03;

// [주제] Favorites 가 타입 불변식을 어기지 않도록 수정하기

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Favorites {
  private Map<Class<?>, Object> favorites = new HashMap<>();

  public <T> void putFavorite(Class<T> type, T instance) {
    // 타입 불변식(type invariant)을 어기지 않도록 수정
    // - instance의 타입이 type이 가리키는 클래스의 타입과 일치하는지 검사하면 된다.
    // - 조건문으로 검사할 수도 있지만, cast() 메서드를 사용하면 더 간단하다.
    favorites.put(Objects.requireNonNull(type), type.cast(instance));
  }

  public <T> T getFavorite(Class<T> type) {
    return type.cast(favorites.get(type));
  }
}

public class Test {

  public static void main(String[] args) throws Exception {
    Favorites f = new Favorites();

    f.putFavorite(String.class, "Java");
    f.putFavorite(Integer.class, 0xcafebabe);
    f.putFavorite(Class.class, Favorites.class);

    String favoriteString = f.getFavorite(String.class);
    int favoriteInteger = f.getFavorite(Integer.class);
    Class<?> favoriteClass = f.getFavorite(Class.class);
    System.out.printf("%s, %x, %s%n", favoriteString, favoriteInteger, favoriteClass.getName());
  }
}
