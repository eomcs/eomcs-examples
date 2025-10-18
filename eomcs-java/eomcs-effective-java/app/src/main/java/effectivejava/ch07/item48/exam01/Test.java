// # 아이템 48. 스트림 병렬화는 주의해서 적용하라
// [자바의 동시성 프로그래밍 역사]
// - 자바 1.0: 스레드와 동기화 제공
// - 자바 1.5: java.util.concurrent 라이브러리 + 실행자(Executor) 프레임워크 추가
// - 자바 1.7: 고성능 병렬 분해 프레임워크(Fork/Join) 추가
// - 자바 1.8: 스트림 병렬화 추가
// [동시성 프로그래밍의 어려움]
// - 안정성과 응답 가능 상태를 유지하면서 여러 스레드가 공유 자원을 안전하게 접근하도록 만드는 것은 매우 어렵다.
// - 안정성이란?
//   여러 스레드가 동시에 공유자원을 접근했을 때 스레드 상호간 값을 덮어 쓰지 않도록 작업의 순차성을 보장하는 것.
//   예) synchronized void increment() { count++; }
// - 응답 가능 상태란?
//   여러 스레드가 동시에 공유자원을 접근했을 때 교착 상태에 빠지지 않고 적절한 시간 내에 작업을 완료하는 것.
//   예) 데드락(deadlock), 활주(lock contention), 무한 대기 등의 문제를 방지.
// - "데이터를 보호하기 위해 lock을 걸면 응답성이 떨어지고, 응답성을 높이려 lock을 줄이면 안정성이 깨진다."
//

package effectivejava.ch07.item48.exam01;

// [주제] 스트림 병렬화 사용 전

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;

import java.math.BigInteger;
import java.util.stream.Stream;

public class Test {
  public static void main(String[] args) {
    // 메스센 소수 출력하기
    primes()
        // 소스 --> 2의 n승(제곱) - 1 = 메르센 소수(Mersenne prime numbers)
        .map(p -> TWO.pow(p.intValueExact()).subtract(ONE))
        .filter(mersenne -> mersenne.isProbablePrime(50))
        .limit(20)
        .forEach(System.out::println);
  }

  static Stream<BigInteger> primes() {
    return Stream.iterate(TWO, BigInteger::nextProbablePrime);
  }
}
