package com.eomcs.openapi.json.jackson;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// JSON 문자열로 넘어 온 값을 객체로 만들 때
// JSON 프로퍼티의 이름과 일치하는 객체 프로퍼티가 없다면 예외가 발생한다.
// 그럴 경우 다음과 같이
// JSON으로 넘어온 값 중에서 객체에 저장할 대상이 아닌 프로퍼티에 대해 표시를 해야 한다.
// 그 방법은 다음과 같이 애노테이션을 이용하여 무시할 JSON 프로퍼티의 이름을 명시한다.
//
@JsonIgnoreProperties(value= {"name", "email", "password", "photo", "tel"})
public class Board {
  private int no;
  private String title;
  private String content;
  private Member writer;
  private Date registeredDate;
  private int viewCount;
  private int like;

  @Override
  public String toString() {
    return "Board [no=" + no + ", title=" + title + ", content=" + content + ", writer=" + writer
        + ", registeredDate=" + registeredDate + ", viewCount=" + viewCount + ", like=" + like
        + "]";
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
  public Date getRegisteredDate() {
    return registeredDate;
  }
  public void setRegisteredDate(Date registeredDate) {
    this.registeredDate = registeredDate;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }
  public int getLike() {
    return like;
  }
  public void setLike(int like) {
    this.like = like;
  }
  public Member getWriter() {
    return writer;
  }
  public void setWriter(Member writer) {
    this.writer = writer;
  }

}
