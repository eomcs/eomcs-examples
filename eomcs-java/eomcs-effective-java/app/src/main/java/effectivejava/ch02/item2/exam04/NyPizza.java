package effectivejava.ch02.item2.exam04;

import java.util.Objects;

public class NyPizza extends Pizza {

  public enum Size {
    SMALL,
    MEDIUM,
    LARGE
  }

  private final Size size;

  public static class Builder extends Pizza.Builder<Builder> {
    private final Size size;

    public Builder(Size size) {
      this.size = Objects.requireNonNull(size);
    }

    @Override
    public NyPizza build() {
      return new NyPizza(this);
    }

    @Override
    protected Builder self() {
      // 하위 클래스의 메서드가 상위 클래스의 메서드가 정의한 반환 타입이 아닌,
      // 그 하위 타입을 반환하는 것을 공변 반환 타입(covariant return type)이라 한다.
      // 이 기능을 이용하면 상위 클래스의 메서드를 오버라이드하는 하위 클래스의 메서드가
      // 정확히 그 하위 타입을 반환하도록 할 수 있다.
      return this;
    }
  }

  private NyPizza(Builder builder) {
    super(builder);
    size = builder.size;
  }

  @Override
  public String toString() {
    return "New York Pizza with " + toppings;
  }
}
