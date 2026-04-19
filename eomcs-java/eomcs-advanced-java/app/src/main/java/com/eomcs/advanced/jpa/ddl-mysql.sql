-- =============================================================================
-- JPA 예제용 테이블 정의
-- 대상 DB: MySQL / MariaDB
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 기존 테이블 삭제 (FK 의존성 역순)
-- -----------------------------------------------------------------------------
DROP TABLE IF EXISTS shop_cart_item;
DROP TABLE IF EXISTS shop_cart;
DROP TABLE IF EXISTS shop_order_item;
DROP TABLE IF EXISTS shop_orders;
DROP TABLE IF EXISTS shop_product_category;
DROP TABLE IF EXISTS shop_digital_product;
DROP TABLE IF EXISTS shop_physical_product;
DROP TABLE IF EXISTS shop_product;
DROP TABLE IF EXISTS shop_category;
DROP TABLE IF EXISTS shop_customer;

-- -----------------------------------------------------------------------------
-- 1. shop_customer (고객)
--    - @Embeddable Address 매핑 예시용 컬럼(city, street, zipcode) 포함
--    - Auditing 예시용 created_at, updated_at 포함
-- -----------------------------------------------------------------------------
CREATE TABLE shop_customer (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(200) NOT NULL,
    city       VARCHAR(100),
    street     VARCHAR(200),
    zipcode    VARCHAR(20),
    created_at DATETIME,
    updated_at DATETIME,
    PRIMARY KEY (id),
    UNIQUE KEY uk_shop_customer_email (email)
);

-- -----------------------------------------------------------------------------
-- 2. shop_category (카테고리)
--    - 자기 참조 연관관계: parent_id → shop_category.id
--    - 계층 구조 예시: 전자제품 > 노트북 > 게이밍노트북
-- -----------------------------------------------------------------------------
CREATE TABLE shop_category (
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(100) NOT NULL,
    parent_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY fk_shop_category_parent (parent_id) REFERENCES shop_category (id)
);

-- -----------------------------------------------------------------------------
-- 3. shop_product (제품 - 상속 매핑 부모)
--    - dtype: 상속 매핑 구분자 ('PHYSICAL' / 'DIGITAL')
--    - SINGLE_TABLE / JOINED / TABLE_PER_CLASS 세 전략 비교 예시
-- -----------------------------------------------------------------------------
CREATE TABLE shop_product (
    id         BIGINT         NOT NULL AUTO_INCREMENT,
    dtype      VARCHAR(20)    NOT NULL,
    name       VARCHAR(200)   NOT NULL,
    price      DECIMAL(12, 2) NOT NULL,
    stock      INT            NOT NULL DEFAULT 0,
    created_at DATETIME,
    updated_at DATETIME,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------------------------------
-- 4. shop_physical_product (실물 제품 - 상속 매핑 자식, JOINED 전략)
-- -----------------------------------------------------------------------------
CREATE TABLE shop_physical_product (
    product_id   BIGINT         NOT NULL,
    weight       DECIMAL(10, 3) COMMENT '단위: kg',
    shipping_fee DECIMAL(10, 2) COMMENT '단위: 원',
    PRIMARY KEY (product_id),
    FOREIGN KEY fk_shop_physical_product (product_id) REFERENCES shop_product (id)
);

-- -----------------------------------------------------------------------------
-- 5. shop_digital_product (디지털 제품 - 상속 매핑 자식, JOINED 전략)
-- -----------------------------------------------------------------------------
CREATE TABLE shop_digital_product (
    product_id     BIGINT       NOT NULL,
    download_url   VARCHAR(500),
    license_count  INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (product_id),
    FOREIGN KEY fk_shop_digital_product (product_id) REFERENCES shop_product (id)
);

-- -----------------------------------------------------------------------------
-- 6. shop_product_category (제품-카테고리 다대다 중간 테이블)
--    - @ManyToMany 및 연결 엔티티 리팩토링 예시
-- -----------------------------------------------------------------------------
CREATE TABLE shop_product_category (
    product_id  BIGINT   NOT NULL,
    category_id BIGINT   NOT NULL,
    created_at  DATETIME,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY fk_shop_pc_product  (product_id)  REFERENCES shop_product  (id),
    FOREIGN KEY fk_shop_pc_category (category_id) REFERENCES shop_category (id)
);

-- -----------------------------------------------------------------------------
-- 7. shop_orders (주문)
--    - 테이블명: ORDER는 SQL 예약어이므로 orders 사용
--    - status: PENDING / CONFIRMED / SHIPPED / DELIVERED / CANCELLED
-- -----------------------------------------------------------------------------
CREATE TABLE shop_orders (
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    customer_id BIGINT     NOT NULL,
    order_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    order_date DATETIME    NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY fk_shop_orders_customer (customer_id) REFERENCES shop_customer (id)
);

-- -----------------------------------------------------------------------------
-- 8. shop_order_item (주문 상세 - 복합 키)
--    - @IdClass / @EmbeddedId 복합 키 매핑 예시
--    - price: 주문 시점 가격 스냅샷 (제품 가격 변동과 무관하게 보존)
-- -----------------------------------------------------------------------------
CREATE TABLE shop_order_item (
    order_id   BIGINT         NOT NULL,
    product_id BIGINT         NOT NULL,
    quantity   INT            NOT NULL,
    price      DECIMAL(12, 2) NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY fk_shop_oi_order   (order_id)   REFERENCES shop_orders  (id),
    FOREIGN KEY fk_shop_oi_product (product_id) REFERENCES shop_product (id)
);

-- -----------------------------------------------------------------------------
-- 9. shop_cart (장바구니)
--    - 고객 1명당 장바구니 1개 (UNIQUE)
-- -----------------------------------------------------------------------------
CREATE TABLE shop_cart (
    id          BIGINT   NOT NULL AUTO_INCREMENT,
    customer_id BIGINT   NOT NULL,
    created_at  DATETIME,
    PRIMARY KEY (id),
    UNIQUE KEY uk_shop_cart_customer (customer_id),
    FOREIGN KEY fk_shop_cart_customer (customer_id) REFERENCES shop_customer (id)
);

-- -----------------------------------------------------------------------------
-- 10. shop_cart_item (장바구니 상품 - 복합 키)
-- -----------------------------------------------------------------------------
CREATE TABLE shop_cart_item (
    cart_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INT    NOT NULL DEFAULT 1,
    PRIMARY KEY (cart_id, product_id),
    FOREIGN KEY fk_shop_ci_cart    (cart_id)    REFERENCES shop_cart    (id),
    FOREIGN KEY fk_shop_ci_product (product_id) REFERENCES shop_product (id)
);
