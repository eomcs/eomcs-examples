package com.eomcs.web.javascript;

import java.sql.Date;

public class Board {
  int no;
  String title;
  String content;
  Date createdDate;
  int viewCount;

  public Board() {}

  public Board(int no, String title, Date createdDate, int viewCount) {
    this.no = no;
    this.title = title;
    this.createdDate = createdDate;
    this.viewCount = viewCount;
  }

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }



}
