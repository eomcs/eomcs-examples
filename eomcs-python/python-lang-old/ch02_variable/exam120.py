# 키워드(keywords; reserved words)
# - 일반적인 식별자(ordinary identifiers)로 사용할 수 없는 이름
#   (즉 변수명, 함수명, 클래스명, 모듈명으로 사용할 수 없는 이름)
#   False      await      else       import     pass
#   None       break      except     in         raise
#   True       class      finally    is         return
#   and        continue   for        lambda     try
#   as         def        from       nonlocal   while
#   assert     del        global     not        with
#   async      elif       if         or         yield

# 이런 키워드는 변수명, 함수명, 클래스명, 모듈명으로 사용할 수 없다.
# and = 100 
# with = 100

# 만약 가독성을 높이기 위해 꼭 키워드와 동일한 이름을 식별자로 사용하고 싶다면,
# 식별자 뒤에 _ 를 붙여라.
and_ = 100
with_ = 100

# 키워드의 동의어를 사용하는 것도 좋다.
lastly = 200 # finally의 동의어

# 주의!
# - 키워드와의 충돌을 피하기 위해 약자나 틀린 알파벳을 사용하지 말라.
frm = 100 # from 키워드의 약어
clazz = 100 # class 키워드를 일부러 틀리게 쓴 이름



