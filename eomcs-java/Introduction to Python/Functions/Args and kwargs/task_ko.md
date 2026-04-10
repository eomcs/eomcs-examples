## Args와 kwargs

형식 매개변수 중 마지막에 `**name` 형태의 매개변수가 있으면, 해당 매개변수는 모든 키워드 인수를 포함한 사전을 받습니다. (참고: [데이터 구조 — 사전(Dictionaries)](course://Data structures/Dictionaries)) 이때, 해당 키워드 인수는 형식 매개변수에 해당하지 않는 경우에만 포함됩니다. 이 매개변수는 `*name` 형태의 매개변수와 결합하여 사용할 수 있습니다. 이 경우, `*name`은 형식 매개변수 목록을 초과하는 모든 위치 인수를 포함하는 튜플을 받습니다(`*name`은 반드시 `**name`보다 먼저 위치해야 합니다). 예를 들어, 코드 편집기의 코드와 같은 함수를 정의하면, 호출 예 1번처럼 호출할 수 있으며, 다음과 같은 출력이 나타납니다:

```text
-- Do you know how to get to the Library ?
-- I'm sorry, I am not from here, no idea about the Library
Do you at least have a cigar, sir?
Sure, help yourself.
----------------------------------------
lost_person : old banker
other_guy : street clown
scene : in a park
```

이 함수는 임의 개수의 인수로 호출할 수 있습니다. 이러한 인수는 튜플 또는 리스트에 묶이게 됩니다(참고: [튜플](course://Data structures/Tuples), [리스트](course://Data structures/Lists introduction)). 가변 인수 앞에는 0개 이상의 일반 인수가 올 수 있으며, 이 경우에는 `place`라는 하나의 일반 인수가 있습니다. `*args` 매개변수 뒤에 나오는 모든 형식 매개변수는 [‘키워드 전용’](https://peps.python.org/pep-3102/) 매개변수가 됩니다. 즉, 이러한 매개변수는 위치 인수가 아닌 키워드로만 사용할 수 있습니다. 이 함수를 호출하는 또 다른 방법은 호출 예 2번과 같으며, 같은 결과를 출력합니다.

더 구조적이고 자세한 정보를 원하신다면 [여기](https://hyperskill.org/learn/step/8560?utm_source=jba&utm_medium=jba_courses_links)와 [여기](https://hyperskill.org/learn/step/9544?utm_source=jba&utm_medium=jba_courses_links) Hyperskill 지식 기반 페이지를 참고하십시오.

### 작업

코드 편집기에서 아래 `cat()` 함수의 코드를 수정하여 다음을 출력하도록 만드세요:
```text
-- This cat would eat if you gave it anything
-- Lovely fur, the Maine Coon
-- It's fat !
IT IS TOO FAT.
YOU ARE FEEDING YOUR CAT TOO MUCH.
```

<div class="hint"><code>*</code>를 사용하여 추가 위치 인수를 언팩하는 것을 기억하세요.</div>

<div class="hint"><code>**</code>를 사용하여 키워드 인수를 언팩하는 것을 기억하세요.</div>

<div class="hint">형식 매개변수 <code>food</code>에 값을 제공하는 것을 잊지 마세요.</div>