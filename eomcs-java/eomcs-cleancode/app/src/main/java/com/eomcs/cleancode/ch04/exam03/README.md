# 좋은 주석 (Good Comments)

> 좋은 주석은 “나쁜 코드를 설명하는 주석”이 아니라, 코드만으로는 표현하기 어려운 정보를 보완하는 주석이다.

## 예제 1: 법적인 주석 (Legal Comments)

> 저작권, 라이선스 같은 법적 이유로 필요한 주석이다.

```java
// Copyright (C) 2026 Example Corp.
// Released under the MIT License.
public class UserService {
}
```

- 법적 고지는 허용된다.
- 단, 긴 법률 문서를 코드에 넣기보다 라이선스 파일을 참조하는 편이 좋다.

## 예제 2: 정보를 제공하는 주석 (Informative Comments)

> 코드만으로 즉시 알기 어려운 정보를 짧게 제공하는 주석이다.

```java
// kk:mm:ss EEE, MMM dd, yyyy 형식이다.
Pattern timeMatcher = Pattern.compile("\\d{2}:\\d{2}:\\d{2} \\w{3}, \\w{3} \\d{2}, \\d{4}");
```

## 예제 3: 의도를 설명하는 주석 (Explanation of Intent)

> 코드가 “무엇을 하는지”가 아니라, 왜 그렇게 했는지를 설명하는 주석이다.

- 구현 설명이 아니라 의도 설명이다.
- “왜 이런 선택을 했는가?”를 알려준다.

```java
public int compareTo(Object o)
{
    if(o instanceof WikiPagePath)
    {
        WikiPagePath p = (WikiPagePath) o;
        String compressedName = StringUtil.join(names, "");
        String compressedArgumentName = StringUtil.join(p.names, "");
        return compressedName.compareTo(compressedArgumentName);
    }
    return 1; // we are greater because we are the right type.
}
```

- 파라미터 타입이 다를 때 현재 객체가 더 높은 우선 순위를 갖도록 한다는 의도를 드러내는 주석이다.

```java
public void testConcurrentAddWidgets() throws Exception {
    WidgetBuilder widgetBuilder =
        new WidgetBuilder(new Class[]{BoldWidget.class});
    String text = "'''bold text'''";
    ParentWidget parent =
        new BoldWidget(new MockWidgetRoot(), "'''bold text'''");
    AtomicBoolean failFlag = new AtomicBoolean();
    failFlag.set(false);

    //This is our best attempt to get a race condition
    //by creating large number of threads.
    for (int i = 0; i < 25000; i++) {
        WidgetBuilderThread widgetBuilderThread =
            new WidgetBuilderThread(widgetBuilder, text, parent, failFlag);
        Thread thread = new Thread(widgetBuilderThread);
        thread.start();
    }
    assertEquals(false, failFlag.get());
}
```
- 스레드의 경쟁 조건을 유발하기 위해 많은 수의 스레드를 생성한다는 의도를 드러내는 주석이다.

## 예제 4: 의미를 명료하게 밝히는 주석 (Clarification)

> 표준 라이브러리나 외부 API처럼 수정할 수 없는 코드의 의미를 명확히 해주는 주석이다.

- 직접 고칠 수 없는 API의 반환값 의미를 설명할 때 유용하다.
- 단, 주석이 틀리면 매우 위험하므로 조심해야 한다.

```java
assertTrue(user.compareTo(user) == 0);     // user == user
assertTrue(admin.compareTo(user) < 0);     // admin comes before user
assertTrue(guest.compareTo(user) > 0);     // guest comes after user
```


## 예제 5: 결과를 경고하는 주석 (Warning of Consequences)

> 코드를 잘못 사용했을 때 발생할 수 있는 결과를 경고하는 주석이다.

- 성능, 동시성, 부작용 같은 위험을 알려준다.
- 다른 개발자의 실수를 예방한다.

```java
// SimpleDateFormat is not thread-safe.
// Create a new instance for each call.
public static SimpleDateFormat createStandardHttpDateFormat() {
    return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
}
```

