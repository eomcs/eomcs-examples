package com.eomcs.oop;

import java.util.Objects;

public class Board {
  int no;
  String title;
  String content;

  public Board(int no, String title, String content) {
    this.no = no;
    this.title = title;
    this.content = content;
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



  @Override
  public String toString() {
    return "Board [no=" + no + ", title=" + title + ", content=" + content + "]";
  }
  @Override
  public int hashCode() {
    return Objects.hash(content, no, title);
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Board other = (Board) obj;
    return Objects.equals(content, other.content) && no == other.no
        && Objects.equals(title, other.title);
  }

  public static BoardBuilder builder() {
    return new BoardBuilder();
  }

  public static class BoardBuilder {
    int no;
    String title;
    String content;

    public BoardBuilder no(int no) {
      this.no = no;
      return this;
    }
    public BoardBuilder title(String title) {
      this.title = title;
      return this;
    }
    public BoardBuilder content(String content) {
      this.content = content;
      return this;
    }
    public Board build() {
      return new Board(this.no, this.title, this.content);
    }
  }

}
