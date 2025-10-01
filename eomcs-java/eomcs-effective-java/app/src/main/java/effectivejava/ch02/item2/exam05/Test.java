// # 아이템 2. 생성자에 매개변수가 많다면 빌더를 고려하라.
// - 파라미터가 많을 때 생성자나 정적 팩터리 메서드로 객체를 준비하거나 생성하기 어렵다.

package effectivejava.ch02.item2.exam05;

// 개선 방식: 빌더 패턴(Builder pattern) + 레코드(record) 사용
// - 빌더 패턴:
//   복잡한 객체의 생성 과정을 캡슐화한 별도의 빌더 객체를 두고,
//   빌더 객체의 메서드를 연쇄적으로 호출하여 필요한 매개변수를 설정한 후,
//   마지막에 빌더 객체의 build() 메서드를 호출하여 최종 객체를 생성하는 패턴
// - 레코드:
//   자바 16부터 도입된 새로운 클래스 형태로, 불변 데이터 객체를 간결하게 정의할 수 있다.
//   - 선언된 모든 필드는 final이며, 자동으로 생성자, 접근자(getter), equals(), hashCode(), toString() 메서드가 생성된다.
//   - 주로 데이터 전달 객체(Data Transfer Object, DTO)를 정의할 때 사용된다.
// - 빌더 패턴과 레코드를 함께 사용하면,
//   복잡한 객체를 간결하고 명확하게 생성할 수 있다.
record NutritionFacts(
    int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
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
      return new NutritionFacts(servingSize, servings, calories, fat, sodium, carbohydrate);
    }
  }
}

public class Test {
  public static void main(String[] args) {

    // 빌더 객체를 통해 NutritionFacts 레코드 객체를 생성한다.
    // - 리턴된 레코드 객체는 불변(immutable) 객체이다.
    NutritionFacts cocaCola =
        new NutritionFacts.Builder(240, 8).calories(100).sodium(35).carbohydrate(27).build();

    System.out.println(cocaCola);
    System.out.println(cocaCola.calories()); // 레코드의 접근자 메서드 호출

    // 레코드 객체는 불변 객체이므로, 필드 값을 변경할 수 없다.
    //    cocaCola.calories = 200; // 컴파일 에러: 레코드의 필드는 final이므로 변경할 수 없다.
  }
}
