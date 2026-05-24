# 나쁜 주석 (Bad Comments)

> 다음 예제들은 나쁜 주석의 유형과 그에 대한 설명을 보여준다.

## 예제 1: 주절거리는 주석 (Mumbling)

Bad:

```java
try {
    loadProperties();
} catch (IOException e) {
    // No properties means defaults
}
```

- 무슨 기본값이 어디서 로드되는지 불명확하다.

Good:

```java
try {
    loadProperties();
} catch (IOException e) {
    loadDefaultProperties();
}
```

## 예제 2: 같은 이야기를 중복하는 주석 (Redundant Comments)

Bad:

```java
// increments count by one
count++;
```

- 코드가 이미 말하는 내용을 반복한다.
- 주석이 코드보다 더 많은 정보를 제공하지 못한다.

Good:

```java
count++;
```

## 예제 3: 오해할 여지가 있는 주석 (Misleading Comments)

Bad:

```java
// Returns when the connection is closed
public void waitForClose(long timeoutMillis) {
    wait(timeoutMillis);
}
```

- 실제로는 연결이 닫힐 때가 아니라 timeout까지 기다릴 수 있다.

Good:

```java
public void waitForCloseOrTimeout(long timeoutMillis) {
    wait(timeoutMillis);
}
```

## 예제 4: 의무적으로 다는 주석 (Mandated Comments)

Bad:

```java
/**
 * @param name user name
 */
public void setName(String name) {
    this.name = name;
}
```

- 규칙 때문에 억지로 작성한 주석이다.

Good:

```java
public void setName(String name) {
    this.name = name;
}
```

## 예제 5: 이력을 기록하는 주석 (Journal Comments)

Bad:

```java
// 2024-01-01 Bernard created this class
// 2024-02-10 Jinyoung fixed login bug
// 2024-03-05 Updated validation
public class LoginService {
}
```

- 변경 이력은 Git 같은 버전 관리 시스템이 담당해야 한다.

Good:

```java
public class LoginService {
}
```

## 예제 6: 있으나 마나 한 주석 (Noise Comments)

Bad:

```java
// The user's name
private String name;

/**
 * default constructor
 */
protected AnnualDateRule() {
}
```

- 너무 당연한 사실을 언급하며 새로운 정보를 제공하지 못한다.

```java
private void startSending()
{
    try {
        doSending();
    } catch(SocketException e) {
        // normal. someone stopped the request.
    } catch(Exception e) {
        try {
            response.add(ErrorResponder.makeExceptionString(e));
            response.closeAll();
        } catch(Exception e1) {
            //Give me a break!
        }
    }
}
```

- "normal. someone stopped the request." 주석은 정상적인 상황을 설명한다.
- "Give me a break!" 주석은 아무런 정보를 제공하지 않는다.
- 프로그래머의 감정을 표현할 뿐이다.

Good:

```java
private String name;

protected AnnualDateRule() {
}
```

```java
private void startSending() {
    try {
        doSending();
    } catch(SocketException e) {
        // normal. someone stopped the request.
    } catch(Exception e) {
        addExceptionAndCloseResponse(e);
    }
}

private void addExceptionAndCloseResponse(Exception e) {
    try {
        response.add(ErrorResponder.makeExceptionString(e));
        response.closeAll();
    } catch(Exception e1) {
    }
}
```

- 있으나 마나 한 주석으르 달려는 유혹에서 벗어나 코드를 정리하라.
- 더 낫고, 행복한 프로그래머가 되는 지름길이다.

## 예제 7: 무서운 잡음 (Scary Noise)

Bad:

```java
/** The name. */
private String name;

/** The age. */
private int age;
```

- 형식만 갖춘 무의미한 Javadoc이다.
- 문서를 제공해야 한다는 잘못된 욕심으로 탄생한 잡음일 뿐이다.

Good:

```java
private String name;
private int age;
```

## 예제 8: 함수나 변수로 표현할 수 있다면 주석을 달지 마라 (Don’t Use a Comment When You Can Use a Function or a Variable)

Bad:

```java
// check if user can receive discount
if (user.isActive() && user.getPoint() > 1000) {
    applyDiscount(user);
}
```

