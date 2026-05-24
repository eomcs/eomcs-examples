package com.eomcs.cleancode.ch04.exam03;

public class BadAndGood1 {

  // Bad: 법적 고지가 없다.
  // - 오픈소스로 배포할 때 라이선스가 누락되면 법적 문제가 생길 수 있다.
  static class BadUserService {
    void save() { System.out.println("저장"); }
  }

  // Good: 법적인 주석 (Legal Comments)
  // - 저작권·라이선스 정보는 주석으로 표기해야 하는 법적 이유가 있다.
  // - 긴 법률 문서를 코드에 넣기보다 라이선스 파일을 참조하는 형태가 좋다.
  //
  // Copyright (C) 2026 Example Corp.
  // Released under the MIT License.
  // See LICENSE file in the project root for full license information.
  static class GoodUserService {
    void save() { System.out.println("저장"); }
  }
}
