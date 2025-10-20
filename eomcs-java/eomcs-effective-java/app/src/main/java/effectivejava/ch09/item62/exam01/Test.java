// # 아이템 62. 다른 타입이 적절하다면 문자열 사용을 피하라
// - 문자열은 텍스트를 표현하도록 설계되었는데, 의도하지 않은 용도로도 쓰이는 경향이 있다.
//   문자열을 잘못 사용하면 번거롭고, 덜 유연하고, 느리고, 오류 가능성도 크다.
//
// [문자열을 쓰지 않아야 할 사례]
// 1) 문자열은 다른 값 타입을 대신하기에 적합하지 않다.
//    사용자로부터 값을 입력 받을 때는 문자열이더라도
//    받은 데이터가 수치형이면 int, float, BigInteger 등 적당한 수치타입으로 변환하라.
//    '예/아니오' 값은 적절한 열거형이나 boolean으로 변환하라.
//    적절한 값 타입이 없다면, 새로 하나 만들라.
// 2) 문자열은 열거 타입을 대신하기에 적합하지 않다.
// 3) 문자열은 혼합 타입을 대신하기에 적합하지 않다.
//    전용 클래스를 새로 만드는 것이 낫다.
// 4) 문자열은 권한을 표현하기에 적합하지 않다.
//
//
package effectivejava.ch09.item62.exam01;

// [주제] 문자열을 잘못 사용한 예

public class Test {

  public static void main(String[] args) {
    // 혼합 타입을 문자열로 처리한 부적절한 예
    String departmentCode = "HR"; // HR: 인사부
    String employNo = "00124"; // 사원 일련 번호
    String employId = departmentCode + "#" + employNo;
    System.out.println("사원ID: " + employId);

    // [문제점]
    // - 각 요소를 개별로 접근하려면 문자열을 파싱해야 해서 느리고, 귀찮고, 오류 가능성도 커진다.
    // - equals, toString, compareTo 같은 메서드를 제공할 수 없으며,
    //   String이 제공하는 기능에만 의존해야 한다.

    // [해결책]
    // - 차라리 전용 클래스를 새로 만드는 것이 낫다.
    class EmployeeId {
      private final String departmentCode;
      private final String employNo;

      public EmployeeId(String departmentCode, String employNo) {
        this.departmentCode = departmentCode;
        this.employNo = employNo;
      }

      @Override
      public String toString() {
        return departmentCode + "#" + employNo;
      }

      // equals, hashCode, compareTo 등도 적절히 구현 가능
    }
  }
}