```java
// does the module from the global list <mod> depend on the
// subsystem we are part of?
if (smodule.getDependSubsystems().contains(subSysMod.getSubSystem()))
```

Good:

```java
if (canReceiveDiscount(user)) {
    applyDiscount(user);
}
```

```java
ArrayList moduleDependees = smodule.getDependSubsystems();
String ourSubSystem = subSysMod.getSubSystem();
if (moduleDependees.contains(ourSubSystem))
```

- 주석이 필요하지 않도록 코드를 개선하는 편이 더 낫다.

## 예제 9: 위치를 표시하는 주석 (Position Markers)

Bad:

```java
// ================== Actions ==================
public void save() { }

public void delete() { }
```

- 대부분 불필요한 시각적 잡음이다.
- 배너 주석을 남용하면 독자가 흔한 잡음으로 여겨 무시한다.

Good:

```java
public void save() { }

public void delete() { }
```

## 예제 10: 닫는 괄호를 다는 주석 (Closing Brace Comments)

Bad:

```java
public class wc {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in));
        String line;
        int lineCount = 0;
        int charCount = 0;
        int wordCount = 0;
        try {
            while ((line = in.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                String words[] = line.split("\\W");
                wordCount += words.length;
            } //while
            System.out.println("wordCount = " + wordCount);
            System.out.println("lineCount = " + lineCount);
            System.out.println("charCount = " + charCount);
        } // try
        catch (IOException e) {
            System.err.println("Error:" + e.getMessage());
        } //catch
    } //main
}
```

- 중첩이 심하고 장황한 함수라면 의미가 있을지도 모른다.
- 하지만 작고 캡슐화된 함수에는 잡음일 뿐이다.

Good:

```java
public class WordCount {

    public static void main(String[] args) {
        try {
            CountResult result = countFromStdIn();
            printResult(result);
        } catch (IOException e) {
            printError(e);
        }
    }

    private static CountResult countFromStdIn() throws IOException {
        BufferedReader reader = createReader();

        String line;
        CountResult result = new CountResult();

        while ((line = reader.readLine()) != null) {
            processLine(line, result);
        }

        return result;
    }

    private static BufferedReader createReader() {
        return new BufferedReader(
                new InputStreamReader(System.in)
        );
    }

    private static void processLine(String line, CountResult result) {
        result.incrementLineCount();
        result.addCharCount(line.length());
        result.addWordCount(countWords(line));
    }

    private static int countWords(String line) {
        return line.split("\\W").length;
    }

    private static void printResult(CountResult result) {
        System.out.println("wordCount = " + result.getWordCount());
        System.out.println("lineCount = " + result.getLineCount());
        System.out.println("charCount = " + result.getCharCount());
    }

    private static void printError(IOException e) {
        System.err.println("Error: " + e.getMessage());
    }
}
```

- 함수가 작아서 구조가 명확해짐 → 닫는 괄호 주석이 필요 없어짐

## 예제 11: 공로를 돌리거나 저자를 표시하는 주석 (Attributions and Bylines)

Bad:

```java
// Added by Bernard
public void calculate() {
}
```

- 작성자 정보는 버전 관리 시스템이 더 정확히 관리한다.
- 오랫동안 코드에 방치되어 점차 부정확하고 쓸모없는 정보로 변하기 쉽다.

Good:

```java
public void calculate() {
}
```

## 예제 12: 주석 처리한 코드 (Commented-Out Code)

Bad:

```java
save(user);
// sendEmail(user);
// updateCache(user);
```

- 왜 남아 있는지 알 수 없고, 이유가 있어 남겨놓았으리라 생각하여 다른 사람들이 지우기를 주저한다.
- 시간이 지나면 쓰레기가 된다.

Good:

```java
save(user);
```

- 소스 코드 관리 시스템이 우리를 대신해 코드를 기억해준다.
- 필요하다면 언제든지 과거의 코드를 찾아볼 수 있다.
- 그냥 코드를 삭제하라.

## 예제 13: HTML 주석 (HTML Comments)

Bad:

