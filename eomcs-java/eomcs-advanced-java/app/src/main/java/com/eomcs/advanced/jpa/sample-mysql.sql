-- =============================================================================
-- JPA 예제용 샘플 데이터
-- 대상 DB: MySQL / MariaDB
-- 주의: AUTO_INCREMENT 컬럼(id)은 INSERT 목록에서 제외
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 1. shop_customer (고객 3명)
--    id: 1=홍길동, 2=김영희, 3=이철수
-- -----------------------------------------------------------------------------
INSERT INTO shop_customer (name, email, city, street, zipcode, created_at, updated_at)
VALUES ('홍길동', 'hong@example.com', '서울', '강남대로 123', '06000',
        '2024-01-10 09:00:00', '2024-01-10 09:00:00');

INSERT INTO shop_customer (name, email, city, street, zipcode, created_at, updated_at)
VALUES ('김영희', 'kim@example.com', '부산', '해운대로 456', '48100',
        '2024-01-15 11:30:00', '2024-02-20 14:00:00');

INSERT INTO shop_customer (name, email, city, street, zipcode, created_at, updated_at)
VALUES ('이철수', 'lee@example.com', '대구', '동성로 789', '41000',
        '2024-02-01 08:15:00', '2024-02-01 08:15:00');

-- -----------------------------------------------------------------------------
-- 2. shop_category (카테고리 5개 - 계층 구조)
--    id: 1=전자제품, 2=노트북(→1), 3=게이밍노트북(→2), 4=의류, 5=남성의류(→4)
-- -----------------------------------------------------------------------------
INSERT INTO shop_category (name, parent_id) VALUES ('전자제품', NULL);
INSERT INTO shop_category (name, parent_id) VALUES ('노트북', 1);
INSERT INTO shop_category (name, parent_id) VALUES ('게이밍노트북', 2);
INSERT INTO shop_category (name, parent_id) VALUES ('의류', NULL);
INSERT INTO shop_category (name, parent_id) VALUES ('남성의류', 4);

-- -----------------------------------------------------------------------------
-- 3. shop_product (제품 5개 - PHYSICAL 3개, DIGITAL 2개)
--    id: 1=MacBook Pro, 2=ASUS ROG, 3=Office 365, 4=Adobe CC, 5=울 코트
-- -----------------------------------------------------------------------------
INSERT INTO shop_product (dtype, name, price, stock, created_at, updated_at)
VALUES ('PHYSICAL', 'MacBook Pro 14 M3', 2990000.00, 10,
        '2024-01-05 10:00:00', '2024-01-05 10:00:00');

INSERT INTO shop_product (dtype, name, price, stock, created_at, updated_at)
VALUES ('PHYSICAL', 'ASUS ROG Zephyrus G14', 1890000.00, 5,
        '2024-01-06 10:00:00', '2024-01-06 10:00:00');

INSERT INTO shop_product (dtype, name, price, stock, created_at, updated_at)
VALUES ('DIGITAL', 'Microsoft Office 365', 119000.00, 999,
        '2024-01-07 10:00:00', '2024-01-07 10:00:00');

INSERT INTO shop_product (dtype, name, price, stock, created_at, updated_at)
VALUES ('DIGITAL', 'Adobe Creative Cloud', 72600.00, 999,
        '2024-01-08 10:00:00', '2024-01-08 10:00:00');

INSERT INTO shop_product (dtype, name, price, stock, created_at, updated_at)
VALUES ('PHYSICAL', '남성 울 코트', 189000.00, 20,
        '2024-01-09 10:00:00', '2024-01-09 10:00:00');

-- -----------------------------------------------------------------------------
-- 4. shop_physical_product (실물 제품 상세 - product_id: 1, 2, 5)
-- -----------------------------------------------------------------------------
INSERT INTO shop_physical_product (product_id, weight, shipping_fee)
VALUES (1, 1.600, 3000.00);

INSERT INTO shop_physical_product (product_id, weight, shipping_fee)
VALUES (2, 1.650, 3000.00);

INSERT INTO shop_physical_product (product_id, weight, shipping_fee)
VALUES (5, 0.900, 2500.00);

-- -----------------------------------------------------------------------------
-- 5. shop_digital_product (디지털 제품 상세 - product_id: 3, 4)
-- -----------------------------------------------------------------------------
INSERT INTO shop_digital_product (product_id, download_url, license_count)
VALUES (3, 'https://www.microsoft.com/office/download', 5);

