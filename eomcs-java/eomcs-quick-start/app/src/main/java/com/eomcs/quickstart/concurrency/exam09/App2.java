package com.eomcs.quickstart.concurrency.exam09;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

// WebFlux 스타일 HTTP 예외 매핑:
//
// 컨트롤러나 핸들러가 Mono를 반환할 때, 파이프라인 내부에서 발생한 예외는
// WebFlux가 구독하는 시점에 error signal로 전달된다.
//
// onErrorMap(type, function)
//   - 특정 예외 타입을 다른 예외로 변환한다. 정상 값으로 복구하지 않는다.
//   - 도메인 예외를 HTTP 응답 상태를 가진 ResponseStatusException으로 바꿀 때 유용하다.
//   - 실제 WebFlux 서버에서는 ResponseStatusException이 HTTP 상태 코드(403 등)로 변환된다.
//
// onErrorResume vs onErrorMap:
//   onErrorResume : 예외를 대체 Mono(정상 값)로 복구한다.
//   onErrorMap    : 예외를 다른 예외로 변환한다. 스트림이 여전히 error 상태로 남는다.
//
// 이 예제는 서버를 띄우지 않고, 같은 파이프라인을 main에서 block()으로 실행해 관찰한다.
// block()은 ResponseStatusException(RuntimeException)을 그대로 던지므로 try-catch로 잡는다.

public class App2 {

  public static void main(String[] args) {

    System.out.println("[HTTP 매핑] 도메인 예외 → ResponseStatusException");
    System.out.println("서비스 계층 예외를 WebFlux가 이해할 수 있는 HTTP 예외로 바꾼다.");
    System.out.println();

    // getMember("guest")는 checkAccess()에서 UnauthorizedMemberException을 발생시킨다.
    Mono<Member> handlerResult =
        getMember("guest")
            // onErrorMap(): UnauthorizedMemberException을 ResponseStatusException(403)으로 변환한다.
            // - 실제 WebFlux 서버에서는 이 변환이 자동으로 HTTP 403 응답으로 이어진다.
            // - onErrorResume()과 달리 정상 값으로 복구하지 않는다. 예외가 바뀐 채 전파된다.
            .onErrorMap(
                UnauthorizedMemberException.class,
                ex -> new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage(), ex));

    try {
      // block()은 파이프라인을 구독하고 대기한다.
      // - onErrorMap()이 ResponseStatusException(RuntimeException)을 발생시키므로 try-catch로 잡는다.
      Member member = handlerResult.block();
      System.out.println("  회원: " + member);
    } catch (ResponseStatusException e) {
      System.out.printf("  HTTP 상태: %s%n", e.getStatusCode()); // 403 FORBIDDEN
      System.out.printf("  응답 사유: %s%n", e.getReason());
    }

    System.out.println("→ onErrorMap()은 예외 종류를 바꾸며, 복구해서 정상 값으로 만들지는 않는다.");
  }

  private static Mono<Member> getMember(String memberId) {
    // findMember()로 회원을 조회한 뒤, flatMap()으로 checkAccess()에 연결한다.
    // - flatMap()은 Mono<T> → Mono<U> 변환에 사용한다. (map()은 T → U 값 변환)
    return findMember(memberId).flatMap(App2::checkAccess);
  }

  private static Mono<Member> findMember(String memberId) {
    // Mono.just(): 이미 준비된 값을 Mono로 감싼다.
    return Mono.just(new Member(memberId, "GUEST"));
  }

  private static Mono<Member> checkAccess(Member member) {
    // 권한이 없으면 error signal을 발생시킨다. (throw 대신 Mono.error 사용)
    // - 이 예외는 호출 스택으로 즉시 전파되지 않고, 파이프라인의 error signal로 전달된다.
    if (member.role().equals("GUEST")) {
      return Mono.error(new UnauthorizedMemberException("접근 권한 없음: " + member.id()));
    }
    return Mono.just(member);
  }

  record Member(String id, String role) {}

  static class UnauthorizedMemberException extends RuntimeException {
    UnauthorizedMemberException(String message) {
      super(message);
    }
  }
}
