# str

from datetime import datetime

# f-strings: 문자열 포맷팅 - :format spec.

# 날짜 및 시간
d = datetime.now()
print(f"년: {d:%Y}, {d:%y}") # 2025, 25
print(f"월: {d:%B}, {d:%b}, {d:%m}")
print(f"일: {d:%d}")
print(f"요일: {d:%A}, {d:%a}, {d:%w}") # Sunday: 0
print(f"오전/오후: {d:%p}") # AM, PM
print(f"시: {d:%H}, {d:%I}") # 00, 01, ..., 23 / 01, 02, ..., 12
print(f"분: {d:%M}") # 00, 01, ..., 59
print(f"초: {d:%S}") # 00, 01, ..., 59
print(f"마이크로초: {d:%f}") # 000000, 000001, ..., 999999
print(f"1년 기준 일: {d:%j}") # 001, 002, ..., 366
print(f"1년 기준 주: {d:%U}") # 00, 01, ..., 53
print(f"로케일 기준 일자 및 시각: {d:%c}")
print(f"로케일 기준 일자: {d:%x}")
print(f"로케일 기준 시각: {d:%X}")
print(f"% 문자 출력: {d:%Y-%m-%d %% %H:%M:%S}") # % 문자 출력
