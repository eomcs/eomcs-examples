# set

# 집합의 요소는 hashable 객체만 가능하다.
# 해시 가능한(hashable) 객체?
# - 객체가 생성되어서 소멸될 때까지 변하지 않는 해시값을 가지는 객체
# - hash() 함수를 사용할 수 있는 객체
# - __hash__() 메서드를 가지고 있는 객체

# hash() 함수를 사용하여 해시값을 알아내기 
# 해시 가능한 객체 = 불변 객체(immutable object)
print(hash(100)) # int
print(hash(3.14)) # float
print(hash(3.2 + 4.1j)) # complex
print(hash("Hello")) # str
print(hash(True)) # bool
print(hash((1,2,3))) # tuple

# 해시 불가능한 객체 = 가변 객체(mutable object)
print(hash([1, 2, 3])) # TypeError: unhashable type: 'list'
print(hash({1, 2, 3})) # TypeError: unhashable type: 'set'
print(hash({"name": "홍길동", "age": 20})) # TypeError: unhashable type: 'dict'
