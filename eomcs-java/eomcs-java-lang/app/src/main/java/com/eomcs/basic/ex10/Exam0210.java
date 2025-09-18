package com.eomcs.basic.ex10;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

public class Exam0210 {
  public static void main(String[] args) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Calendar cal = Calendar.getInstance();
    cal.set(2021, 10, 23, 18, 0, 0);

    System.out.println(df.format(new Date(cal.getTimeInMillis())));

    Duration duration1 = Duration.ofMillis(cal.getTimeInMillis());
    Duration duration2 = duration1.minusMillis(System.currentTimeMillis());

    System.out.format("%d일 %d시간 %d분 %d초\n", 
        duration2.toDaysPart(), 
        duration2.toHoursPart(),
        duration2.toMinutesPart(),
        duration2.toSecondsPart());

  }
}
