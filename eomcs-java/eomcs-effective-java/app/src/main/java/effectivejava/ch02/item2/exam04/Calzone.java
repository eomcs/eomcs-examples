package effectivejava.ch02.item2.exam04;

public class Calzone extends Pizza {
  private final boolean sauceInside;

  public static class Builder extends Pizza.Builder<Builder> {
    private boolean sauceInside = false; // Default

    public Builder sauceInside() {
      sauceInside = true;
      return this;
    }

    @Override
    public Calzone build() {
      return new Calzone(this);
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

  private Calzone(Builder builder) {
    super(builder);
    sauceInside = builder.sauceInside;
  }

  @Override
  public String toString() {
    return String.format(
        "Calzone with %s and sauce on the %s", toppings, sauceInside ? "inside" : "outside");
  }
}
