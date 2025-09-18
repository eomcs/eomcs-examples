package com.eomcs.web.vo;

import java.io.Serializable;
import java.sql.Date;

public class Board implements Serializable {

  private static final long serialVersionUID = 20200131L;

  private int no;
  private String contents;
  private Date createdDate;
  private int viewCount;
  private String writer;

  @Override
  public String toString() {
    return "Board [no=" + no + ", contents=" + contents + ", createdDate=" + createdDate
        + ", viewCount=" + viewCount + ", writer=" + writer + "]";
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
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

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }



}
