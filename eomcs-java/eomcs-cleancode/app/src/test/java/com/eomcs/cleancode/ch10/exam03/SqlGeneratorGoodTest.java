package com.eomcs.cleancode.ch10.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch10.exam03.BadAndGood1.CreateSql;
import com.eomcs.cleancode.ch10.exam03.BadAndGood1.FindByIdSql;
import com.eomcs.cleancode.ch10.exam03.BadAndGood1.InsertSql;
import com.eomcs.cleancode.ch10.exam03.BadAndGood1.SelectAllSql;
import com.eomcs.cleancode.ch10.exam03.BadAndGood1.UpdateSql;
import org.junit.jupiter.api.Test;

// 예제 1: 변경하기 쉬운 클래스 - Sql 계층구조
//
// Bad: SQL 종류가 추가될 때마다 BadSqlGenerator 클래스를 수정해야 한다.
// Good: 새 SQL 종류는 새 Sql 하위 클래스로 추가한다.
//       기존 CreateSql, InsertSql 등은 수정하지 않는다.
//       확장에는 열려 있고 수정에는 닫혀 있다 (OCP).
class SqlGeneratorGoodTest {

  @Test
  void CreateSql이_create_문을_생성한다() {
    CreateSql sql = new CreateSql("users");

    assertEquals("CREATE TABLE users", sql.generate());
  }

  @Test
  void InsertSql이_insert_문을_생성한다() {
    InsertSql sql = new InsertSql("users", new String[]{"name", "email"});

    assertEquals("INSERT INTO users (name, email)", sql.generate());
  }

  @Test
  void SelectAllSql이_select_전체_문을_생성한다() {
    SelectAllSql sql = new SelectAllSql("users");

    assertEquals("SELECT * FROM users", sql.generate());
  }

  @Test
  void FindByIdSql이_id_조회_문을_생성한다() {
    FindByIdSql sql = new FindByIdSql("users", 42L);

    assertEquals("SELECT * FROM users WHERE id = 42", sql.generate());
  }

  @Test
  void UpdateSql이_update_문을_생성한다() {
    // 새 기능: 기존 클래스를 수정하지 않고 새 클래스 추가만으로 확장한다
    UpdateSql sql = new UpdateSql("users", "name", "Alice", 42L);

    assertEquals("UPDATE users SET name = 'Alice' WHERE id = 42", sql.generate());
  }
}
