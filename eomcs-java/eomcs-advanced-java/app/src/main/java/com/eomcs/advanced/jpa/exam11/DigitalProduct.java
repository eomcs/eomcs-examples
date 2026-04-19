package com.eomcs.advanced.jpa.exam11;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

// exam11 - 디지털 제품 (상속 매핑 자식 엔티티)
//
// @DiscriminatorValue("DIGITAL"):
//   shop_product.dtype = 'DIGITAL' 인 행이 이 클래스로 매핑된다.
//
@Entity
@Table(name = "shop_digital_product")
@DiscriminatorValue("DIGITAL")
@PrimaryKeyJoinColumn(name = "product_id")
public class DigitalProduct extends Product {

  @Column(name = "download_url", length = 500)
  private String downloadUrl;

  @Column(name = "license_count", nullable = false)
  private int licenseCount = 1;

  public DigitalProduct() {}

  public String getDownloadUrl()        { return downloadUrl; }
  public void setDownloadUrl(String v)  { this.downloadUrl = v; }
  public int getLicenseCount()          { return licenseCount; }
  public void setLicenseCount(int v)    { this.licenseCount = v; }

  @Override
  public String toString() {
    return String.format(
        "DigitalProduct{id=%d, name='%s', price=%s, licenseCount=%d}",
        getId(), getName(), getPrice(), licenseCount);
  }
}
