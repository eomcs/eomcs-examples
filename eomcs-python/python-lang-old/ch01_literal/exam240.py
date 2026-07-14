# str

# f-strings: 문자열 포맷팅 - :format spec.

from datetime import datetime

# 정수 포맷
value = 126
print(f"값: {value}") # 기본 10진수
print(f"값: {value:d}") # 10진수 
print(f"값: {value:b}") # 2진수
print(f"값: {value:o}") # 8진수
print(f"값: {value:x}") # 16진수(소문자)
print(f"값: {value:X}") # 16진수(대문자)
print(f"값: {value:c}") # 유니코드 문자

# 부동소수점 포맷
value =123.4567
print(f"값: {value}") # 기본 고정 소수점
print(f"값: {value:f}") # 고정 소수점
print(f"값: {value:.2f}") # 소수점 자릿수 2자리
print(f"값: {value:10.2f}") # 전체 자릿수 10자리
print(f"값: {value:010.2f}") # 전체 자릿수 10자리 + 빈자리 0으로 채운다.

# 날짜 포맷
d = datetime.now()
print(f"{d}")
print(f"{d:%Y-%m-%d %a %H:%M:%S}")
