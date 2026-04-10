## 리스트 컴프리헨션

반복문을 사용하여 리스트(또는 다른 데이터 구조)를 만들 수 있습니다.  
예를 들어:

```python
my_list = []
for i in range(5):
    my_list.append(i)

print(my_list)
```
출력:
```text
[0, 1, 2, 3, 4]
```

이것도 좋지만, 다소 부피가 큽니다. 리스트 컴프리헨션은 기존 리스트나  
다른 이터러블(tuple, string, array, range 등)의 값을 기반으로 새로운 리스트를 만들 때  
더 간결한 구문을 제공합니다. 동일한 작업을 수행하며 프로그램을 단순화합니다.  
일반적으로 리스트 컴프리헨션은 한 줄 코드로 작성됩니다.

```python
my_list = [i for i in range(5)]
print(my_list)
```
출력:
```text
[0, 1, 2, 3, 4]
```

리스트 컴프리헨션은 또한 `for` 루프보다 계산적으로 더 효율적입니다.

보다 구조적이고 자세한 정보는 [이 Hyperskill Knowledge Base 페이지](https://hyperskill.org/learn/step/6315?utm_source=jba&utm_medium=jba_courses_links)를 참조하십시오.

### 작업
코드 편집기에서 리스트 컴프리헨션을 사용하여 `my_efficient_list`를  
`my_inefficient_list`의 요소 각각에 $10$을 더해 생성하세요. 예를 들어,  
`my_inefficient_list`의 첫 번째 요소가 $1 + 10 = 11$이라면,  
`my_efficient_list`의 첫 번째 요소는 $11 + 10 = 21$이 되어야 합니다.

<div class="hint">

위 예제에서, 우리는 `i for i in range(5)`를 사용했습니다.  
이 표현식 안에서 바로 `i`를 원하는 대로 수정할 수 있습니다.  
예를 들어, `i`에서 `5`를 빼고 싶다면,  
`i - 5 for i in range(5)`를 사용할 수 있습니다.
</div>