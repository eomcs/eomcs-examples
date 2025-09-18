package com.eomcs.mybatis.ex14;

import java.io.Serializable;
import java.sql.Date;

public class Board implements Serializable {
  private static final long serialVersionUID = 1L;

  int no;
  String title;
  String content;
  Date registeredDate;

  @Override
  public String toString() {
    return "Board [no=" + no + ", title=" + title + ", content=" + content + ", registeredDate=" + registeredDate
        + "]";
  }

  // 셋터와 겟터 생성
  public int getNo() {
    return no;
  }
  public Board setNo(int no) {
    this.no = no;
    return this;
  }
  public String getTitle() {
    return title;
  }
  public Board setTitle(String title) {
    this.title = title;
    return this;
  }
  public String getContent() {
    return content;
  }
  public Board setContent(String content) {
    this.content = content;
    return this;
  }
  public Date getRegisteredDate() {
    return registeredDate;
  }
  public Board setRegisteredDate(Date registeredDate) {
    this.registeredDate = registeredDate;
    return this;
  }
}
