# 가로 형식 맞추기 (Horizontal Formatting)

> 가로 형식은 한 줄 안에서 코드 요소들을 어떻게 배치할 것인가를 다룬다.

## 예제 1: 가로 공백과 밀집도 (Horizontal Openness and Density)

> **서로 관계가 약한 요소는 공백으로 분리하고, 강한 요소는 붙여 쓴다.**

```java
// Bad
private void measureLine(String line) {
    lineCount++;
    int lineSize=line.length();
    totalChars+=lineSize;
    lineWidthHistogram.addLine (lineSize,lineCount);
    recordWidestLine (lineSize);
}
```

- 연산자와 값이 붙어 있어 읽기 어렵다.
- 함수 이름과 인자의 괄호 사이에 공백이 있어서 서로 관련이 없어 보인다.
- 인자 사이에 공백이 없어서 인자 구분이 어렵다.

```java
// Good
private void measureLine(String line) {
    lineCount++;
    int lineSize = line.length();
    totalChars += lineSize;
    lineWidthHistogram.addLine(lineSize, lineCount);
    recordWidestLine(lineSize);
}
```

- 할당 연산자를 강조하려고 앞뒤에 공백을 줬다.
- 함수 이름과 인자의 괄호 사이의 공백을 제거하여 관련성을 높였다.
- 함수 호출의 인자 사이에도 공백을 줘서 인자들이 서로 별개임을 명확히 했다.

## 예제 2: 가로 정렬 (Horizontal Alignment)

> 변수나 대입문을 세로로 맞추려고 억지 정렬하지 마라.

```java
// Bad
private String  name;
private int     age;
private boolean active;
```

또는

```java
// Bad
userName     = "Bernard";
userAge      = 30;
userActive   = true;
```

- 이름보다 정렬에 눈이 간다.
- 변수의 타입, 이름, 값이 한 덩어리로 읽히지 않는다.
- 항목이 추가되면 정렬을 계속 다시 맞춰야 한다.

```java
// Good
private String name;
private int age;
private boolean active;
```

또는

```java
// Good
userName = "Bernard";
userAge = 30;
userActive = true;
```

- 불필요한 정렬을 제거한다.
- 각 줄을 자연스럽게 읽을 수 있다.
- 자동 포매터와도 잘 맞는다.

## 예제 3: 들여쓰기 (Indentation)

> 들여쓰기는 코드의 계층 구조를 보여준다.

들여쓰기가 없으면 코드의 구조가 사라진다.

```java
// Bad
public void process(User user) {
if (user != null) {
if (user.isActive()) {
save(user);
sendEmail(user);
}
}
}
```

- 블록 구조가 보이지 않는다.
- 어떤 코드가 어떤 조건 안에 있는지 파악하기 어렵다.

```java
// Good
public void process(User user) {
    if (user != null) {
        if (user.isActive()) {
            save(user);
            sendEmail(user);
        }
    }
}
```

- 클래스 내부 → 한 단계 들여쓰기
- 메서드 내부 → 한 단계 들여쓰기
- 조건문 내부 → 한 단계 더 들여쓰기

더 좋은 개선:

```java
// Good
public void process(User user) {
    if (isActiveUser(user)) {
        save(user);
        sendEmail(user);
    }
}

private boolean isActiveUser(User user) {
    return user != null && user.isActive();
}
```

- 들여쓰기가 깊어진다면 함수 분리를 고려한다.

### 들여쓰기 무시?

> 때로는 간단한 if 문, 짧은 while 문, 짧은 함수에서 들여쓰기 규칙을 무시하고픈 유혹이 생긴다.

```java
// Bad
public class CommentWidget extends TextWidget
{
    public static final String REGEXP = "^#[^\r\n]*(?:(?:\r\n)|\n|\r)?";

    public CommentWidget(ParentWidget parent, String text){super(parent, text);}
    public String render() throws Exception {return ""; }
}
```

```java
// Good
public class CommentWidget extends TextWidget {
    public static final String REGEXP = "^#[^\r\n]*(?:(?:\r\n)|\n|\r)?";

    public CommentWidget(ParentWidget parent, String text) {
        super(parent, text);
    }

    public String render() throws Exception {
        return "";
    }
}
```

-  하지만 일관된 들여쓰기는 코드의 가독성을 높이는 데 도움이 된다.

## 예제 4: 가짜 범위 (Dummy Scopes)

> 의미 없는 빈 블록을 만들지 마라.

특히 while, for 문에서 본문이 없는 경우 주의해야 한다.

```java
// Bad
while (input.read() != -1);
```

- 세미콜론 하나가 반복문의 본문이다.
- 실수인지 의도인지 구분하기 어렵다.

```java
// Good
while (input.read() != -1)
    ;
```

- 세미콜론을 새 행에다 들여써서 넣어 주면 눈에 띄게 된다.

```java
// Good: 더 좋은 개선
while (input.read() != -1) {
}
```

- 빈 블록이 눈에 보이므로 세미콜론의 실수를 방지할 수 있다.
- 하지만 여전히 의도가 약하다.

```java
// Good: 더 좋은 개선 2
while (hasMoreInput(input)) {
    discardCurrentInput(input);
}
```

- 가능한 한 의미 있는 함수로 표현한다.

```java
// Good: 더 좋은 개선 3
while (input.read() != -1) {
    // intentionally empty
}
```

- 정말 빈 블록이 필요하다면 주석으로 의도를 명확히 표시한다.

## 나쁜 코드 vs 좋은 코드

| 주제                              | 나쁜 코드           | 좋은 코드    |
| ------------------------------- | --------------- | -------- |
| Horizontal Openness and Density | 공백 없음 또는 과도한 공백 | 적절한 공백   |
| Horizontal Alignment            | 억지 세로 정렬        | 자연스러운 선언 |
| Indentation                     | 들여쓰기 없음         | 계층 구조 표현 |
| Dummy Scopes                    | 의미 없는 빈 블록      | 의도 표현    |

## 핵심 원칙

> 가로 형식은 코드를 예쁘게 꾸미는 것이 아니라, 한 줄 안에서 관계와 구조를 명확히 보여주는 방법이다.