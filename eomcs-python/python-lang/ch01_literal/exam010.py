# 리터럴(literals)

# 수치 타입 리터럴(numeric type)
print(100) # 정수 (int)
print(100.0) # 실수 (float)
print(3.1 + 4.2j) # 복소수 (complex)

# 불린 타입 리터럴(boolean type) 
print(True) # 불리언 (bool)
print(False) # 불리언 (bool)

# 시퀀스 타입 리터럴(sequence type)
print([1, 2, 3]) # 리스트 (list)
print((1, 2, 3)) # 튜플 (tuple)

# 텍스트 시퀀스 타입 리터럴(text sequence type)
print("Hello Python world!") # double quotes 문자열 (str)
print('Hello Python world!') # single quotes 문자열 (str)
print("""Hello
Python
world!""") # three double quotes 문자열 (str)
print('''Hello
Python
world!''') # three single quotes 문자열 (str)

# 바이너리 시퀀스 타입 리터럴
print(b"Hello, world!") # double quotes 바이트 (bytes)
print(b'Hello, world!') # single quotes 바이트 (bytes)
print(b"""Hello,
Python
world!""") # three double quotes 바이트 (bytes)
print(b'''Hello,
Python
world!''') # three single quotes 바이트 (bytes)

# 집합 타입 리터럴
print({1, 2, 3}) # 집합 (set)
print({"홍길동", "임꺽정", "유관순"}) # 집합 (set)

# 매핑 타입 리터럴
print({"kor": 100, "math": 100, "eng": 100}) # 딕셔너리 (dict)
print({1: "홍길동", 2: "임꺽정", 3: "유관순"}) # 딕셔너리 (dict)

# 널 타입 리터럴
print(None) # 널 (None)

# 생략 타입 리터럴
print(...) # 생략 (Ellipsis)