INSERT INTO shop_digital_product (product_id, download_url, license_count)
VALUES (4, 'https://www.adobe.com/creativecloud/download', 1);

-- -----------------------------------------------------------------------------
-- 6. shop_product_category (제품-카테고리 매핑)
-- -----------------------------------------------------------------------------
INSERT INTO shop_product_category (product_id, category_id) VALUES (1, 2); -- MacBook Pro    → 노트북
INSERT INTO shop_product_category (product_id, category_id) VALUES (2, 2); -- ASUS ROG       → 노트북
INSERT INTO shop_product_category (product_id, category_id) VALUES (2, 3); -- ASUS ROG       → 게이밍노트북
INSERT INTO shop_product_category (product_id, category_id) VALUES (3, 1); -- Office 365     → 전자제품
INSERT INTO shop_product_category (product_id, category_id) VALUES (4, 1); -- Adobe CC       → 전자제품
INSERT INTO shop_product_category (product_id, category_id) VALUES (5, 4); -- 남성 울 코트   → 의류
INSERT INTO shop_product_category (product_id, category_id) VALUES (5, 5); -- 남성 울 코트   → 남성의류

-- -----------------------------------------------------------------------------
-- 7. shop_orders (주문 3건)
--    id: 1=홍길동 확정, 2=홍길동 배송완료, 3=김영희 대기
-- -----------------------------------------------------------------------------
INSERT INTO shop_orders (customer_id, order_status, order_date, created_at, updated_at)
VALUES (1, 'CONFIRMED', '2024-03-01 14:00:00', '2024-03-01 14:00:00', '2024-03-01 15:30:00');

INSERT INTO shop_orders (customer_id, order_status, order_date, created_at, updated_at)
VALUES (1, 'DELIVERED', '2024-02-15 10:00:00', '2024-02-15 10:00:00', '2024-02-20 18:00:00');

INSERT INTO shop_orders (customer_id, order_status, order_date, created_at, updated_at)
VALUES (2, 'PENDING', '2024-03-10 09:30:00', '2024-03-10 09:30:00', '2024-03-10 09:30:00');

-- -----------------------------------------------------------------------------
-- 8. shop_order_item (주문 상세)
--    price: 주문 시점 가격 스냅샷
-- -----------------------------------------------------------------------------
-- 주문 1 (홍길동 CONFIRMED): MacBook Pro x1, Office 365 x1
INSERT INTO shop_order_item (order_id, product_id, quantity, price) VALUES (1, 1, 1, 2990000.00);
INSERT INTO shop_order_item (order_id, product_id, quantity, price) VALUES (1, 3, 1,  119000.00);

-- 주문 2 (홍길동 DELIVERED): 남성 울 코트 x2
INSERT INTO shop_order_item (order_id, product_id, quantity, price) VALUES (2, 5, 2, 189000.00);

-- 주문 3 (김영희 PENDING): ASUS ROG x1, Adobe CC x1
INSERT INTO shop_order_item (order_id, product_id, quantity, price) VALUES (3, 2, 1, 1890000.00);
INSERT INTO shop_order_item (order_id, product_id, quantity, price) VALUES (3, 4, 1,   72600.00);

-- -----------------------------------------------------------------------------
-- 9. shop_cart (장바구니 - 고객 2, 3)
--    id: 1=김영희, 2=이철수
-- -----------------------------------------------------------------------------
INSERT INTO shop_cart (customer_id, created_at) VALUES (2, '2024-03-11 10:00:00');
INSERT INTO shop_cart (customer_id, created_at) VALUES (3, '2024-03-12 16:00:00');

-- -----------------------------------------------------------------------------
-- 10. shop_cart_item (장바구니 상품)
-- -----------------------------------------------------------------------------
-- 김영희 장바구니 (cart_id=1): MacBook Pro x1, Adobe CC x2
INSERT INTO shop_cart_item (cart_id, product_id, quantity) VALUES (1, 1, 1);
INSERT INTO shop_cart_item (cart_id, product_id, quantity) VALUES (1, 4, 2);

-- 이철수 장바구니 (cart_id=2): ASUS ROG x1, 남성 울 코트 x1
INSERT INTO shop_cart_item (cart_id, product_id, quantity) VALUES (2, 2, 1);
INSERT INTO shop_cart_item (cart_id, product_id, quantity) VALUES (2, 5, 1);

COMMIT;
