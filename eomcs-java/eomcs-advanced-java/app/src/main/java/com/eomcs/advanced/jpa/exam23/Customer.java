package com.eomcs.advanced.jpa.exam23;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "shop_customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "email", nullable = false, length = 200, unique = true)
  private String email;

  @Column(name = "city", length = 100)
  private String city;

  @Column(name = "street", length = 200)
  private String street;

  @Column(name = "zipcode", length = 20)
  private String zipcode;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Customer() {}

  public Long getId()                        { return id; }
  public String getName()                    { return name; }
  public void setName(String v)              { this.name = v; }
  public String getEmail()                   { return email; }
  public void setEmail(String v)             { this.email = v; }
  public String getCity()                    { return city; }
  public void setCity(String v)              { this.city = v; }
  public String getStreet()                  { return street; }
  public void setStreet(String v)            { this.street = v; }
  public String getZipcode()                 { return zipcode; }
  public void setZipcode(String v)           { this.zipcode = v; }
  public LocalDateTime getCreatedAt()        { return createdAt; }
  public void setCreatedAt(LocalDateTime v)  { this.createdAt = v; }
  public LocalDateTime getUpdatedAt()        { return updatedAt; }
  public void setUpdatedAt(LocalDateTime v)  { this.updatedAt = v; }

  @Override
  public String toString() {
    return String.format(
        "Customer{id=%d, name='%s', email='%s', city='%s'}",
        id, name, email, city);
  }
}
