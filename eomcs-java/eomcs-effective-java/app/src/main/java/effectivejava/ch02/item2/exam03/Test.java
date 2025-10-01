// # 아이템 2. 생성자에 매개변수가 많다면 빌더를 고려하라.
// - 파라미터가 많을 때 생성자나 정적 팩터리 메서드로 객체를 준비하거나 생성하기 어렵다.

package effectivejava.ch02.item2.exam03;

// 개선 방식: 빌더 패턴(Builder pattern)을 사용
// - telescoping constructor pattern의 안정성과 JavaBeans pattern의 가독성을 겸비한 방식
class NutritionFacts {
  private final int servingSize; // (ml, 1회 제공량)         필수
  private final int servings; // (회, 총 n회 제공량)      필수
  private final int calories; // (1회 제공량당)          선택
  private final int fat; // (g/1회 제공량)          선택
  private final int sodium; // (mg/1회 제공량)         선택
  private final int carbohydrate; // (g/1회 제공량)          선택

  private NutritionFacts(Builder builder) {
    this.servingSize = builder.servingSize;
    this.servings = builder.servings;
    this.calories = builder.calories;
    this.fat = builder.fat;
    this.sodium = builder.sodium;
    this.carbohydrate = builder.carbohydrate;
  }

  public static class Builder {
    // 필수 매개변수
    private final int servingSize;
    private final int servings;

    // 선택 매개변수 - 기본값으로 초기화
    private int calories = 0;
    private int fat = 0;
    private int sodium = 0;
    private int carbohydrate = 0;

    public Builder(int servingSize, int servings) {
      this.servingSize = servingSize;
      this.servings = servings;
    }

    public Builder calories(int val) {
      calories = val;
      return this;
    }

    public Builder fat(int val) {
      fat = val;
      return this;
    }

    public Builder sodium(int val) {
      sodium = val;
      return this;
    }

    public Builder carbohydrate(int val) {
      carbohydrate = val;
      return this;
    }

    public NutritionFacts build() {
      return new NutritionFacts(this);
    }
  }
}

public class Test {
  public static void main(String[] args) {

    // 빌더 객체를 통해 NutritionFacts 객체를 생성한다.
    // - 리턴된 객체는 불변(immutable) 객체이다.
    NutritionFacts cocaCola =
        new NutritionFacts.Builder(240, 8).calories(100).sodium(35).carbohydrate(27).build();
    // - 빌더 객체를 만들 때 필수 값을 반드시 설정해야 하기 때문에, 점층적 생성자 패턴의 장점을 살렸다.
    // - 메서드 이름을 통해 파라미터의 의미를 알기 쉽기 때문에, 자바빈즈 패턴의 장점을 살렸다.
    // - 메서드를 연쇄적으로 호출하는 방식을 "method chaining" 또는 "fluent API" 라고 한다.
    //   파이썬, 스칼라에 있는 "명령된 선택적 매개변수(named optional parameter)"와 비슷한 개념

    // 참고: 불변(immutable or immutability)과 불변식(invariant)
    // - 불변: 객체의 상태(state)가 변하지 않는 것
    // - 불변식: 객체가 반드시 지켜야 하는 조건
    //   예) 어떤 클래스의 불변식이 "나이(age)는 음수가 될 수 없다" 라고 한다면,
    //       이 불변식을 지키지 않는 객체는 잘못된 객체이다.
    //       즉, 나이가 -1인 객체는 잘못된 객체이다.
    //   예) 어떤 클래스의 불변식이 "종료 시간(end time)은 시작 시간(start time)보다 늦어야 한다" 라고 한다면,
    //       이 불변식을 지키지 않는 객체는 잘못된 객체이다.
    //       즉, 시작 시간이 10시이고 종료 시간이 9시인 객체는 잘못된 객체이다.
  }
}
