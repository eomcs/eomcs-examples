package com.eomcs.cleancode.ch13.exam07;

import java.util.ArrayList;
import java.util.List;

// 예제 3: 리포트 생성 - 오래 걸리는 계산은 락 밖에서 수행하라
public class BadAndGood3 {

  private BadAndGood3() {}

  static class User {

    private final long id;

    User(long id) {
      this.id = id;
    }

    long id() {
      return id;
    }
  }

  static class Report {

    private final long userId;

    Report(long userId) {
      this.userId = userId;
    }

    long userId() {
      return userId;
    }
  }

  // Bad: 리포트 생성까지 락 안에서 실행된다
  //   - generateReport()는 오래 걸릴 수 있다
  //   - 실제 공유 자원 접근은 reports.add(report)뿐이다
  //   - 불필요한 코드까지 직렬화된다
  static class BadReportService {

    private final List<Report> reports = new ArrayList<>();

    public synchronized void generateAndAddReport(User user) {
      Report report = generateReport(user);  // 오래 걸리는 계산이 락 안에 있다
      reports.add(report);
    }

    private Report generateReport(User user) {
      return new Report(user.id());
    }

    public synchronized List<Report> getReports() {
      return new ArrayList<>(reports);
    }
  }

  // Good: 리포트 생성은 락 밖에서 수행하고, 공유 리스트 접근만 동기화한다
  //   - 동기화 구간이 최소화된다
  //   - 리포트 생성이 병렬로 진행될 수 있다
  //   - 보호 대상이 명확해진다
  static class ReportService {

    private final List<Report> reports = new ArrayList<>();

    public void generateAndAddReport(User user) {
      Report report = generateReport(user);  // 락 밖에서 생성

      synchronized (this) {
        reports.add(report);  // 공유 리스트 접근만 보호
      }
    }

    private Report generateReport(User user) {
      return new Report(user.id());
    }

    public synchronized List<Report> getReports() {
      return new ArrayList<>(reports);
    }
  }
}
