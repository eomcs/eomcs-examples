package com.eomcs.web.vo;

import java.io.Serializable;
import java.sql.Date;

public class Member implements Serializable {

  private static final long serialVersionUID = 20200131L;

  private int no;
  private String name;
  private String email;
  private String password;
  private String photo;
  private String tel;
  private Date registeredDate;

  @Override
  public String toString() {
    return "Member [no=" + no + ", name=" + name + ", email=" + email + ", password=" + password
        + ", photo=" + photo + ", tel=" + tel + ", registeredDate=" + registeredDate + "]";
  }

  public static Member valueOf(String csv) {
    String[] data = csv.split(",");

    Member member = new Member();
    member.setNo(Integer.parseInt(data[0]));
    member.setName(data[1]);
    member.setEmail(data[2]);
    member.setPassword(data[3]);
    member.setPhoto(data[4]);
    member.setTel(data[5]);
    member.setRegisteredDate(Date.valueOf(data[6]));

    return member;
  }

  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%s,%s", this.getNo(), this.getName(), this.getEmail(),
        this.getPassword(), this.getPhoto(), this.getTel(), this.getRegisteredDate());
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (email == null ? 0 : email.hashCode());
    result = prime * result + (name == null ? 0 : name.hashCode());
    result = prime * result + no;
    result = prime * result + (password == null ? 0 : password.hashCode());
    result = prime * result + (photo == null ? 0 : photo.hashCode());
    result = prime * result + (registeredDate == null ? 0 : registeredDate.hashCode());
    result = prime * result + (tel == null ? 0 : tel.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Member other = (Member) obj;
    if (email == null) {
      if (other.email != null) {
        return false;
      }
    } else if (!email.equals(other.email)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (no != other.no) {
      return false;
    }
    if (password == null) {
      if (other.password != null) {
        return false;
      }
    } else if (!password.equals(other.password)) {
      return false;
    }
    if (photo == null) {
      if (other.photo != null) {
        return false;
      }
    } else if (!photo.equals(other.photo)) {
      return false;
    }
    if (registeredDate == null) {
      if (other.registeredDate != null) {
        return false;
      }
    } else if (!registeredDate.equals(other.registeredDate)) {
      return false;
    }
    if (tel == null) {
      if (other.tel != null) {
        return false;
      }
    } else if (!tel.equals(other.tel)) {
      return false;
    }
    return true;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public Date getRegisteredDate() {
    return registeredDate;
  }

  public void setRegisteredDate(Date registeredDate) {
    this.registeredDate = registeredDate;
  }
}
