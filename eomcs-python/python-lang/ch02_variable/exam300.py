# 객체의 값 바꾸기

# 1) mutable 객체는 값을 변경할 수 있다.
#    예: list, set, dict 등

# list
a = [100, 200, 300]
print(a)

a[1] = 222
print(a)

# set
b = {100, 200, 300}
print(b)

b.discard(200)
b.add(333)
print(b)

# dict
c = {'name': '홍길동', 'age': 20, 'working': True}
print(c)

c['name'] = '임꺽정'
print(c)

# 2) immutable 객체는 값을 변경할 수 없다.
#    예: int, float, complex, bool, str, bytes, tuple

# tuple
a = (100, 200, 300)
print(a)
# a[1] = 222 # TypeError

# str
b = 'Hello, Java World!'
# b[0] = 'X' # TypeError
new_str = b.replace('Java', 'Python')
print(b)
print(new_str)
