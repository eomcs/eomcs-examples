// # 아이템 2. 생성자에 매개변수가 많다면 빌더를 고려하라.
// - 파라미터가 많을 때 생성자나 정적 팩터리 메서드로 객체를 준비하거나 생성하기 어렵다.

package effectivejava.ch02.item2.exam01;

// 기존 방식: 점층적 생성자 패턴(telescoping constructor pattern)을 사용
// - 필수 필드 값만 받는 생성자,
// - 필수 필드와 선택 필드값 1개를 받는 생성자,
// - 필수 필드와 선택 필드값 2개를 받는 생성자, ...
class NutritionFacts {
  private final int servingSize; // (ml, 1회 제공량)         필수
  private final int servings; // (회, 총 n회 제공량)      필수
  private final int calories; // (1회 제공량당)          선택
  private final int fat; // (g/1회 제공량)          선택
  private final int sodium; // (mg/1회 제공량)         선택
  private final int carbohydrate; // (g/1회 제공량)          선택

  public NutritionFacts(int servingSize, int servings) {
    this(servingSize, servings, 0);
  }

  public NutritionFacts(int servingSize, int servings, int calories) {
    this(servingSize, servings, calories, 0);
  }

  public NutritionFacts(int servingSize, int servings, int calories, int fat) {
    this(servingSize, servings, calories, fat, 0);
  }

  public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
    this(servingSize, servings, calories, fat, sodium, 0);
  }

  public NutritionFacts(
      int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
    this.servingSize = servingSize;
    this.servings = servings;
    this.calories = calories;
    this.fat = fat;
    this.sodium = sodium;
    this.carbohydrate = carbohydrate;
  }
}

public class Test {
  public static void main(String[] args) {

    // 클래스의 인스턴스를 만들 때 원하는 파라미터를 모두 포함한 생성자 중에서 가장 짧은 것을 호출한다.
    NutritionFacts cocaCola = new NutritionFacts(240, 8, 100, 0, 35, 27);
    // [문제] 확장하기 어렵다.
    // - 사용자가 설정하고 싶지 않은 파라미터의 값도 모두 넣어줘야 한다.
    // - 파라미터가 많아지면, 클라이언트 코드를 작성하기 어렵고, 읽기도 어렵다.
    // - 코드를 읽을 때 각 파라미터가 무엇을 의미하는지 알기 어렵다.
    // - 파라미터가 몇 개인지 주의해서 세어 보아야 한다.
    // - 같은 타입의 파라미터가 연달아 있으면, 파라미터의 순서를 바꿔도 컴파일러가 알아채지 못한다.
  }
}
