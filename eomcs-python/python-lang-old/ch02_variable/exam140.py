# 특별한 식별자 : _

# 예) match 문장
# - else/otherwise/wildcard 의미로 사용된다.
level = 'A'
match level:
    case 'A':
        print('매우 우수!')
    case 'B':
        print('우수!')
    case _:
        print('보통!')

# 예) 반복문
# - 반복문에서 값을 무시할 때 사용한다.
for _ in range(3):
    print('Hello!')

# 예) 함수 리턴 값 받기
# - 함수의 리턴 값 중 일부를 무시할 때 사용한다.
def get_user():
    return ('홍길동', 20, True)

name, _, working = get_user() 
print(name, working)
print(_) # 실제 _ 도 변수다. 다만 중요하지 않다라는 의미로 사용된다.

