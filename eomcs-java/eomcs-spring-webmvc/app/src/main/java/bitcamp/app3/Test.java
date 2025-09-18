package bitcamp.app3;

public class Test {
  static class A {}
  static class B extends A {}

  public static void main(String[] args) {
    System.out.println(A.class.isAssignableFrom(B.class));
  }
}
