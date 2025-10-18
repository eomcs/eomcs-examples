// # 아이템 48. 스트림 병렬화는 주의해서 적용하라
// [자바의 동시성 프로그래밍 역사]
// - 자바 1.0: 스레드와 동기화 제공
// - 자바 1.5: java.util.concurrent 라이브러리 + 실행자(Executor) 프레임워크 추가
// - 자바 1.7: 고성능 병렬 분해 프레임워크(Fork/Join) 추가
// - 자바 1.8: 스트림 병렬화 추가
//
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

package effectivejava.ch07.item48.exam03;

// [주제] 스트림 병렬화에 적합한 경우

import java.math.BigInteger;
import java.util.stream.LongStream;

public class Test {

  static long pi(long n) {
    return LongStream.rangeClosed(2, n)
        //        .parallel() // 스트림 병렬화 적용
        .mapToObj(BigInteger::valueOf)
        .filter(i -> i.isProbablePrime(50))
        .count();
  }

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    System.out.println(pi(10_000_000));
    long end = System.currentTimeMillis();
    System.out.printf("경과 시간: %.2f초\n", (end - start) / 1000f);

    // [참고] 무작위 수들로 이뤄진 스트림 병렬화
    // - 무작위 수들로 이뤄진 스트림을 병렬화할 때는
    //   ThreadLocalRandom(혹은 구식인 Random)보다는 SplittableRandom을 사용하라.
    // - SplittableRandom은 병렬 스트림에 더 적합하도록 설계되었다.
    // - ThreadLocalRandom은 단일 스레드에서 쓰고자 만들어졌다.
    //   병렬 스트림에서 사용하면 SplittableRandom보다 빠르지 않다.
    // - Random은 모든 연산을 동기화하기 때문에,
    //   병렬 처리하면 최악의 성능을 보일 것이다.

    // [정리]
    // - 계산도 올바로 수행하고 성능도 빨라질 거라는 확신 없이는 스트림 파이프라인 병렬화는 시도조차 하지 말라.
    // - 스트림을 잘못 병렬화하면 프로그램을 오동작하게 하거나 성능을 급격히 떨어뜨린다.
    // - 병렬화가 낫다고 믿더라도, 수정 후의 코드가 여전히 정확한지 확인하고
    //   운영 환경과 유사한 조건에서 수행해보면 성능 지표를 유심히 관찰하라.
    //   그래서 계산도 정확하고 성능도 좋아졌음이 확실해졌을 때 비로소 병렬화를 채택하라.
  }
}
