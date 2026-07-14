# NFKC 변환하기

import unicodedata

print(unicodedata.normalize('NFKC', '𝒜')) # U+1D49C, 수학 스크립트 자본금 A
print(unicodedata.normalize('NFKC', '𝓐')) # U+1D4D0, 수학 대담한 대문자 A
print(unicodedata.normalize('NFKC', '𝔄')) # U+1D504, 수학 프랙티스 캐피탈 A
print(unicodedata.normalize('NFKC', 'A')) # U+0041, 알파벳 대문자 A