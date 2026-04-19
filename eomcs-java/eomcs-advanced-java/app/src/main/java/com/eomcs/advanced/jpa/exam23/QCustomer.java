package com.eomcs.advanced.jpa.exam23;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import java.time.LocalDateTime;

// exam23 - Querydsl Q-타입 (수동 작성본)
//
// 실제 프로젝트에서는 annotationProcessor로 자동 생성한다:
//   annotationProcessor "com.querydsl:querydsl-apt:버전:jakarta"
//   annotationProcessor "jakarta.persistence:jakarta.persistence-api:3.1.0"
//   → build 시 build/generated/sources/annotationProcessor/... 에 QCustomer.java 자동 생성
//
// 이 파일은 자동 생성 Q-타입의 구조를 학습하기 위한 수동 작성본이다.
// EntityPathBase<T>를 상속하고, 각 필드는 타입별 Path 클래스로 선언한다.
//
public class QCustomer extends EntityPathBase<Customer> {

  public static final QCustomer customer = new QCustomer("customer");

  public final NumberPath<Long> id        = createNumber("id", Long.class);
  public final StringPath name            = createString("name");
  public final StringPath email           = createString("email");
  public final StringPath city            = createString("city");
  public final StringPath street          = createString("street");
  public final StringPath zipcode         = createString("zipcode");
  public final DateTimePath<LocalDateTime> createdAt =
      createDateTime("createdAt", LocalDateTime.class);
  public final DateTimePath<LocalDateTime> updatedAt =
      createDateTime("updatedAt", LocalDateTime.class);

  public QCustomer(String variable) {
    super(Customer.class, variable);
  }

  public QCustomer(com.querydsl.core.types.Path<? extends Customer> path) {
    super(path.getType(), path.getMetadata());
  }

  public QCustomer(PathMetadata metadata) {
    super(Customer.class, metadata);
  }
}
