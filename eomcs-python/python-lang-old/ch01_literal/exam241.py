# str

# f-strings: 문자열 포맷팅 - :format spec.

# 정렬
name = "홍길동"
print(f"{name:10}") # 출력할 문자열 자릿수 10자리. 기본 왼쪽 정렬
print(f"{name:<10}") # 왼쪽 정렬
print(f"{name:>10}") # 오른쪽 정렬
print(f"{name:^10}") # 가운데 정렬
print(f"{name:*>10}") # 왼쪽 빈자리 * 로 채운다.
print(f"{name:*<10}") # 오른쪽 빈자리 *로 채운다.
print(f"{name:*^10}") # 양쪽 빈자리 *로 채운다.

value = 100
print(f"{value:10}") # 출력할 문자열 자릿수 6자리. 기본 오른쪽 정렬
print(f"{value:>10}") # 오른쪽 정렬
print(f"{value:<10}") # 왼쪽 정렬
print(f"{value:^10}") # 가운데 정렬
print(f"{value:010}") # 왼쪽 빈자리 0으로 채운다
print(f"{value:0>10}") # 왼쪽 빈자리 0으로 채운다.
print(f"{value:0<10}") # 오른쪽 빈자리 0으로 채운다.
print(f"{value:*^10}") # 양쪽 빈자리는 *로 채운다

# 부호
value1 = 100
value2 = -100
print(f"{value1:+}, {value2:+}") # 부호 삽입 
print(f"{value1:-}, {value2:-}") # - 부호만 삽입 
print(f"{value1: }, {value2: }") # 양수는 공백. 음수는 - 부호 삽입

# 진수 접두사 추가
value = 100
print(f"값: {value:#b}") # 0b 추가
print(f"값: {value:#o}") # 0o 추가
print(f"값: {value:#x}") # 0x 추가

# 자릿수 구분 출력
value = 1234567890
print(f"값: {value:,}") # 세자리 마다 , 삽입
print(f"값: {value:_}") # 세자리 마다 _ 삽입

# 부동소수점 출력
value =123.4567
print(f"값: {value:e}") # e 지수 표기법
print(f"값: {value:E}") # E 지수 표기법
print(f"값: {value:%}") # 뒤에 % 붙이기