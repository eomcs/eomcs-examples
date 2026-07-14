# 객체 캐싱(Integer Interning) II
# - 값을 변경할 수 있는 객체(immutable object)는 캐싱하지 않는다.
#   예) list, set, dict 등

a1 = [100, 200, 300]
a2 = [100, 200, 300]
print(id(a1), id(a2)) # 다르다. 왜? 리스트는 변경되기 때문이다.

b1 = {1, 2, 3}
b2 = {1, 2, 3}
print(id(b1), id(b2)) # 다르다.

c1 = {'name': '홍길동', 'age': 20}
c2 = {'name': '홍길동', 'age': 20}
print(id(c1), id(c2)) 