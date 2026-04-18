package com.eomcs.advanced.concurrency.exam06;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

// ScheduledExecutorService:
//
// 지정 시간 뒤에 작업을 실행하거나, 일정한 간격으로 반복 실행할 때 사용한다.
// Timer/TimerTask보다 강력하며 스레드 풀을 함께 사용할 수 있다.
//
// 주요 메서드:
//   schedule(task, delay, unit)
//     - delay 뒤에 한 번 실행
//
//   scheduleAtFixedRate(task, initialDelay, period, unit)
//     - 작업 시작 시점을 기준으로 period 간격 유지
//     - 작업 실행 시간이 period보다 짧으면: period 간격 유지
//     - 작업 실행 시간이 period보다 길면: 다음 실행을 즉시 시작(간격 0)
//     - 예: 0초 시작, 1초 시작, 2초 시작 ... (period=1s, 작업<1s인 경우)
//
//   scheduleWithFixedDelay(task, initialDelay, delay, unit)
//     - 이전 작업 종료 후 delay만큼 기다렸다가 다음 실행
//     - 작업 실행 시간과 무관하게 종료→delay 간격이 보장된다.
//     - 예: 0초 시작 → 종료 → 1초 대기 → 다음 시작 (작업시간+delay 간격)
//
// scheduleAtFixedRate vs scheduleWithFixedDelay:
//   FixedRate  : "매 N초마다 실행" - 작업 시간이 짧고 일정할 때 적합
//   FixedDelay : "끝나고 N초 후 실행" - 작업 시간이 가변적이거나 과부하 방지가 필요할 때 적합
//
// 반환값 ScheduledFuture:
//   cancel(false): 실행 중인 작업은 내버려 두고 이후 반복만 취소
//   cancel(true) : 실행 중인 작업에도 interrupt를 보내 취소 시도

public class App4 {

  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

  public static void main(String[] args) throws InterruptedException {

    System.out.println("[스케줄링] ScheduledExecutorService");
    System.out.println("지연 실행, 고정 주기 실행, 고정 지연 실행을 비교한다.");
    System.out.println();

    // newScheduledThreadPool(2): 스케줄링 전용 풀. 2개의 스레드로 동시에 여러 작업을 처리한다.
    // - fixedRate와 fixedDelay가 동시에 실행되므로 최소 2개 스레드가 필요하다.
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    // schedule(): 1초 뒤에 한 번만 실행한다.
    // - Timer.schedule()과 유사하지만 스레드 풀을 사용하므로 더 안전하다.
    scheduler.schedule(
        () -> System.out.printf("  %s: 1초 뒤 한 번 실행%n", now()),
        1,         // delay: 1초 뒤 실행
        TimeUnit.SECONDS);

    // scheduleAtFixedRate(): 작업 시작 시점을 기준으로 1초 간격을 유지한다.
    // - initialDelay=0: 즉시 첫 실행
    // - period=1: 이전 시작 시점으로부터 1초 후에 다음 실행
    // - 작업(400ms)이 period(1000ms)보다 짧으므로 → 실제 간격 ≈ 1초
    // - 만약 작업이 period보다 길면 → 다음 실행이 종료 즉시 시작됨 (간격 0)
    ScheduledFuture<?> fixedRate =
        scheduler.scheduleAtFixedRate(
            () -> {
              System.out.printf("  %s: fixed-rate 시작%n", now());
              sleep(400); // 400ms 작업 (< period 1000ms이므로 간격 유지)
              System.out.printf("  %s: fixed-rate 종료%n", now());
            },
            0,  // initialDelay: 첫 실행까지 대기 시간 (0 = 즉시)
            1,  // period: 시작-시작 간격 (1초)
            TimeUnit.SECONDS);

    // scheduleWithFixedDelay(): 이전 작업 종료 후 1초를 기다린 뒤 다음을 실행한다.
    // - initialDelay=0: 즉시 첫 실행
    // - delay=1: 이전 종료 후 1초 대기
    // - 작업(400ms) + delay(1000ms) = 약 1.4초 간격으로 반복된다.
    // - 작업 시간이 달라져도 "끝나고 1초 쉬는" 간격은 항상 보장된다.
    ScheduledFuture<?> fixedDelay =
        scheduler.scheduleWithFixedDelay(
            () -> {
              System.out.printf("  %s: fixed-delay 시작%n", now());
              sleep(400); // 400ms 작업 + delay 1000ms = 약 1.4초 간격
              System.out.printf("  %s: fixed-delay 종료%n", now());
            },
            0,  // initialDelay: 첫 실행까지 대기 시간 (0 = 즉시)
            1,  // delay: 이전 작업 종료 후 대기 시간 (1초)
            TimeUnit.SECONDS);

    Thread.sleep(3500); // 약 3.5초 동안 반복 실행 관찰

    // cancel(false): 현재 실행 중인 작업은 끝까지 두고, 이후 예약된 반복만 취소한다.
    // cancel(true) : 실행 중인 작업에도 interrupt를 보낸다.
    fixedRate.cancel(false);
    fixedDelay.cancel(false);
    System.out.println();
    System.out.println("  반복 작업 취소 요청");

    // ScheduledExecutorService도 ExecutorService를 상속하므로 동일하게 종료해야 한다.
    scheduler.shutdown();
    boolean finished = scheduler.awaitTermination(2, TimeUnit.SECONDS);
    if (!finished) {
      scheduler.shutdownNow(); // 제한 시간 초과 시 강제 종료
    }

    System.out.println("→ 스케줄러도 ExecutorService이므로 작업 후 반드시 종료해야 한다.");
  }

  private static String now() {
    return LocalTime.now().format(TIME_FORMATTER);
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
