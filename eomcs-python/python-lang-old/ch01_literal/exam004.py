# 빈 줄(blank lines)
# - space, tab, form feed 만 있는 줄
# - 주석만 있는 줄
# - 소스 코드 해석(parsing)에서 무시된다.
# - 단 대화형 입력(interactive input)인 REPL에서는 
#   구현 방식에 따라 다를 수 있다. 
# - 표준 인터프리터에서는 멀티라인 문장을 종료하는 역할을 한다.
# - 공백도 주석도 없은 완전히 비어 있는 줄을 입력하면
#   멀티라인 문장이 종료 된다.
#   즉 입력을 완료했음을 파이썬 인터프리터에게 알린다.

# 다음 문장을 REPL에 직접 입력하여 확인할 것
# - break 문을 입력한 다음에 공백없이 빈 줄을 입려하라.
# - REPL은 즉시 while 문을 실행할 것이다.
a = 0
while True:
    a += 1
    if a > 10:
        break

    print(a)
