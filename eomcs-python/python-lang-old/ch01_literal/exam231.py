# str
 
# f-strings: 문자열 포맷팅 - !conversion

name = "홍길동"

#   {표현식!s} = {str(표현식)} = {표현식}
# - 표현식의 실행 결과에 대해 str() 리턴 값을 사용
print(f"{name}님 안녕하세요!")
print(f"{name!s}님 안녕하세요!")
print(f"{str(name)}님 안녕하세요!")

# {표현식!r} = {repr(표현식)}
# - 표현식의 실행 결과에 대해 repr() 리턴 값을 사용
print(f"{name!r}님 안녕하세요!")
print(f"{repr(name)}님 안녕하세요!")

# {표현식!a} = {ascii(표현식)}
# - 표현식의 실행 결과에 대해 repr() 리턴 값을 사용.
# - 단 non-ASCII 문자는 이스케이프 문자(\x, \u, \U)로 바뀐다. 
#   . ASCII 문자: 변환 없이 그대로
#   . non-ASCII 문자(0x80 ~ 0xFF): \xHH 문자로 변환
#   . non-ASCII 문자(0x0100 ~ 0xFFFF): \uHHHH 문자로 변환
#   . non-ASCII 문자(0x010000 ~ 0x10FFFF: \UHHHHHHHH 문자로 변환
name = "ABC©가각간💕"
print(f"{name!a}님 안녕하세요!")
print(f"{ascii(name)}님 안녕하세요!")