# 리터럴(literals)의 타입

print(type(100)) # <class 'int'>
print(type(100.0)) # <class 'float'>
print(type(3.1 + 4.2j)) # <class 'complex'>

print(type(True)) # <class 'bool'>
print(type(False)) # <class 'bool'>

print(type([1, 2, 3])) # <class 'list'>
print(type((1, 2, 3))) # <class 'tuple'>

print(type("Hello Python world!")) # <class 'str'>
print(type("""Hello
Python
world!""")) # <class 'str'>
print(type(b"Hello, world!")) # <class 'bytes'>
print(type(b"""Hello,
Python
world!""")) # <class 'bytes'>

print(type({1, 2, 3})) # <class 'set'>

print(type({"a": 1, "b": 2, "c": 3})) # <class 'dict'>

print(type(None)) # <class 'NoneType'>

print(type(...)) # <class 'ellipsis'>
