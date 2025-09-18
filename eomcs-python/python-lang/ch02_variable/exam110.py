# 식별자와 NFKC 변환
# - 파이썬은 코드를 파싱(parsing)할 때 모든 식별자를 NFKC 형식으로 변환한다.

# NFKC(Normalization Form KC)란? 
# - 유니코드 문자 정규화 방식 중 하나이다.
# - 문자는 다르지만 같은 의미를 지닌 경우 표준화된 형태로 변환한다.

# 예) 다음 식별자들은 모두 NFKC 변환을 거친 후 알파벳 A로 바뀐다.

𝒜 = 999 # U+1D49C, 수학 스크립트 자본금 A
print(𝒜)
print(A) # 알파벳 A

𝓐 = 888 # U+1D4D0, 수학 대담한 대문자 A
print(𝓐)
print(A) # 알파벳 A

𝔄 = 777 # U+1D504, 수학 프랙티스 캐피탈 A
print(𝔄)
print(A) # 알파벳 A

