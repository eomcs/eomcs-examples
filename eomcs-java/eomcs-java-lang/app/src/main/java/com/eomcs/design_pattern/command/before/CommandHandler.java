package com.eomcs.design_pattern.command.before;

public class CommandHandler {
  
  public void addBoard() {
    System.out.println("게시물 입력 처리!");
  }
  
  public void detailBoard() {
    System.out.println("게시물 상세조회 처리!");
  }
  
  public void listBoard() {
    System.out.println("게시물 목록조회 처리!");
  }
  
  public void updateBoard() {
    System.out.println("게시물 변경 처리!");
  }
  
  public void deleteBoard() {
    System.out.println("게시물 삭제 처리!");
  }
  public void addMember() {
    System.out.println("회원 입력 처리!");
  }
  
  public void detailMember() {
    System.out.println("회원 상세조회 처리!");
  }
  
  public void listMember() {
    System.out.println("회원 목록조회 처리!");
  }
  
  public void updateMember() {
    System.out.println("회원 변경 처리!");
  }
  
  public void deleteMember() {
    System.out.println("회원 삭제 처리!");
  }  
}