- 스레드 안전 문제로 `SimpleDateFormat`을 매번 새로 생성해야 한다는 경고를 담은 주석이다.
- 이를 통해 프로그램 효율을 높이기 위해 정적 초기화 함수를 사용하려던 개발자의 실수를 예방한다.

```java
// Don't run unless you have some time to kill.
public void _testWithReallyBigFile() {
    writeLinesToFile(10000000);

    response.setBody(testFile);
    response.readyToSend(this);
    String responseString = output.toString();
    assertSubString("Content-Length: 1000000000", responseString);
    assertTrue(bytesSent > 1000000000);
}
```

- 테스트가 오래 걸릴 수 있으니 시간 여유가 있을 때 실행하라는 경고를 담은 주석이다.
- 테스트 케이스를 꺼야 하는 이유를 설명하는 주석이다.
- JUnit 4 이전에는 메서드 이름 앞에 `_`를 붙여서 테스트 케이스를 비활성화 하는 관례가 있었다.
- JUnit 4 이후에는 `@Ignore` 어노테이션을 사용하여 테스트를 비활성화 하는 것이 더 명확하다.

## 예제 6: TODO 주석 (TODO Comments)

> 지금 당장 해결할 수 없지만 나중에 처리해야 할 일을 표시한다.

- TODO는 나쁜 코드를 남기는 핑계가 아니다.
    - 더 이상 필요 없는 기능을 삭제하라는 알림
    - 누군가에게 문제를 봐달라는 요청
    - 더 좋은 이름을 떠올려달라는 부탁
    - 앞으로 발생할 이벤트에 맞춰 코드를 고치라는 주의
- 주기적으로 확인하고, 없애도 괜찮은 주석은 없애야 한다.

```java
// TODO-MdM these are not needed
// We expect this to go away when we do the checkout model
protected VersionInfo makeVersion() throws Exception {
    return null;
}
```

```java
// TODO: Replace this temporary discount rule after policy review.
public int calculateDiscount(User user) {
    return 10;
}
```

## 예제 7: 중요성을 강조하는 주석 (Amplification)

> 겉보기에는 사소해 보이지만 중요한 부분을 강조하는 주석이다.

- 작은 코드의 중요성을 강조한다.
- “이 줄은 그냥 있는 것이 아니다”를 알려준다.

```java
// the trim is real important. It removes the starting
// spaces that could cause the item to be recognized
// as another list.
new ListItemWidget(this, listItemContent, this.level + 1);
return buildList(text.substring(match.end()));
```

```java
String normalizedName = name.trim();

// The trim is important.
// Without it, names with trailing spaces are treated as different users.
return userRepository.findByName(normalizedName);
```

## 예제 8: 공개 API의 Javadocs (Javadocs in Public APIs)

> 외부 사용자가 호출하는 public API에는 Javadoc이 유용하다.

- public API는 사용자가 내부 구현을 볼 수 없기 때문에 문서가 필요하다.
- 단, 내부 코드에 무분별한 Javadoc을 붙이면 오히려 잡음이 된다.

```java
/**
 * Finds a user by email.
 *
 * @param email the user's email address
 * @return the matching user
 * @throws UserNotFoundException if no user exists with the given email
 */
public User findByEmail(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(UserNotFoundException::new);
}
```

## 좋은 주석

| 주석 유형                   | 목적            |
| ----------------------- | ------------- |
| Legal Comments          | 법적 고지         |
| Informative Comments    | 기본 정보 제공      |
| Explanation of Intent   | 의도 설명         |
| Clarification           | 모호한 API 의미 보완 |
| Warning of Consequences | 위험 경고         |
| TODO Comments           | 나중에 할 일 표시    |
| Amplification           | 중요성 강조        |
| Javadocs in Public APIs | 공개 API 문서화    |

## 핵심 원칙

- 좋은 주석은 코드를 변명하지 않고, 코드만으로 표현하기 어려운 정보를 보완한다.