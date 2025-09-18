# str

# 이스케이프 문자 II

# \OOO: 8진수 유니코드
print("\101\102\103\060\061\062\141\142\143") # 최대 \377까지

# \xhh: 16진수 유니코드
print("\x41\x42\x43\x30\x31\x32\x61\x62\x63") # 최대 \xff


# \N{이름}: 이스케이프 문자에 지정한 별칭
# - https://www.unicode.org/Public/15.1.0/ucd/NameAliases.txt
print("Hello Python World!\N{LINE FEED}-Linefeed") # Linefeed
print("Hello Python World!\N{line feed}-Linefeed") # 대소문자 구분 없음
print("Hello Python World!\N{CARRIAGE RETURN}-CarriageReturn") # carriage return
print("Hello Python World!\N{FORM FEED}-Formfeed") # Form Feed

# \uxxxx: 16진수 UTF-16(16비트 유니코드; UCS 2)
print("\u0041\u0042\u0043\uac00\uac01\uac02\u2665")

# \Uxxxxxxxx: 16진수 UTF-32(32비트 유니코드; UCS 4)
print("\U00000041\U00000042\U00000043\U0000ac00\U0000ac01\U0000ac02\U00002665") 
