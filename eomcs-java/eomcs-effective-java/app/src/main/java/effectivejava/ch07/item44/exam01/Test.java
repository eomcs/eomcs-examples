// # 아이템 44. 표준 함수형 인터페이스를 사용하라
// - java.util.function 패키지에 표준 함수형 인터페이스가 제공된다.
// - 필요한 용도에 맞는게 있다면, 직접 정의하지 말고 표준 함수형 인터페이스를 활용하라.
//   그러면 API가 다루는 개념의 수가 줄어들어 익히기 더 쉬워진다.
// - 표준 함수형 인터페이스는 유용한 디폴트 메서드를 많이 제공하므로
//   다른 코드와의 상호운용성도 크게 좋아진다.

package effectivejava.ch07.item44.exam01;

// [주제] 표준 함수형 인터페이스에서 기본 인터페이스와 변형 인터페이스
// [기본 인터페이스(6개)]
// - UnaryOperator<T>: 단항 연산자, T -> T
//     반환값과 아규먼트의 타입이 같은 함수
//     예) String::toUpperCase
// - BinaryOperator<T>: 이항 연산자, (T, T) -> T
//     반환값과 아규먼트의 타입이 같은 함수
//     예) BigInteger::add
// - Predicate<T>: 단항 술어, T -> boolean
//     아규먼트를 하나 받아서 불리언 값을 반환하는 함수
//     예) String::isEmpty
// - Function<T,R>: 일반 함수, T -> R
//     아규먼트와 반환값의 타입이 다른 함수. 즉 임의의 타입으로 매핑하는 함수
//     예) Arrays::asList
// - Supplier<T>: 공급자, () -> T
//     아규먼트를 받지 않고 값을 반환하는 함수
//     예) Instant::now
// - Consumer<T>: 소비자, T -> void
//   아규먼트를 하나 받고 반환값이 없는 함수
//     예) System.out::println
//
// [변형 인터페이스]
// int, long, double 용으로 각 3개씩 변형이 생겨난다.
// 기본 인터페이스의 이름 앞에 해당 기본 타입 이름을 붙여 지었다.
//   예) IntPredicate, DoubleSupplier, LongConsumer
// - Function<T,R>
//   기본타입 --> 기본타입(SrcToResult):
//     IntToLongFunction
//     IntToDoubleFunction
//     LongToDoubleFunction
//     LongToIntFunction
//     DoubleToLongFunction
//     DoubleToIntFunction
//   객체 레퍼런스 --> int, long, double(ToResult):
//     ToIntFunction<T>
//     ToLongFunction<T>
//     ToDoubleFunction<T>
//   int, long, double --> 객체 레퍼런스:
//     IntFunction<R>
//     LongFunction<R>
//     DoubleFunction<R>
//   아규먼트를 2개 받는 변형:
//     BiFunction<T,U,R>
//       ToIntBiFunction<T,U>
//       ToLongBiFunction<T,U>
//       ToDoubleBiFunction<T,U>
// - Consumer<T>
//   기본타입:
//     IntConsumer
//     LongConsumer
//     DoubleConsumer
//   아규먼트를 2개 받는 변형:
//     BiConsumer<T,U>
//   아규먼트를 2개 받는 변형(객체 레퍼런스 + 기본타입):
//     ObjIntConsumer<T>
//     ObjLongConsumer<T>
//     ObjDoubleConsumer<T>
// - Supplier<T>
//   기본타입:
//     IntSupplier
//     LongSupplier
//     DoubleSupplier
//     BooleanSupplier
// - Predicate<T>
//   기본타입:
//     IntPredicate
//     LongPredicate
//     DoublePredicate
//   아규먼트를 2개 받는 변형:
//     BiPredicate<T,U>
// - UnaryOperator<T>
//   기본타입:
//     IntUnaryOperator
//     LongUnaryOperator
//     DoubleUnaryOperator
// - BinaryOperator<T>
//   기본타입:
//     IntBinaryOperator
//     LongBinaryOperator
//     DoubleBinaryOperator
//

public class Test {
  public static void main(String[] args) {}
}
