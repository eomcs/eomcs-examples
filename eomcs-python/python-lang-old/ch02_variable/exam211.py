# 객체 캐싱(Integer Interning)
# - 값을 변경할 수 없는 객체(immutable object)는 캐싱된다.
#   예) int, float, complex, bool, str, bytes, tuple 등
# - 단 파이썬 구현체에 따라 동작 방식이 다를 수 있다.

a1 = 100
a2 = int(100)
a3 = 30 + 70
print(a1, a2, a3)
print(id(a1), id(a2), id(a3))

b1 = 3.14
b2 = float(3.14)
b3 = 2.1 + 1.04
print(b1, b2, b3)
print(id(b1), id(b2), id(b3))

c1 = 3.1 + 4.5j
c2 = complex(3.1 + 4.5j)
print(c1, c2)
print(id(c1), id(c2))

d1 = True
d2 = 100 > 99
print(d1, d2)
print(id(d1), id(d2)) 

e1 = 'Hello'
e2 = str('Hello') # 리터럴로 생성한 객체와 같다. 
e3 = str('He') + str('llo') # 리터럴이 아닌 str 객체를 연결할 때 새 객체 생성
print(e1, e2, e3)
print(id(e1), id(e2), id(e3))

f1 = b'ABC'
f2 = bytes(b'ABC')
print(f1, f2)
print(id(f1), id(f2)) 

g1 = (100, 200, 300)
g2 = (100, 200, 300)
print(id(g1), id(g2))

