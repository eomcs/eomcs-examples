package com.eomcs.cleancode.ch10.exam03;

// 예제 1: 변경하기 쉬운 클래스 - SqlGenerator vs Sql 계층구조
public class BadAndGood1 {

  private BadAndGood1() {}

  // Bad: SQL 생성 책임이 한 클래스에 모두 모여 있다
  //   - update()가 필요해지면 이 클래스를 수정해야 한다
  //   - delete()가 필요해져도 이 클래스를 수정해야 한다
  //   - 기존 insert(), selectAll() 기능까지 영향을 받을 수 있다
  //   - 클래스가 변경에 열려 있다
  static class BadSqlGenerator {

    private final String table;

    BadSqlGenerator(String table) {
      this.table = table;
    }

    public String create() {
      return "CREATE TABLE " + table;
    }

    public String insert(String[] columns) {
      return "INSERT INTO " + table + " (" + String.join(", ", columns) + ")";
    }

    public String selectAll() {
      return "SELECT * FROM " + table;
    }

    public String findById(long id) {
      return "SELECT * FROM " + table + " WHERE id = " + id;
    }
  }

  // Good: 공통 개념을 추상 클래스로 표현
  //   - 새 SQL 종류는 새 하위 클래스로 추가한다
  //   - 기존 클래스는 수정하지 않는다
  //   - 확장에는 열려 있고 수정에는 닫혀 있다 (OCP)
  abstract static class Sql {

    protected final String table;

    protected Sql(String table) {
      this.table = table;
    }

    public abstract String generate();
  }

  static class CreateSql extends Sql {

    CreateSql(String table) {
      super(table);
    }

    @Override
    public String generate() {
      return "CREATE TABLE " + table;
    }
  }

  static class InsertSql extends Sql {

    private final String[] columns;

    InsertSql(String table, String[] columns) {
      super(table);
      this.columns = columns;
    }

    @Override
    public String generate() {
      return "INSERT INTO " + table + " (" + String.join(", ", columns) + ")";
    }
  }

  static class SelectAllSql extends Sql {

    SelectAllSql(String table) {
      super(table);
    }

    @Override
    public String generate() {
      return "SELECT * FROM " + table;
    }
  }

  static class FindByIdSql extends Sql {

    private final long id;

    FindByIdSql(String table, long id) {
      super(table);
      this.id = id;
    }

    @Override
    public String generate() {
      return "SELECT * FROM " + table + " WHERE id = " + id;
    }
  }

  // 새 기능 추가: 기존 클래스를 수정하지 않고 새 클래스로 확장한다
  static class UpdateSql extends Sql {

    private final String column;
    private final String value;
    private final long id;

    UpdateSql(String table, String column, String value, long id) {
      super(table);
      this.column = column;
      this.value = value;
      this.id = id;
    }

    @Override
    public String generate() {
      return "UPDATE " + table
          + " SET " + column + " = '" + value + "'"
          + " WHERE id = " + id;
    }
  }
}
