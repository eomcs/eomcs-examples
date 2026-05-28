package com.eomcs.cleancode.ch09.exam05;

import com.eomcs.cleancode.ch09.exam05.BadAndGood4.User;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - S (Self-Validating)
//
// 문제점:
// - assert가 없어 테스트가 항상 통과한다 (아무것도 검증하지 않는다).
// - 결과를 사람이 직접 콘솔 출력을 보고 판단해야 한다.
// - 자동화된 빌드/CI 파이프라인에서 버그를 잡을 수 없다.
// - 이것은 테스트가 아니라 단순한 코드 실행이다.
class SelfValidatingBadTest {

  @Test
  void 사용자_출력() {
    User user = new User("kim");

    System.out.println(user.getName()); // 사람이 확인해야 함 - 자동화 불가
  }
}
