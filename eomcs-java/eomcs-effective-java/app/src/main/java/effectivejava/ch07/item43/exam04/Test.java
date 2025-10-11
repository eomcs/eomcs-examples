// # 아이템 43. 람다보다는 메서드 참조를 사용하라
// [메서드 레퍼런스]
// - 함수 객체를 람다보다도 더 간결하게 만들 수 있다.
//

package effectivejava.ch07.item43.exam04;

// [주제] 메서드 레퍼런스의 5가지 유형

import java.util.TreeMap;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

class InterestComputer {
  private final double rate;

  public InterestComputer(double rate) {
    this.rate = rate;
  }

  double compute(double money) {
    return money * rate;
  }
}

public class Test {

  static int strToInt(String str, Function<String, Integer> converter) {
    return converter.apply(str);
  }

  static double computeInterest(double money, DoubleUnaryOperator interestComputer) {
    return interestComputer.applyAsDouble(money);
  }

  static void printLowerCase(String s, Function<String, String> converter) {
    System.out.println(converter.apply(s));
  }

  static TreeMap<String, Integer> computeScores(Supplier<TreeMap<String, Integer>> factory) {
    TreeMap<String, Integer> treeMap = factory.get();
    treeMap.put("홍길동", 100);
    treeMap.put("임꺽정", 90);
    treeMap.put("장길산", 80);
    return treeMap;
  }

  static int[] createIntArray(int size, IntFunction<int[]> arrayCreator) {
    return arrayCreator.apply(size);
  }

  public static void main(String[] args) {
    // 1) 정적 메서드 레퍼런스
    int result1 = strToInt("123", Integer::parseInt);
    int result2 = strToInt("100", str -> Integer.parseInt(str));
    System.out.printf("%d, %d%n", result1, result2);
    System.out.println("---------------------------");

    // 2) 한정적 인스턴스 메서드 레퍼런스
    // - 특정 인스턴스에 종속적인 메서드 레퍼런스
    InterestComputer ic = new InterestComputer(0.065);
    double interest1 = computeInterest(10_000_000, ic::compute);
    double interest2 = computeInterest(10_000_000, money -> ic.compute(money));
    System.out.printf("%.1f, %.1f%n", interest1, interest2);
    System.out.println("---------------------------");

    // 3) 비한정적 인스턴스 메서드 레퍼런스
    // - 함수 객체를 적용하는 시점에 인스턴스를 받는 메서드 레퍼런스
    printLowerCase("Hello, World!", String::toLowerCase);
    printLowerCase("Hello, World!", str -> str.toLowerCase());
    System.out.println("---------------------------");

    // 4) 클래스 생성자 레퍼런스
    TreeMap<String, Integer> scoreMap1 = computeScores(TreeMap::new);
    TreeMap<String, Integer> scoreMap2 = computeScores(() -> new TreeMap<>());
    System.out.println(scoreMap1);
    System.out.println(scoreMap2);
    System.out.println("---------------------------");

    // 5) 배열 생성자 레퍼런스
    int[] arr1 = createIntArray(5, int[]::new);
    int[] arr2 = createIntArray(5, size -> new int[size]);
    System.out.println(arr1.length);
    System.out.println(arr2.length);
    System.out.println("---------------------------");

    // [정리]
    // - 메서드 레퍼런스 쪽이 짧고 명확하다면 메서드 참조를 쓰고,
    //   그렇지 않을 때만 람다를 사용하라.
  }
}
