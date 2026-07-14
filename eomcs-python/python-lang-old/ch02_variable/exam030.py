# 대입문(assignment statement)
# - 변수에 객체 레퍼런스(참조)를 저장한다.

a = 100
b = a

# a와 b는 같은 객체를 참조한다.
print(id(a)) 
print(id(b))