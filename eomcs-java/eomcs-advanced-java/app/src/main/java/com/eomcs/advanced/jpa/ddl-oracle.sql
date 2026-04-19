-- =============================================================================
-- JPA 예제용 테이블 정의
-- 대상 DB: Oracle Database 19c+
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 기존 테이블 삭제 (FK 의존성 역순 / ORA-00942 무시)
-- CASCADE CONSTRAINTS: 해당 테이블을 참조하는 FK 제약도 함께 삭제
-- -----------------------------------------------------------------------------
BEGIN
    FOR t IN (
        SELECT table_name FROM (
            SELECT 1 AS ord, 'SHOP_CART_ITEM'        AS table_name FROM dual UNION ALL
            SELECT 2,        'SHOP_CART'              FROM dual UNION ALL
            SELECT 3,        'SHOP_ORDER_ITEM'        FROM dual UNION ALL
            SELECT 4,        'SHOP_ORDERS'            FROM dual UNION ALL
            SELECT 5,        'SHOP_PRODUCT_CATEGORY'  FROM dual UNION ALL
            SELECT 6,        'SHOP_DIGITAL_PRODUCT'   FROM dual UNION ALL
            SELECT 7,        'SHOP_PHYSICAL_PRODUCT'  FROM dual UNION ALL
            SELECT 8,        'SHOP_PRODUCT'           FROM dual UNION ALL
            SELECT 9,        'SHOP_CATEGORY'          FROM dual UNION ALL
            SELECT 10,       'SHOP_CUSTOMER'          FROM dual
        ) ORDER BY ord
    ) LOOP
        BEGIN
            EXECUTE IMMEDIATE 'DROP TABLE ' || t.table_name || ' CASCADE CONSTRAINTS';
        EXCEPTION
            WHEN OTHERS THEN
                IF SQLCODE != -942 THEN RAISE; END IF;
        END;
    END LOOP;
END;
/

-- -----------------------------------------------------------------------------
-- 1. shop_customer (고객)
--    - @Embeddable Address 매핑 예시용 컬럼(city, street, zipcode) 포함
--    - Auditing 예시용 created_at, updated_at 포함
-- -----------------------------------------------------------------------------
CREATE TABLE shop_customer (
    id         NUMBER(19)         GENERATED ALWAYS AS IDENTITY NOT NULL,
    name       VARCHAR2(100 CHAR) NOT NULL,
    email      VARCHAR2(200 CHAR) NOT NULL,
    city       VARCHAR2(100 CHAR),
    street     VARCHAR2(200 CHAR),
    zipcode    VARCHAR2(20 CHAR),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT pk_shop_customer       PRIMARY KEY (id),
    CONSTRAINT uk_shop_customer_email UNIQUE (email)
);

-- -----------------------------------------------------------------------------
-- 2. shop_category (카테고리)
--    - 자기 참조 연관관계: parent_id → shop_category.id
--    - 계층 구조 예시: 전자제품 > 노트북 > 게이밍노트북
-- -----------------------------------------------------------------------------
CREATE TABLE shop_category (
    id        NUMBER(19)         GENERATED ALWAYS AS IDENTITY NOT NULL,
    name      VARCHAR2(100 CHAR) NOT NULL,
    parent_id NUMBER(19),
    CONSTRAINT pk_shop_category        PRIMARY KEY (id),
    CONSTRAINT fk_shop_category_parent FOREIGN KEY (parent_id) REFERENCES shop_category (id)
);

-- -----------------------------------------------------------------------------
-- 3. shop_product (제품 - 상속 매핑 부모)
--    - dtype: 상속 매핑 구분자 ('PHYSICAL' / 'DIGITAL')
--    - SINGLE_TABLE / JOINED / TABLE_PER_CLASS 세 전략 비교 예시
-- -----------------------------------------------------------------------------
CREATE TABLE shop_product (
    id         NUMBER(19)         GENERATED ALWAYS AS IDENTITY NOT NULL,
    dtype      VARCHAR2(20 CHAR)  NOT NULL,
    name       VARCHAR2(200 CHAR) NOT NULL,
    price      NUMBER(12, 2)      NOT NULL,
    stock      NUMBER(10)         DEFAULT 0 NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT pk_shop_product PRIMARY KEY (id)
);

-- -----------------------------------------------------------------------------
-- 4. shop_physical_product (실물 제품 - 상속 매핑 자식, JOINED 전략)
-- -----------------------------------------------------------------------------
CREATE TABLE shop_physical_product (
    product_id   NUMBER(19)    NOT NULL,
    weight       NUMBER(10, 3),
    shipping_fee NUMBER(10, 2),
    CONSTRAINT pk_shop_physical_product PRIMARY KEY (product_id),
    CONSTRAINT fk_shop_physical_product FOREIGN KEY (product_id) REFERENCES shop_product (id)
);

