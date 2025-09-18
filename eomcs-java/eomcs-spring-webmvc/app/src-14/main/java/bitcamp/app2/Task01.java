package bitcamp.app2;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task01 {
  
  // cron="초 분 시 일 월 요일"
  @Scheduled(cron="*/10 * * * * *")
  public void m1() {
    System.out.println("m1()");
  }
  
  @Scheduled(cron="0 */1 * * * *")
  public void m2() {
    System.out.println("m2()");
  }
  
  @Scheduled(cron="0 0 */1 * * *")
  public void m3() {
    System.out.println("m3()");
  }
}
