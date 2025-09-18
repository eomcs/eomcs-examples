# tuple
# - 값을 변경할 수 없는 immutable object

print((), type(())) # 빈 튜플
print((1), type((1))) # 주의! 일반 괄호로 해석됨
print((1,), type((1,))) # 항목이 한 개인 튜플은 끝에 ,를 붙여야 한다.
print((1,2)) # 2 개 이상일 때는 끝에 ,를 붙이지 않아도 된다.
print(("Hello", "Python")) # 문자열 튜플
print((1, "Hello", 3.14)) #  다양한 타입의 데이터 튜플
print((1, (2, 3), (4, 5))) # 중첩된 튜플

# 값 변경 불가!
a = (1, 2, 3)
a[1] = 200 # TypeError: 값을 변경할 수 없다.
