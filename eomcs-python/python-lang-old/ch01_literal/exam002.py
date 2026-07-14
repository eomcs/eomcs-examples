# 논리적인 줄(logical line)과 물리적인 줄(physical line)

# 논리적인 줄
# - 파이썬 코드에서 한 문장으로 간주하는 줄
# - \를 사용하면 여러 줄을 한 개의 논리적인 줄로 다룰 수 있다.
print("Hello, world!") # 물리적인 1줄 = 논리적인 1줄
print("Hello\
,\
world!") # 물리적인 3줄 = 논리적인 1줄

# 물리적인 1줄 = 논리적인 1줄
if (3 + 2 == 2 + 3) or "AB" == "A" "B" or True == 1: 
    print("OK!")

# 물리적인 3줄 = 놀리적인 1줄
if (3 + 2 == 2 + 3)\
    or "AB" == "A" "B"\
    or True == 1:
    print("OK!")
