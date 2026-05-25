package com.eomcs.cleancode.ch06.exam04;

// 예제 1: DTO (Data Transfer Object)
// - 필드 중심, getter/setter 중심
// - 비즈니스 로직 없음
// - 계층 간 데이터 전달 목적
// - 객체라기보다 자료 구조에 가깝다.
public class BadAndGood1 {

  private BadAndGood1() {}

  // DTO: 데이터를 운반하는 단순한 자료 구조다.
  // - DB → Service → Controller → View 처럼 계층을 오갈 때 사용한다.
  // - 비즈니스 규칙을 여기에 넣으면 안 된다.
  static class AddressDto {
    private String street;
    private String city;
    private String state;
    private String zip;

    AddressDto(String street, String city, String state, String zip) {
      this.street = street;
      this.city = city;
      this.state = state;
      this.zip = zip;
    }

    public String getStreet() { return street; }
    public String getCity()   { return city; }
    public String getState()  { return state; }
    public String getZip()    { return zip; }

    public void setStreet(String street) { this.street = street; }
    public void setCity(String city)     { this.city = city; }
    public void setState(String state)   { this.state = state; }
    public void setZip(String zip)       { this.zip = zip; }
  }

  // 사용 예: 계층 간 데이터 전달에 사용한다.
  static class AddressService {
    AddressDto findByUserId(Long userId) {
      // DB에서 조회한 결과를 DTO에 담아 반환한다
      return new AddressDto("123 Main St", "Seoul", "Gangnam", "06100");
    }
  }

  static class AddressController {
    void printAddress(Long userId) {
      AddressService service = new AddressService();
      AddressDto dto = service.findByUserId(userId);
      System.out.println(dto.getCity() + " " + dto.getStreet());
    }
  }
}
