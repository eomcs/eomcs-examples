// # 아이템 2. 생성자에 매개변수가 많다면 빌더를 고려하라.
// - 파라미터가 많을 때 생성자나 정적 팩터리 메서드로 객체를 준비하거나 생성하기 어렵다.

package effectivejava.ch02.item2.exam02;

// 기존 방식: 자바빈즈 패턴(JavaBeans pattern)을 사용
// - setter를 호출해 파라미터 값을 설정한다.
class NutritionFacts {
  private int servingSize; // (ml, 1회 제공량)         필수
  private int servings; // (회, 총 n회 제공량)      필수
  private int calories; // (1회 제공량당)          선택
  private int fat; // (g/1회 제공량)          선택
  private int sodium; // (mg/1회 제공량)         선택
  private int carbohydrate; // (g/1회 제공량)          선택

  public void setServingSize(int servingSize) {
    this.servingSize = servingSize;
  }

  public void setServings(int servings) {
    this.servings = servings;
  }

  public void setCalories(int calories) {
    this.calories = calories;
  }

  public void setFat(int fat) {
    this.fat = fat;
  }

  public void setSodium(int sodium) {
    this.sodium = sodium;
  }

  public void setCarbohydrate(int carbohydrate) {
    this.carbohydrate = carbohydrate;
  }
}

public class Test {
  public static void main(String[] args) {

    // 인스턴스를 생성한 후, setter로 값을 설정한다.
    // - '점층적 생성자 패턴' 보다 코드가 길어졌지만, 읽기 쉬운 코드가 되었다.
    NutritionFacts cocaCola = new NutritionFacts();
    cocaCola.setServingSize(240);
    cocaCola.setServings(8);
    cocaCola.setCalories(100);
    cocaCola.setSodium(35);
    cocaCola.setCarbohydrate(27);

    // [문제] 일관성이 깨지고, 불변으로 만들 수 없다.
    // - 객체 하나를 만들기 위해 여러 메서드를 호출해야 한다.
    // - setter를 호출하지 않아 필수 필드 값을 설정하지 않는 실수를 범할 수 있다.
    //   (점층적 생성자 패턴에서는 최소한 필수 필드를 설정하도록 강제할 수 있다)
    // - 불변 객체(immutable object)를 만들 수 없다. => 스레드 안정성을 보장할 수 없다.
    //   (불변 객체: String 같이 한 번 생성되면 내부 상태가 바뀌지 않는 객체)
  }
}
