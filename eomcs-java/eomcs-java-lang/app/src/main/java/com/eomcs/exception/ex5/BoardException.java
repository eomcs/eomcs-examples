package com.eomcs.exception.ex5;

public class BoardException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public BoardException() {
    super();
  }

  public BoardException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public BoardException(String message, Throwable cause) {
    super(message, cause);
  }

  public BoardException(String message) {
    super(message);
  }

  public BoardException(Throwable cause) {
    super(cause);
  }

  // 이 클래스는 생성자가 호출될 때 그와 대응하는 수퍼 클래스의 생성자를 호출하는
  // 일 외에는 다른 작업을 수행하지 않는다.
  //
  // 아니, 기능을 추가할 것도 아니면서 왜 RuntimeException을 상속 받았는가?
  // => 이 클래스는 기존의 예외 클래스 기능을 확장하기 위함이 아니라,
  //    의미있는 이름을 가진 예외 클래스를 만드는 것이 목적이다.
  // => 즉 예외가 발생했을 때 클래스 이름으로 어떤 예외인지 
  //    쉽게 추측할 수 있도록 하기 위함이다.
  // => 일종의 분류표로서 사용한다.



}
