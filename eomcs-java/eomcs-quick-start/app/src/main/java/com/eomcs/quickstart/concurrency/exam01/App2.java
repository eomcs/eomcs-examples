package com.eomcs.quickstart.concurrency.exam01;

// Thread 제어 메서드:
//
// sleep(ms):
//   - 현재 스레드를 지정한 시간(밀리초) 동안 TIMED_WAITING 상태로 전환한다.
//   - 시간이 지나면 자동으로 RUNNABLE 상태로 돌아온다.
//   - InterruptedException이 checked exception이므로 반드시 처리해야 한다.
//
// join():
//   - 호출한 스레드가 종료될 때까지 현재 스레드를 WAITING 상태로 전환한다.
//   - join(ms): 최대 ms 밀리초 동안만 대기한다. 이후 시간 초과로 반환된다.
//
// interrupt():
//   - 대상 스레드에 인터럽트 신호를 보낸다.
//   - 대상 스레드가 sleep() / join() / wait() 등으로 대기 중이면 InterruptedException이 발생한다.
//   - 대기 중이 아니면 인터럽트 플래그만 true로 설정된다.
//   - isInterrupted(): 인터럽트 플래그를 반환한다 (플래그 유지).
//   - Thread.interrupted(): 인터럽트 플래그를 반환하고 false로 초기화한다.
//
// 우선순위 (Priority):
//   - 1(MIN) ~ 10(MAX), 기본값 5(NORM).
//   - 높은 우선순위 스레드가 더 자주 실행될 가능성이 높지만, OS 스케줄러에 따라 보장되지 않는다.
//

public class App2 {

  public static void main(String[] args) throws InterruptedException {

    // 1. sleep() - 스레드 일시 정지
    System.out.println("[1. Thread.sleep() - 일시 정지]");
    Thread sleepThread =
        new Thread(
            () -> {
              System.out.println("  작업 시작");
              try {
                Thread.sleep(300); // 0.3초 대기
                System.out.println("  300ms 후 재개");
              } catch (InterruptedException e) {
                System.out.println("  sleep 중 인터럽트 발생");
              }
            },
            "SleepThread");
    sleepThread.start();
    sleepThread.join();

    // 2. join() - 스레드 종료 대기
    System.out.println("\n[2. join() - 종료 대기]");
    Thread worker =
        new Thread(
            () -> {
              System.out.println("  worker: 시작");
              try {
                Thread.sleep(200);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
              System.out.println("  worker: 종료");
            },
            "Worker");

    worker.start();
    System.out.println("  main: worker 종료 대기 중...");
    worker.join(); // worker가 종료될 때까지 main 대기
    System.out.println("  main: worker 종료 확인, 계속 진행");

    // 3. join(ms) - 제한 시간 대기
    System.out.println("\n[3. join(ms) - 제한 시간 대기]");
    Thread longTask =
        new Thread(
            () -> {
              try {
                Thread.sleep(1000); // 1초 작업
                System.out.println("  longTask: 완료");
              } catch (InterruptedException e) {
                System.out.println("  longTask: 인터럽트");
              }
            },
            "LongTask");

    longTask.start();
    longTask.join(200); // 최대 200ms만 대기
    if (longTask.isAlive()) {
      System.out.println("  main: 200ms 초과 - longTask가 아직 실행 중");
    }
    longTask.interrupt(); // 정리
    longTask.join();

    // 4. interrupt() - 스레드 인터럽트
    System.out.println("\n[4. interrupt() - 인터럽트]");
    Thread interruptTarget =
        new Thread(
            () -> {
              System.out.println("  target: 긴 작업 시작");
              try {
                Thread.sleep(5000); // 5초 대기 중 인터럽트 받음
                System.out.println("  target: 정상 완료");
              } catch (InterruptedException e) {
                // sleep 중 interrupt() 호출 → InterruptedException 발생
                System.out.println("  target: sleep 중 인터럽트 수신, 작업 취소");
              }
            },
            "InterruptTarget");

    interruptTarget.start();
    Thread.sleep(100); // main 스레드 잠깐 대기
    interruptTarget.interrupt(); // 인터럽트 신호 전송
    interruptTarget.join();

    // 5. isInterrupted() - 인터럽트 플래그 확인
    System.out.println("\n[5. isInterrupted() - 플래그 확인]");
    Thread flagCheck =
        new Thread(
            () -> {
              // sleep 없이 루프로 작업하는 경우 - 인터럽트 플래그로 종료 여부 판단
              int count = 0;
              while (!Thread.currentThread().isInterrupted()) {
                count++;
                if (count % 100_000 == 0) {
                  System.out.println("  flagCheck: 진행 중 count=" + count);
                }
              }
              System.out.println("  flagCheck: 인터럽트 플래그 감지, 종료. count=" + count);
            },
            "FlagCheck");

    flagCheck.start();
    Thread.sleep(50); // main 스레드 잠깐 대기
    flagCheck.interrupt(); // 인터럽트 신호 전송
    flagCheck.join();

    // 6. 스레드 우선순위
    System.out.println("\n[6. 스레드 우선순위]");
    Thread low =
        new Thread(
            () -> System.out.println("  low  priority: " + Thread.currentThread().getPriority()),
            "Low");
    Thread norm =
        new Thread(
            () -> System.out.println("  norm priority: " + Thread.currentThread().getPriority()),
            "Norm");
    Thread high =
        new Thread(
            () -> System.out.println("  high priority: " + Thread.currentThread().getPriority()),
            "High");

    low.setPriority(Thread.MIN_PRIORITY); // 1
    norm.setPriority(Thread.NORM_PRIORITY); // 5 (기본값)
    high.setPriority(Thread.MAX_PRIORITY); // 10

    high.start();
    low.start();
    norm.start();
    high.join();
    low.join();
    norm.join();
    // 우선순위가 높다고 실행 순서가 보장되지는 않는다

    System.out.println("\nmain 스레드 종료");
  }
}
