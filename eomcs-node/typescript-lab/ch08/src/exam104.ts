/**
 * JavaScript 이벤트 루프: 비동기 작업의 콜백은 메인 스레드에서 하나씩 실행된다.
 */

export {}

function scheduleTask(name: string, delay: number) {
  console.info(`${name}: scheduled`)

  setTimeout(() => {
    console.info(`${name}: callback`)
  }, delay)
}

scheduleTask('H', 5)
scheduleTask('I', 1)

console.info('J: all tasks scheduled')

// 1. scheduleTask()를 호출한다.
//   - H를 출력한다.
//   - 5ms가 지나면, 네이티브 타임아웃 API는 이벤트 루프에 I 콜백을 넣는다.
// 2. scheduleTask()를 호출한다.
//   - I를 출력한다.
//   - 1ms가 지나면, 네이티브 타임아웃 API는 이벤트 루프에 H 콜백을 넣는다.
// 3. console.info('J...')를 동기적으로 실행한다.
//   - 그래서 J가 가장 먼저 출력된다.
// 4. 전역 코드를 모두 실행하면(콜 스택이 비게 되면), 이벤트 큐에 다음에 실행할 태스크가 있는지 확인한다.
//   - 있으면, 실행한다.
//   - 없으면, Active Handle(타이머, I/O 등)의 존재 여부를 확인한다.
//     - Active Handle 이 없다면 프로그램이 종료된다.
//     - Active Handle이 있다면, 이벤트 루프는 대기한다.
// 5. I 콜백 태스크가 이벤트 큐에 먼저 들어왔으므로 I 콜백을 먼저 실행한다.
// 6. I 콜백의 실행이 끝나면 콜 스택이 비었기 때문에 이벤트 큐에서 다음 태스크가 있는지 확인한다.
// 7. H 콜백 태스크가 이벤트 큐에 들어오면 실행한다.
// 8. 콜 스택이 비었고 이벤트 큐도 비었고, Active Handle도 없으므로 프로그램이 종료된다.