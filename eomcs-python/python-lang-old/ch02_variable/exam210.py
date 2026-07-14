# 객체 생성

# int 타입 객체 만들기
i1 = int(100) # 클래스로 만들기
i2 = 100 # 리터럴로 만들기
print(i1, type(i1))
print(i2, type(i2))

# float 타입 객체 만들기
f1 = float(3.14) # 클래스로 만들기
f2 = 3.14 # 리터럴로 만들기
print(f1, type(f1))
print(f2, type(f2))

# complex 타입 객체 만들기
c1 = complex(3.1, 4.5)
c2 = 3.1 + 4.5j
print(c1, type(c1))
print(c2, type(c2))

# bool 타입 객체 만들기
# - bool은 int의 서브 타입이다.
b1 = bool(True)
b2 = True
print(b1, type(b1))
print(b2, type(b2))

# str 타입 객체 만들기
s1 = str('Hello') # 리터럴로 생성하는 것 같다.
s2 = 'Hello'
print(s1, type(s1))
print(s2, type(s2))

# bytes 타입 객체 만들기
f1 = bytes(b'ABC')
f2 = b'ABC'
print(f1, type(f1))
print(f2, type(f2)) 

# tuple 타입 객체 만들기
t1 = tuple([100, 200, 300])
t2 = (100, 200, 300)
print(t1, type(t1))
print(t2, type(t2)) 

# list 타입 객체 만들기
t1 = list([100, 200, 300])
t2 = [100, 200, 300]
print(t1, type(t1))
print(t2, type(t2)) 

# set 타입 객체 만들기
t1 = set([100, 200, 300, 200])
t2 = {100, 200, 300, 200}
print(t1, type(t1))
print(t2, type(t2)) 

# dict 타입 객체 만들기
t1 = dict(name="홍길동", age=20)
t2 = {"name": "홍길동", "age": 20}
print(t1, type(t1))
print(t2, type(t2)) 