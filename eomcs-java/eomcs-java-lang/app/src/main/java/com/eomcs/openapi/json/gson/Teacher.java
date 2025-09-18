package com.eomcs.openapi.json.gson;

public class Teacher extends Member {
  String major;
  int hourPay;

  public String getMajor() {
    return major;
  }
  public void setMajor(String major) {
    this.major = major;
  }
  public int getHourPay() {
    return hourPay;
  }
  public void setHourPay(int hourPay) {
    this.hourPay = hourPay;
  }
  @Override
  public String toString() {
    return "Teacher [major=" + major + ", hourPay=" + hourPay + ", toString()=" + super.toString()
    + "]";
  }



}
