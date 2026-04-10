## 클래스 및 인스턴스 변수

일반적으로, 인스턴스 변수는 각 인스턴스에 고유한 데이터를 저장하고,  
클래스 변수는 클래스의 모든 인스턴스에서 공유되는 속성과 메서드를 나타냅니다:

```python
class Cat:

    species = "Felis catus"  
    
    def __init__(self, breed, name):
        self.breed = breed  
        self.name = name

cleo = Cat('mix', 'Cleo')
furry = Cat('bengal', 'Furry')

print(cleo.name)
print(cleo.species)
print(furry.name)
print(furry.species)
```

```text
Cleo
Felis catus
Furry
Felis catus
```
`species`는 클래스의 모든 인스턴스에서 공유되는 클래스 변수이고,  
`name`과 `breed`는 각 인스턴스에 고유한 인스턴스 변수임을 확인할 수 있습니다.

공유 데이터는 목록(list)이나 딕셔너리(dictionary) 같은 변경 가능한 객체가 관여할 때  
의외의 결과를 초래할 수 있습니다. 예를 들어, 클래스 변수가 목록일 경우, 한 객체에서 이를 수정하면  
클래스의 모든 객체에 동일한 변경이 적용됩니다(코드 에디터에 있는 예제를 참고하세요 – `print(barsik.favorite_food)`의 결과를 확인해 보세요).  
만약 각 인스턴스에 고유한 특징을 목록으로 관리하려면, 이를 인스턴스 속성으로 만들어야 합니다.

더 구조적이고 자세한 정보는 [이 Hyperskill 지식 페이지](https://hyperskill.org/learn/step/6677?utm_source=jba&utm_medium=jba_courses_links)를 참고하세요.

### 작업
코드 에디터에서 `Animals` 클래스의 구현을 완료하여  
아래의 `print` 문이 다음과 같은 문장을 각 애완동물마다 출력하도록 하십시오:  
`"This is Doggy the dog, one of my pets."`

<div class="hint">클래스 변수는 모든 인스턴스에서 공유되는 정보를 포함해야 합니다 (<code>"pets"</code> 중 하나입니다).</div>
<div class="hint">인스턴스 변수는 각 인스턴스에서 고유한 정보를 포함해야 합니다 (이름은 고유합니다).</div>