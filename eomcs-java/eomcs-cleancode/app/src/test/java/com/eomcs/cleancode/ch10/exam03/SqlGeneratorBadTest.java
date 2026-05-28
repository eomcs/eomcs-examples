package com.eomcs.cleancode.ch10.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch10.exam03.BadAndGood1.BadSqlGenerator;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - SqlGenerator
//
// 문제점:
// - SQL 종류가 추가될 때마다 BadSqlGenerator 클래스를 직접 수정해야 한다.
// - 기존 create(), insert(), selectAll() 메서드가 함께 컴파일되므로 기존 기능도 영향을 받을 수 있다.
// - 새 메서드를 추가하려면 기존 클래스를 열어야 한다 — 수정에 열려 있다.
class SqlGeneratorBadTest {

  @Test
  void create_SQL을_생성한다() {
    BadSqlGenerator generator = new BadSqlGenerator("users");

    String sql = generator.create();

    assertEquals("CREATE TABLE users", sql);
  }

  @Test
  void insert_SQL을_생성한다() {
    BadSqlGenerator generator = new BadSqlGenerator("users");

    String sql = generator.insert(new String[]{"name", "email"});

    assertEquals("INSERT INTO users (name, email)", sql);
  }

  @Test
  void selectAll_SQL을_생성한다() {
    BadSqlGenerator generator = new BadSqlGenerator("users");

    String sql = generator.selectAll();

    assertEquals("SELECT * FROM users", sql);
  }

  @Test
  void findById_SQL을_생성한다() {
    BadSqlGenerator generator = new BadSqlGenerator("users");

    String sql = generator.findById(42L);

    assertEquals("SELECT * FROM users WHERE id = 42", sql);
  }
}
