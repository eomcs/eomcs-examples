# 변수명
# - 식별자(identifier) 작성 원칙에 따른다.
# - 명명규칙은 PEP 8 문서의 내용을 참고하라.

# 식별자(Identifier)?
# - 변수, 함수, 클래스, 모듈 등의 이름이다.
# - ASCII 범위(U+0001 ~ U+007F) 안의 문자를 사용한다.
# - 알파벳 대.소문자와 언더스코어(_), 숫자를 사용한다.
# - 단 숫자는 첫 문자로 올 수 없다.
# - Python 3.0부터는 ASCII 범위 밖의 문자도 사용할 수 있지만, 비추한다.
#   (자세한 것은 PEP 3131 을 참고하라.)
# - 대소문자를 구분한다.
 
a = 100
abc = 100
_abc = 100
__abc = 100
a123 = 100

ABC = 200
Abc = 300
print(abc, ABC, Abc) # abc, ABC, Abc 는 서로 다른 변수이다.

# 9a = 100 # 첫 문자로 숫자가 올 수 없다.

가나다 = 100 # non-ASCII 문자 사용 가능. 다만 비추한다.
print(가나다)

# PEP 8 명명규칙 요약
# 1) 모듈명
#    . 짧고 모두 소문자. 
#    . 가독성을 위해 underscore(_) 를 사용할 수 있다.
# 2) 패키지명
#    . 짧고 모두 소문자.
#    . underscore(_) 사용은 권장하지 않는다.
#    . 모듈명이나 패키지명에 dash(-)가 포함돼 있으면 import 문 사용 불가!
# 3) 클래스명
#    . CapWords 규칙 사용.
#    . built-in 이름은 별도의 규칙 사용
#      . 단일 단어 또는 두 단어.
#      . 예외 이름과 빌트인 상수에는 CapWords 사용.
# 4) 타입 변수명
#    . CapWords 사용하고 짧은 이름 선호.
#      예) T, AnyStr
# 5) 예외명
#    . 예외는 클래스이므로 클래스명 규칙고 같다.
#    . 단 접미사 "Error"를 붙인다.
# 6) 전역 변수명
#    . 함수명 규칙과 같다.
#    . 변수가 선언된 모듈 내부에서만 사용되는 것을 권장.
# 7) 함수명
#    . 소문자 사용.
#    . 단어 사이를 underscore(_)로 연결.
# 8) 변수명
#    . 함수명과 같은 규칙 사용.
#    . mixedCase는 해당 스타일이 널리 사용되는 컨텍스트에서만 허용.
#      예) threading.py 
#      이전 버전과의 호환성 유지를 위함.
# 9) 함수 및 메서드 아규먼트 이름
#    . 인스턴스 메서드: 첫 번째 인수는 항상 self
#    . 클래스 메서드: 첫 번째 인수는 항상 cls
#    . 아규먼트 이름이 예약 키워드와 같을 경우 꼬리 밑줄 사용 권장.
#      예) class_ 
#      약어(clss)나 철자 오류(clazz)를 사용하는 것 보다 더 낫다.
#    . 동의어가 있다면 그 이름을 사용하는 것을 추천.
# 10) 메서드명과 인스턴스 변수명
#     . 함수명과 같은 규칙 사용.
#     . 비공개인 경우 underscore(_) 한 개를 이름 앞에 붙인다.
#       예) _count, _size()
#     . 하위 클래스와의 이름 충돌을 방지할 경우 두 개의 underscore(_) 붙인다.
#       예) __count, __size()
#     . 파이썬은 이런 이름을 맹글링(mangling) 한다.
#       내부 사용예) __count, __size()
#       외부 사용예) _클래스명__count, _클래스명__size()
# 11) 상수명
#     . 모듈 레벨에서 정의된다.
#     . 모두 대문자로 작성.
#     . underscore(_)로 단어를 구분한다.
#       예) MAX_SIZE

# 명명 스타일 예:
#   . b (single lowercase letter)
#   . B (single uppercase letter)
#   . lowercase
#   . lower_case_with_underscores
#   . UPPERCASE
#   . UPPER_CASE_WITH_UNDERSCORES
#   . CapitalizeWords = CapWords = CamelCase = StudlyCaps
#     단 약어는 모두 대문자 사용.
#     예) HTTPServerError(추천), HttpServerError(비추)
#   . mixedCase
#   . Capitalized_Words_With_Underscores (비추)

# 특수 이름
#   . _이름: protected 멤버
#   . 이름_: 파이썬 키워드와의 충돌 회피
#   . __이름: 이름 변형(mangling) 자동 처리. 하위 클래스와의 충돌 회피
#   . __이름__: 매직(magic) 멤버. 특별한 용도의 built-in 멤버. 사용하지 말 것!


# 참고!
# - 파이썬 문서에서 유니코드 문자를 분류할 때 사용하는 약자
# Lu - uppercase letters
# Ll - lowercase letters
# Lt - titlecase letters
# Lm - modifier letters
# Lo - other letters
# Nl - letter numbers
# Mn - nonspacing marks
# Mc - spacing combining marks
# Nd - decimal numbers
# Pc - connector punctuations
# Other_ID_Start - explicit list of characters in PropList.txt to support backwards compatibility
# Other_ID_Continue - likewise
