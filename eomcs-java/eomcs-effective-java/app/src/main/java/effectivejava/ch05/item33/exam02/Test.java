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

package effectivejava.ch05.item33.exam02;

// [주제] 타입 안전 이종 컨테이너 패턴 사용하기(Typesafe Heterogeneous Container Pattern)
// - key 자체에 타입 정보를 포함시키면, 컴파일러가 타임 안전성을 검사할 수 있다.
// - 컴파일 타임 타입 정보와 런타임 타입 정보를 알아내기 위해,
//   메서드들을 주고 받는 class 리터럴을 "타입 토큰(type token)"이라고 부른다.
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Favorites {
  // Class 객체를 키(key)로 사용해 값을 저장하고 검색하는 컨테이너
  private Map<Class<?>, Object> favorites = new HashMap<>();

  public <T> void putFavorite(Class<T> type, T instance) {
    favorites.put(Objects.requireNonNull(type), instance);
  }

  public <T> T getFavorite(Class<T> type) {
    // cast()
    // - 객체 참조를 동적으로 형변환해주는 메서드
    // - 형변환이 불가능하면 ClassCastException을 던진다.
    // - 제네릭 메서드이기 때문에 반환 타입은 Class 객체의 type parameter와 일치한다.
    return type.cast(favorites.get(type));
  }
}

public class Test {

  public static void main(String[] args) throws Exception {
    Favorites f = new Favorites();

    // 값을 저장할 때 타입 토큰(class 리터럴)을 함께 넘긴다.
    // - key에 따라 서로 다른 타입을 저장하기 때문에 "이종 컨테이너"라고 부른다.
    // - 다만 같은 타입의 값을 저장할 수 없다!
    f.putFavorite(String.class, "Java");
    f.putFavorite(Integer.class, 0xcafebabe);
    f.putFavorite(Class.class, Favorites.class);

    // 타입 안정성 보장!
    // - 컴파일 시 타입 안전성을 검사한다.
    //    f.putFavorite(Boolean.class, "true"); // 컴파일 오류!

    // 값을 꺼낼 때도 타입 토큰(class 리터럴)을 함께 넘긴다.
    String favoriteString = f.getFavorite(String.class);
    int favoriteInteger = f.getFavorite(Integer.class);
    Class<?> favoriteClass = f.getFavorite(Class.class);
    System.out.printf("%s, %x, %s%n", favoriteString, favoriteInteger, favoriteClass.getName());
  }
}
