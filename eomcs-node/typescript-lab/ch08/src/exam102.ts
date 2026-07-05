/**
 * JavaScript 이벤트 루프: 0ms 타이머도 현재 콜 스택 뒤에 실행된다.
 */

export {}

setTimeout(() => console.info('D: timeout 0ms'), 0)
console.info('E: still synchronous')

// D는 0ms로 예약했지만 E보다 먼저 실행되지 않는다.
//
// 실행 순서:
// 1. setTimeout(...) 호출
//    - 콜백 () => console.info('D...')를 타이머로 예약한다.
//    - 콜백을 바로 실행하지 않는다.
// 2. 바로 다음 줄 실행한다.
//    - 그래서 E가 먼저 출력된다.
// 3. 현재 실행 중인 동기 코드가 모두 끝나서 콜 스택이 비면, 
//    이벤트 루프가 큐에 들어온 타이머 콜백을 꺼낸다.
// 4. 그때 D가 출력된다.
