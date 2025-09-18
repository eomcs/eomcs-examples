# 특별한 식별자 : _이름

# 예) _이름: 모듈 멤버
# - 다음 문장으로 임포트할 때, 해당 식별자는 제외된다.
#     from 모듈명 import * 
# 
_var = 100 

def _func():
    pass

class _classname:
    pass

# 예) _이름: 클래스 멤버
# - 클래스 내부에서만 사용하는 멤버임을 암시함.

class Car:
    def __init__(self, model, no):
        self.model = model # 공개(public) 멤버
        self._no = no # 보호(protected) 멤버

c1 = Car('소나타', 100)
print(c1.model) # 공개 멤버 사용
print(c1._no) # 보호 멤버를 사용할 수는 있다. 단 권장하지 않는다.