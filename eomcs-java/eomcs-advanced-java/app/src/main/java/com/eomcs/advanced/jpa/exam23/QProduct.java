package com.eomcs.advanced.jpa.exam23;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// exam23 - Querydsl Q-타입 (수동 작성본)
//
public class QProduct extends EntityPathBase<Product> {

  public static final QProduct product = new QProduct("product");

  public final NumberPath<Long> id        = createNumber("id", Long.class);
  public final StringPath dtype           = createString("dtype");
  public final StringPath name            = createString("name");
  public final NumberPath<BigDecimal> price = createNumber("price", BigDecimal.class);
  public final NumberPath<Integer> stock  = createNumber("stock", Integer.class);
  public final DateTimePath<LocalDateTime> createdAt =
      createDateTime("createdAt", LocalDateTime.class);
  public final DateTimePath<LocalDateTime> updatedAt =
      createDateTime("updatedAt", LocalDateTime.class);

  public QProduct(String variable) {
    super(Product.class, variable);
  }

  public QProduct(com.querydsl.core.types.Path<? extends Product> path) {
    super(path.getType(), path.getMetadata());
  }

  public QProduct(PathMetadata metadata) {
    super(Product.class, metadata);
  }
}
