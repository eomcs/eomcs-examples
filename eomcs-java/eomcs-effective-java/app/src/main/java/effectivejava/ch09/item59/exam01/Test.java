// # 아이템 59. 라이브러리를 익히고 사용하라
// - 바퀴를 다시 발명하지 말자.
//   아주 특별한 나만의 기능이 아니라면 누군가 이미 라이브러리 형태로 만들어 놓았을 가능성이 크다.
//   그런 라이브러리가 있다면 쓰면 된다.
//   있는지 잘 모르겠다면 찾아보라.
// - 일반적으로 라이브러리의 코드는 직접 작성한 것보다 품질이 좋고, 점차 개선될 가능성이 크다.
//   코드 품질에도 규모의 경제가 적용된다.
//   많은 사람의 눈에 노출되는 만큼, 버그가 발견될 가능성이 크고, 발견된 버그가 수정될 가능성도 크다.
//
package effectivejava.ch09.item59.exam01;

// [주제] 0부터 n까지의 임의의 정수값 생성하기: 직접 구현 vs 표준 라이브러리 사용

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Test {
  static Random rnd = new Random();

  static int randomInt(int n) {
    // 0부터 n까지의 임의의 정수값을 생성한다.
    return Math.abs(rnd.nextInt()) % n;
  }

  public static void main(String[] args) {

    // 임의로 생성한 정수 값의 빈도수 측정하기
    // - 백만개의 수를 생성하여 중간 값보다 작은 값의 개수를 세본다.
    int n = 2 * (Integer.MAX_VALUE / 3);
    int low = 0;
    for (int i = 0; i < 100_000_000; i++) {
      int r = randomInt(n);
      if (r < n / 2) {
        low++;
      }
    }
    System.out.println("중간 값보다 작은 수의 개수: " + low);
    // [출력 결과] 중간 값보다 작은 수의 개수
    // - 기대한 개수: 약 50,000,000개
    // - 결과 개수: 약 66,666,666개

    // [이유]
    // - Math.abs()의 아규먼트가 Integer.MIN_VALUE일 때 음수 값을 그대로 반환한다.
    //   따라서 randomInt(int n) 메서드는 0부터 n-1까지의 값을 고르게 생성하지 못한다.
    System.out.println(Math.abs(Integer.MIN_VALUE)); // -2147483648
    System.out.println(Math.abs(Integer.MAX_VALUE)); // 2147483647
    // - 이 문제를 해결하려면, 의사난수 생성기, 정수론, 2의 보수 계산 등에 조예가 깊어야 한다.
    System.out.println("------------------------------");

    // [해결책]
    // - 자바 표준 라이브러리의 Random.nextInt(int n) 메서드를 사용하라.
    //   이 메서드는 0부터 n-1까지의 값을 고르게 생성한다.
    // - 알고리즘에 능통한 개발자가 설계와 구현과 검증에 시간을 들여 개발했고,
    //   20여 년 가까이 수백만의 개발자가 사용해온 검증된 코드다.
    low = 0;
    for (int i = 0; i < 100_000_000; i++) {
      int r = rnd.nextInt(n); // Random 클래스의 nextInt(int n) 메서드 사용
      if (r < n / 2) {
        low++;
      }
    }
    System.out.println("중간 값보다 작은 수의 개수: " + low);
    System.out.println("------------------------------");

    // [개선된 해결책]
    // - Random 보다는 ThreadLocalRandom을 사용하면
    //   더 고품질의 무작위 수를 생성하며 속도도 빠르다.
    // - fork/join 풀이나 병렬 스트림에서는 SplittableRandom을 사용하라.
    low = 0;
    ThreadLocalRandom tlr = ThreadLocalRandom.current();
    for (int i = 0; i < 100_000_000; i++) {
      int r = tlr.nextInt(n);
      if (r < n / 2) {
        low++;
      }
    }
    System.out.println("중간 값보다 작은 수의 개수: " + low);

    // [정리] 표준 라이브러리를 사용할 때 얻는 이점
    // 1) 그 코드를 작성한 전문가의 지식과 앞서 사용한 다른 프로그래머들의 경험을 활용할 수 있다.
    // 2) 핵심적인 일과 크게 관련 없는 문제를 해결하느라 시간을 허비하지 않아도 된다.
    //    핵심적인 일이란, 애플리케이션 기능 개발을 말한다.
    // 3) 따로 노력하지 않아도 성능이 지속해서 개선된다.
    // 4) 기능이 점점 많아진다는 것이다. 부족한 부분은 후속 릴리즈에 지속적으로 추가된다.
    // 5) 많은 사람이 알고 있는 API이기에 다른 개발자들이 더 읽기 쉽고, 유지보수하기 좋고,
    //    재활용하기 쉬운 코드가 된다.
    //
  }
}
