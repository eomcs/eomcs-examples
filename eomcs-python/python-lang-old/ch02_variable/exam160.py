# 특별한 식별자 : __이름
#
# - 클래스의 private 멤버를 정의할 때 사용한다.
# - 클래스 내부에서는 '__이름' 그대로 사용한다.
# - 클래스 외부에서는 '_클래스명__이름'으로 변형된 이름으로 사용한다.
 
class Car:
    def __init__(self, model, maker):
        self.__model = model
        self.__maker = maker
   
    def print(self):
        print(self.__model, self.__maker) # 내부에서는 원래 이름 사용

c1 = Car('소나타', '현대자동차')
c1.print()
# print(c1.__model, c1.__maker) # 외부에서는 원래 이름 사용 불가!
print(c1._Car__model, c1._Car__maker) # 변경된 이름을 사용해야 한다.


# 변형된 이름(name mangling)
# - mangling: 망가뜨리다, 변경하다
# - 원래의 이름을 알아보기 어렵게 변경하는 것
# - 이름을 변경하여 접근을 어렵게 만든다.
# - 목적
#   - 서브클래스에서 변수명이 충돌하는 것을 방지
#   - 실수로 외부에서 변경하는 것을 방지
#     즉 '외부에서 직접 쓰지 말라'는 경고의 의미!
#   - 코드의 가독성, 명확성 유지
#     즉 private member 임이 명확해짐
