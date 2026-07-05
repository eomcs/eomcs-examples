/**
 * JavaScript 이벤트 루프: 오래 걸리는 동기 작업은 타이머 콜백도 지연시킨다.
 */

export {}

setTimeout(() => console.info('F: waits for the call stack'), 0)

let sum = 0

for (let i = 0; i < 5_000_000; i++) {
  sum += i
}

console.info('G: long synchronous work finished', sum)

// 출력 순서:
// G: long synchronous work finished ...
// F: waits for the call stack
//
// 실행 순서:
// 1. setTimeout(...) 호출
//    - F를 출력하는 콜백 참조와 값0을 아규먼트로 하여 네이티브 타임아웃 API를 호출한다.
//    - 값0은 "가능한 한 빨리" 예약하라는 뜻이지, 지금 즉시 콜백을 실행하라는 뜻이 아니다.
// 2. setTimeout(...)은 콜백을 예약한 뒤 바로 반환한다.
//    - 그래서 메인 스레드는 다음 동기 코드인 sum 초기화와 for 루프를 실행한다.
// 3. for 루프는 동기 작업이므로 실행되는 동안 콜 스택을 계속 점유한다.
//    - 이 동안 타이머 시간이 이미 지났더라도 F 콜백은 실행될 수 없다.
//    - 왜? 자바스크립트는 단일 스레드로 이벤트 루프를 순서대로 처리하기 때문이다.
// 4. for 루프가 끝나면 console.info('G...')가 동기적으로 실행된다.
//    - 그래서 G가 먼저 출력된다.
// 5. 파일의 동기 코드가 모두 끝나 콜 스택이 비면, 이벤트 루프가 이벤트 큐를 확인한다.
// 6. 그때 큐에 대기 중인 F 콜백을 꺼내 실행한다.
//    - 그래서 F는 G 다음에 출력된다.
