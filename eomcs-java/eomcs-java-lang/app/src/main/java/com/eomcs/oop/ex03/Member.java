package com.eomcs.oop.ex03;

public class Member {
  public static final int GUEST = 0;
  public static final int MEMBER = 1;
  public static final int MANAGER = 2;

  String id;
  String password;
  int type; // 0: 손님, 1: 회원, 2: 관리자
}
