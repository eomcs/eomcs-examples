# str

# 암시적 문자열 연결(implicit line joining)
# - 여러 개의 문자열을 연속으로 나열하면 하나의 문자열로 간주한다.
print("1) Hello" "Python" "World!") 

print("2) Hello"
      "Python" 
      "World!") 

print("3) Hello"
      'Python' # 더블 쿼트(")와 싱글 쿼트(') 상관없다.
      "World!") 

# 명시적 문자열 연결(explicit line joining)
# - 백슬래시(\) 다음에 newline 코드가 오면 그 newline 코드는 무시한다. 
#   따라서 여러 줄의 문자열을 하나로 연결할 때 사용할 수 있다.
print("4) Hello\
Python\
World!")