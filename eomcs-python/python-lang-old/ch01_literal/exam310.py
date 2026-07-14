# bytes

# ASCII 문자
print(b"ABCabc012") # b 또는 B 접두사를 붙인다. 
print(B"ABCabc012")

# non-ASCII 문자: \xHH 이스케이프 문자로 표현
# print(b"ABC©가각간") # 오류!
print(b"ABC\xc2\xa9\xea\xb0\x80") # ABC©가: UTF-8 인코딩 바이트 배열