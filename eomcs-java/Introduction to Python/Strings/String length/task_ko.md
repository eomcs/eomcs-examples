## 문자열 길이

`len()` 메서드는 문자열에 포함된 문자의 개수를 세는 데 사용됩니다.

예를 들어:
```python
s = "Hello World"
print(len(s))   # 11을 출력합니다
```

`/` 나눗셈 연산의 결과는 float 타입이라는 점에 유의하세요:
```python
a = 10/2
print(a)        # 5.0 출력
print(type(a))  # <class 'float'>
```

더 구조적이고 상세한 정보를 원한다면 [이 Hyperskill 지식 베이스 페이지](https://hyperskill.org/learn/step/5814?utm_source=jba&utm_medium=jba_courses_links)를 참조하세요.

### 과제
변수 `phrase`에 저장된 문자열의 앞쪽 절반을 가져오세요.  
참고: 인덱스를 얻을 때 타입 변환에 유의하세요.  

<div class='hint'>문자열의 시작부터 중간 지점까지 슬라이스를 얻어야 합니다.</div>

<div class='hint'>문자열 길이를 2로 나누어 중간 인덱스를 얻으세요. 나눗셈 결과는 정수여야 합니다.</div>