COMMENT ON COLUMN shop_physical_product.weight       IS '단위: kg';
COMMENT ON COLUMN shop_physical_product.shipping_fee IS '단위: 원';

-- -----------------------------------------------------------------------------
-- 5. shop_digital_product (디지털 제품 - 상속 매핑 자식, JOINED 전략)
-- -----------------------------------------------------------------------------
CREATE TABLE shop_digital_product (
    product_id    NUMBER(19)         NOT NULL,
    download_url  VARCHAR2(500 CHAR),
    license_count NUMBER(10)         DEFAULT 1 NOT NULL,
    CONSTRAINT pk_shop_digital_product PRIMARY KEY (product_id),
    CONSTRAINT fk_shop_digital_product FOREIGN KEY (product_id) REFERENCES shop_product (id)
);

-- -----------------------------------------------------------------------------
-- 6. shop_product_category (제품-카테고리 다대다 중간 테이블)
--    - @ManyToMany 및 연결 엔티티 리팩토링 예시
-- -----------------------------------------------------------------------------
CREATE TABLE shop_product_category (
    product_id  NUMBER(19) NOT NULL,
    category_id NUMBER(19) NOT NULL,
    created_at  TIMESTAMP,
    CONSTRAINT pk_shop_product_category PRIMARY KEY (product_id, category_id),
    CONSTRAINT fk_shop_pc_product       FOREIGN KEY (product_id)  REFERENCES shop_product  (id),
    CONSTRAINT fk_shop_pc_category      FOREIGN KEY (category_id) REFERENCES shop_category (id)
);

-- -----------------------------------------------------------------------------
-- 7. shop_orders (주문)
--    - 테이블명: ORDER는 SQL 예약어이므로 orders 사용
--    - order_status: PENDING / CONFIRMED / SHIPPED / DELIVERED / CANCELLED
-- -----------------------------------------------------------------------------
CREATE TABLE shop_orders (
    id           NUMBER(19)         GENERATED ALWAYS AS IDENTITY NOT NULL,
    customer_id  NUMBER(19)         NOT NULL,
    order_status VARCHAR2(20 CHAR)  DEFAULT 'PENDING' NOT NULL,
    order_date   TIMESTAMP          NOT NULL,
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP,
    CONSTRAINT pk_shop_orders          PRIMARY KEY (id),
    CONSTRAINT fk_shop_orders_customer FOREIGN KEY (customer_id) REFERENCES shop_customer (id)
);

-- -----------------------------------------------------------------------------
-- 8. shop_order_item (주문 상세 - 복합 키)
--    - @IdClass / @EmbeddedId 복합 키 매핑 예시
--    - price: 주문 시점 가격 스냅샷 (제품 가격 변동과 무관하게 보존)
-- -----------------------------------------------------------------------------
CREATE TABLE shop_order_item (
    order_id   NUMBER(19)    NOT NULL,
    product_id NUMBER(19)    NOT NULL,
    quantity   NUMBER(10)    NOT NULL,
    price      NUMBER(12, 2) NOT NULL,
    CONSTRAINT pk_shop_order_item PRIMARY KEY (order_id, product_id),
    CONSTRAINT fk_shop_oi_order   FOREIGN KEY (order_id)   REFERENCES shop_orders  (id),
    CONSTRAINT fk_shop_oi_product FOREIGN KEY (product_id) REFERENCES shop_product (id)
);

-- -----------------------------------------------------------------------------
-- 9. shop_cart (장바구니)
--    - 고객 1명당 장바구니 1개 (UNIQUE)
-- -----------------------------------------------------------------------------
CREATE TABLE shop_cart (
    id          NUMBER(19) GENERATED ALWAYS AS IDENTITY NOT NULL,
    customer_id NUMBER(19) NOT NULL,
    created_at  TIMESTAMP,
    CONSTRAINT pk_shop_cart          PRIMARY KEY (id),
    CONSTRAINT uk_shop_cart_customer UNIQUE (customer_id),
    CONSTRAINT fk_shop_cart_customer FOREIGN KEY (customer_id) REFERENCES shop_customer (id)
);

-- -----------------------------------------------------------------------------
-- 10. shop_cart_item (장바구니 상품 - 복합 키)
-- -----------------------------------------------------------------------------
CREATE TABLE shop_cart_item (
    cart_id    NUMBER(19) NOT NULL,
    product_id NUMBER(19) NOT NULL,
    quantity   NUMBER(10) DEFAULT 1 NOT NULL,
    CONSTRAINT pk_shop_cart_item  PRIMARY KEY (cart_id, product_id),
    CONSTRAINT fk_shop_ci_cart    FOREIGN KEY (cart_id)    REFERENCES shop_cart    (id),
    CONSTRAINT fk_shop_ci_product FOREIGN KEY (product_id) REFERENCES shop_product (id)
);