```java
/**
 * <p>This method saves a user.</p>
 * <br/>
 */
public void save(User user) {
}
```

- 편집기/IDE에서조차 읽기가 어렵다.
- 도구로 주석을 뽑아 웹 페이지에 올릴 작정이라면, 주석에 HTML 태그를 삽입해야 하는 책임은 프로그래머가 아니라 도구가 져야 한다.

Good:

```java
/**
 * Saves a user.
 */
public void save(User user) {
}
```

- 위와 같이 뻔한 주석을 작성할 필요는 없지만, 단지 HTML 태그를 삽입하지 않는 것을 보여주기 위한 예시이다.

## 예제 14: 전역 정보 (Nonlocal Information)

Bad:

```java
// Default timeout is configured in SystemConfig as 30 seconds.
public void connect() {
}
```

- 현재 코드와 멀리 떨어진 정보를 설명한다. 그 정보가 바뀌면 주석은 거짓말이 된다.
- 코드 일부에 주석을 달면서 시스템의 전반적인 정보를 기술하지 마라.

Good:

```java
public void connect(Duration timeout) {
}
```

## 예제 15: 너무 많은 정보 (Too Much Information)

Bad:

```java
/*
 * HTTP was originally designed in...
 * RFC details...
 * Historical background...
 */
public void sendRequest() {
}
```

- 코드 이해에 불필요한 배경지식이 너무 많다.
- 주석에다 흥미로운 역사나 관련 없는 정보를 장황하게 늘어놓지 마라.

Good:

```java
public void sendRequest() {
}
```

## 예제 16: 모호한 관계 (Inobvious Connection)

Bad:

```java
// add extra space
String line = text + " ";
```

- 주석과 주석이 설명하는 코드 사이의 관계가 명확하지 않다.
- 왜 공백을 추가하는지 설명하지 않고 있다.

```java
/*
* start with an array that is big enough to hold all the pixels
* (plus filter bytes), and an extra 200 bytes for header info
*/
this.pngBytes = new byte[((this.width + 1) * this.height * 3) + 200];
```

- 'filter bytes'가 무엇인가? 한 픽셀이 한 바이트인가? 200을 추가하는 이유는?
- 주석을 다는 목적은 코드만으로 설명이 부족해서다.
- **주석 자체가 다시 설명을 요구하게 된다면, 그 주석은 나쁜 주석이다.**

Good:

```java
String line = appendDelimiterSpace(text);
```

- appendDelimiterSpace() 함수가 공백을 추가하는 이유를 설명한다.

```java
int filterBytes = (this.width + 1) * this.height * 3;
int headerBytes = 200;
this.pngBytes = new byte[filterBytes + headerBytes];
```

- 변수 이름이 주석 대신 설명한다.

## 예제 17: 함수 헤더 (Function Headers)

Bad:

```java
// This function saves the user
public void saveUser(User user) {
}
```

- 짧고 함수는 기 설명이 필요 없다.

Good:

```java
public void saveUser(User user) {
}
```

- 이름을 잘 붙인 함수가 주석으로 헤더를 추가한 함수보다 훨씬 좋다.

## 예제 18: 비공개 코드에서 Javadocs (Javadocs in Nonpublic Code)

Bad:

```java
/**
 * Calculates total price.
 *
 * @param items items
 * @return total price
 */
private int calculateTotalPrice(List<Item> items) {
    return items.stream()
            .mapToInt(Item::price)
            .sum();
}
```

- 공개하지 않을 코드라면 javadoc은 쓸모가 없다.
- 오히려 잡음이 된다.

Good:

```java
private int calculateTotalPrice(List<Item> items) {
    return items.stream()
            .mapToInt(Item::price)
            .sum();
}
```

## 통합 예제

Bad:

