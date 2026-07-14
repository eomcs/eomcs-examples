# 소프트 키워드(soft keywords)
# - 특정 문맥에서 식별자로 사용된다.
#   match   case    type    _

# 일반 식별자(ordinary identifiers)로 사용될 경우
match = 100
case = 200
type = 300
_ = 400
print(match, case, type, _)

# 소프트 키워드로 사용될 경우
# match 문장:
level = 'A'
match level: # 변수가 아니라 match 문장을 의미하는 키워드로 사용된다.
    case 'A': # 변수가 아니라 case 문을 의미하는 키워드로 사용된다.
        print('매우 우수합니다!')
        match = 500 # 물론 이 부분에서는 일반 변수로 인식된다.
        case = 600
        _ = 700
    case 'B':
        print('우수합니다.')
    case _: # 변수가 아니라 case의 패턴으로 사용된다.
        print('노력이 필요합니다.')
print(match, case, type, _)

# 타입 앨리어스 선언:
type Point = tuple[float, float] # 변수가 아니라 type alias 문법으로 인식된다.
p1: Point = (3.14, 51.3) # type alias는 타입 힌팅(hinting)에서 사용된다.
p2: Point = (45.4, 12.56) # 타입 힌팅은 가독성과 유지보수성을 향상시킨다.
print(p1, p2)

