// # 아이템 68. 일반적으로 통용되는 명명 규칙을 따르라
// - 자바의 명명 규칙은 "자바 언어 명세(JLS)"에 잘 기술되어 있다.
// [철자 규칙]
// - 패키지, 클래스, 인터페이스, 메서드, 필드, 타입 변수의 이름을 다룬다.
//   특별한 이유가 없는 한 반드시 따라야 한다.
// - 패키지와 모듈 이름:
//   - 모두 소문자 알파벳 + (드물게) 숫자
//   - 조직 바깥에서도 사용될 패키지라면 조직의 인터넷 도메인 이름을 역순으로 사용
//   - 패키지 이름의 나머지는 해당 패키지를 설명하는 하나 이상의 요소로 이뤄진다.
//   - 각 요소는 일반적으로 8자 이하의 짧은 단어로 한다.
//     예) utilities보다는 util처럼 의미가 통하는 약어
//   - 여러 단어로 구성된 이름은 경우 awt처럼 각 단어의 첫 글자만 따서 써도 좋다.
//   - 하부의 패키지를 "하위 패키지(subpackage)"라 부른다.
// - 클래스와 인터페이스 이름:
//   - 하나 이상의 단어로 이루어진다.
//     각 단어는 대문자로 시작한다. 예) List, FutherTask
//   - 널리 통용되는 줄임말을 제외하고는 단어를 줄여 쓰지 않도록 한다.
//   - 약자의 경우 첫 글자만 대문자로 하는 쪽이 훨씬 많다. 예) HttpUrl
// - 메서드와 필드 이름:
//   - 첫 글자를 소문자로 쓴다. 나머지는 클래스 명명 규칙과 같다.
// - 상수 필드(static final 필드) 이름:
//   - 모두 대문자로 쓰며 단어 사이는 밑줄로 구분한다. 예) VALUES, NEGATIVE_INFINITY
// - 지역변수 이름:
//   - 약어를 써도 좋다.
//     왜? 그 변수가 사용되는 문맥에서 그 의미를 쉽게 유추할 수 있기 때문이다. 예) i, denom, houseNum
// - 파라미터 이름:
//   - 지역 변수 중의 하나지만 메서드 설명 문서에까지 등장하는 만큼 더 신경을 써야 한다.
// - 타입 파라미터 이름:
//   - 보통 한 문자로 표현한다.
//   - 임의타입(T), 컬렉션 원소(E), 맵의 키(K)와 값(V), 예외(X), 반환타입(R)
//   - 그 외에 임의 타입(T, U, V 또는 T1, T2, T3)
//
// [문법 규칙]
// - 객체를 생성할 수 있는 클래스나 열거타입:
//   - 보통 단수 명사나 명사구를 사용한다. 예) Thread, PriorityQueue, ChessPiece
// - 객체를 생성할 수 없는 클래스:
//   - 보통 복수형 명사로 짓는다. 예) Collectors, Collections
// - 인터페이스:
//   - 클래스와 똑같이 짓거나, 예) Collection, Comparator
//   - able, ible로 끝나는 형용사로 짓는다. 예) Runnable, Iterable, Accessible
// - 애너테이션:
//   - 지배적인 규칙이 없이 명사, 동사, 전치사, 형용사가 두루 쓰인다.
//     예) BindingAnnotation, Inject, ImplementedBy, Singleton
// - 메서드:
//   - 동사나 동사구(목적를 포함)로 짓는다. 예) append, drawImage
//   - boolean 값을 반환하는 경우,
//     is나 has로 시작하고 명사나 명사구, 혹은 형용사구 끝난다.
//     예) isDigit, isProablePrime, isEmpty, isEnabled, hasSiblings
//   - 해당 인스턴스의 속성을 반환하는 경우,
//     명사, 명사구, 혹은 get으로 시작하는 동사구로 짓는다.
//     예) size, hashCode, getTime
// - 변환 기능을 하는 메서드:
//   - 객체의 타입을 바꾸거나 다른 타입의 또 다른 객체를 반환하는 인스턴스 메서드는 toType 형태로 짓는다.
//     예) toString, toArray
//   - 객체의 내용을 다른 뷰로 보여주는 메서드의 이름은 asType 형태로 짓는다.
//     예) asList
//   - 객체의 값을 기본 타입 값으로 변환하는 메서드의 이름은 typeValue 형태로 짓는다.
//     예) intValue
// - 정적 팩토리 메서드:
//   - 예) from, of, valueOf, instance, getInstance, newInstance, getType, newType
// - 필드 이름:
//   - 직접 노출될 일이 거의 없기 때문에 다른 것의 이름에 비해 덜 명확하고 덜 중요하다.
//   - boolean 타입의 필드 이름은 보통 boolean 접근자 메서드에서 앞 단어를 뺀 형태다.
//     예) initialized, composite
//   - 다른 타입의 필드라면 명사나 명사구를 사용한다.
//     예) height, digits, bodyStyle
//   - 지역변수의 이름도 필드와 비슷하게 지으면 되나, 조금 더 느슨하다.
//
// "상식이 이끄는 대로 따르자"
//
//

package effectivejava.ch09.item68.exam01;

// [주제] 명명 규칙 알아보기

public class Test {
  public static void main(String[] args) throws Exception {}
}