```java
/**
 * This class Generates prime numbers up to a user specified
 * maximum. The algorithm used is the Sieve of Eratosthenes.
 * <p>
 * Eratosthenes of Cyrene, b. c. 276 BC, Cyrene, Libya --
 * d. c. 194, Alexandria. The first man to calculate the
 * circumference of the Earth. Also known for working on
 * calendars with leap years and ran the library at Alexandria.
 * <p>
 * The algorithm is quite simple. Given an array of integers
 * starting at 2. Cross out all multiples of 2. Find the next
 * uncrossed integer, and cross out all of its multiples.
 * Repeat untilyou have passed the square root of the maximum
 * value.
 *
 * @author Alphonse
 * @version 13 Feb 2002 atp
 */
import java.util.*;
public class GeneratePrimes
{
    /**
    * @param maxValue is the generation limit.
    */
    public static int[] generatePrimes(int maxValue)
    {
        if (maxValue >= 2) // the only valid case
        {
            // declarations
            int s = maxValue + 1; // size of array
            boolean[] f = new boolean[s];
            int i;

            // initialize array to true.
            for (i = 0; i < s; i++)
                f[i] = true;
            
            // get rid of known non-primes
            f[0] = f[1] = false;
            
            // sieve
            int j;
            for (i = 2; i < Math.sqrt(s) + 1; i++)
            {
                if (f[i]) // if i is uncrossed, cross its multiples.
                {
                    for (j = 2 * i; j < s; j += i)
                        f[j] = false; // multiple is not prime
                }
            }

            // how many primes are there?
            int count = 0;
            for (i = 0; i < s; i++)
            {
                if (f[i])
                    count++; // bump count.
            }

            int[] primes = new int[count];
            
            // move the primes into the result
            for (i = 0, j = 0; i < s; i++)
            {
                if (f[i]) // if prime
                    primes[j++] = i;
            }

            return primes; // return the primes
        }
        else // maxValue < 2
            return new int[0]; // return null array if bad input.
    }
}
```

Good:

```java
/**
 * This class Generates prime numbers up to a user specified
 * maximum. The algorithm used is the Sieve of Eratosthenes.
 * Given an array of integers starting at 2:
 * Find the first uncrossed integer, and cross out all its
 * multiples. Repeat until there are no more multiples
 * in the array.
 */
public class PrimeGenerator
{
    private static boolean[] crossedOut;
    private static int[] result;

    public static int[] generatePrimes(int maxValue)
    {
        if (maxValue < 2)
            return new int[0];
        else
        {
            uncrossIntegersUpTo(maxValue);
            crossOutMultiples();
            putUncrossedIntegersIntoResult();
            return result;
        }
    }

    private static void uncrossIntegersUpTo(int maxValue)
    {
        crossedOut = new boolean[maxValue + 1];
        for (int i = 2; i < crossedOut.length; i++)
            crossedOut[i] = false;
    }

    private static void crossOutMultiples()
    {
        int limit = determineIterationLimit();
        for (int i = 2; i <= limit; i++)
            if (notCrossed(i))
                crossOutMultiplesOf(i);
    }

    private static int determineIterationLimit()
    {
        // Every multiple in the array has a prime factor that
        // is less than or equal to the root of the array size,
        // so we don't have to cross out multiples of numbers
        // larger than that root.
        double iterationLimit = Math.sqrt(crossedOut.length);
        return (int) iterationLimit;
    }

    private static void crossOutMultiplesOf(int i)
    {
        for (int multiple = 2*i; multiple < crossedOut.length; multiple += i)
            crossedOut[multiple] = true;
    }

    private static boolean notCrossed(int i)
    {
        return crossedOut[i] == false;
    }

    private static void putUncrossedIntegersIntoResult()
    {
        result = new int[numberOfUncrossedIntegers()];
        for (int j = 0, i = 2; i < crossedOut.length; i++)
            if (notCrossed(i))
                result[j++] = i;
    }

    private static int numberOfUncrossedIntegers()
    {
        int count = 0;
        for (int i = 2; i < crossedOut.length; i++)
            if (notCrossed(i))
                count++;

        return count;
    }
}
```

- 첫 번째 주석은 알고리즘을 설명하는 주석이라 이해를 위해 남겨둔다.
- 두 번째 주석은 루프 한계 값으로 제곱근을 사용한 이유를 설명하는 주석이다.

## 핵심 원칙

- 나쁜 주석은 코드를 설명하지 않는다.
- 오히려 코드의 문제를 숨기고, 시간이 지나면 거짓말이 된다.