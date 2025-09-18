package com.eomcs.lang.ex07;

// # 배열의 생성은 어디서 하는 것이 좋은가?
// - 상황에 따라 적합한 것을 선택하면 된다.
// - 정답은 없다!
public class Exam0421 {

  public static void main(String[] args) throws Exception {

    int[] moneys = new int[] {100, 200, 300};
    float[] totals = new float[moneys.length];

    // 호출하는 쪽에서 결과를 담을 배열을 주는 경우
    compute(moneys, totals, 0.0089f);

    for (int i = 0; i < moneys.length; i++) {
      System.out.printf("%d => %.1f\n", moneys[i], totals[i]);
    }

    System.out.println("---------------------");

    float[] result;
    // 메서드 쪽에서 결과를 담을 배열을 만들어 리턴하는 경우
    result = compute2(moneys, 0.0089f);

    for (int i = 0; i < moneys.length; i++) {
      System.out.printf("%d => %.1f\n", moneys[i], result[i]);
    }

  }

  static void compute(int[] moneys, float[] totals, float interest) {
    for (int i = 0; i < moneys.length; i++) {
      totals[i] = moneys[i] + (moneys[i] * interest);
    }
  }

  static float[] compute2(int[] moneys, float interest) {
    float[] totals = new float[moneys.length];
    for (int i = 0; i < moneys.length; i++) {
      totals[i] = moneys[i] + (moneys[i] * interest);
    }
    return totals;
  }

}






