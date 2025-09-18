# 특별한 식별자 : __이름__
# 
# - double underscore라는 의미로 'dunder'라 부른다.
# - 파이썬에서 특별한 역할을 하는 변수, 함수, 클래스를 의미한다.
# - 일반적인 이름으로 사용하지 말아야 한다.

# 예) built-in 변수
print(__name__) # 현재 실행하는 스크립트의 모듈명 출력. 
                # 직접 실행할 경우 '__main__' 값을 출력

# 예) magic method
class MyClass:
    def __init__(self): # 생성자를 의미
        print('생성자 호출됨!')

v1 = MyClass()
