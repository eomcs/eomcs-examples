# 결론 (Conclusion)

> **함수는 작게, 하나의 일만 하며, 명확한 이름을 가져야 한다**

- 함수는 작아야 한다
- 하나의 일만 해야 한다
- 추상화 수준을 유지해야 한다
- 읽기 쉬워야 한다

👉 결국 목표는 하나:

> **읽기 쉬운 코드**

## 예제 1

```java
Bad
public static String testableHtml(PageData pageData, boolean includeSuiteSetup) throws Exception {
    WikiPage wikiPage = pageData.getWikiPage();
    StringBuffer buffer = new StringBuffer();
    if (pageData.hasAttribute("Test")) {
      if (includeSuiteSetup) {
        WikiPage suiteSetup =
            PageCrawlerImpl.getInheritedPage(SuiteResponder.SUITE_SETUP_NAME, wikiPage);
        if (suiteSetup != null) {
          WikiPagePath pagePath = suiteSetup.getPageCrawler().getFullPath(suiteSetup);
          String pagePathName = PathParser.render(pagePath);
          buffer.append("!include -setup .").append(pagePathName).append("\n");
        }
      }
      WikiPage setup = PageCrawlerImpl.getInheritedPage("SetUp", wikiPage);
      if (setup != null) {
        WikiPagePath setupPath = wikiPage.getPageCrawler().getFullPath(setup);
        String setupPathName = PathParser.render(setupPath);
        buffer.append("!include -setup .").append(setupPathName).append("\n");
      }
    }
    buffer.append(pageData.getContent());
    if (pageData.hasAttribute("Test")) {
      WikiPage teardown = PageCrawlerImpl.getInheritedPage("TearDown", wikiPage);
      if (teardown != null) {
        WikiPagePath tearDownPath = wikiPage.getPageCrawler().getFullPath(teardown);
        String tearDownPathName = PathParser.render(tearDownPath);
        buffer.append("\n").append("!include -teardown .").append(tearDownPathName).append("\n");
      }

      if (includeSuiteSetup) {
        WikiPage suiteTeardown =
            PageCrawlerImpl.getInheritedPage(SuiteResponder.SUITE_TEARDOWN_NAME, wikiPage);
        if (suiteTeardown != null) {
          WikiPagePath pagePath = suiteTeardown.getPageCrawler().getFullPath(suiteTeardown);
          String pagePathName = PathParser.render(pagePath);
          buffer.append("!include -teardown .").append(pagePathName).append("\n");
        }
      }
    }
    pageData.setContent(buffer.toString());
    return pageData.getHtml();
}
```

### 1. 함수가 너무 크다

testableHtml() 하나가 다음 일을 모두 한다.

- 테스트 페이지인지 확인
- SuiteSetUp 포함
- SetUp 포함
- 본문 추가
- TearDown 포함
- SuiteTearDown 포함
- HTML 변환

### 2. 추상화 수준이 섞여 있다

```java
고수준 판단
pageData.hasAttribute("Test")
```

처럼 의미 있는 고수준 판단과,

```java
세부 구현
buffer.append("!include -setup .")
      .append(pagePathName)
      .append("\n");
```

문자열 조립 세부 구현이 한 함수 안에 섞여 있다.

### 3. 중복이 많다

다음 패턴이 반복된다.

```java
WikiPage page = PageCrawlerImpl.getInheritedPage(...);

if (page != null) {
    WikiPagePath pagePath = page.getPageCrawler().getFullPath(page);
    String pagePathName = PathParser.render(pagePath);

    buffer.append(...)
          .append(pagePathName)
          .append("\n");
}
```

### 4. 플래그 인자가 있다

```java
boolean includeSuiteSetup
```

이 인자는 함수 내부에서 두 가지 흐름을 만든다.

```java
if (includeSuiteSetup) {
    ...
}
```

즉, 하나의 함수가 상황에 따라 여러 일을 한다.

### 5. 이름이 모호하다

```java
testableHtml()
```

- 이 이름만으로는 함수가 실제로 하는 일을 알기 어렵다.
- 실제로는 HTML을 만드는 것뿐 아니라 setup/teardown 페이지를 포함하는 작업까지 수행한다.

## 예제 2: 리팩토링

```java
Good: 리팩토링 후
public class SetupTeardownIncluder {

  private PageData pageData;
  private boolean isSuite;
  private WikiPage testPage;
  private StringBuffer newPageContent;
  private PageCrawler pageCrawler;

  public static String render(PageData pageData) throws Exception {
    return render(pageData, false);
  }

  public static String render(PageData pageData, boolean isSuite) throws Exception {
    return new SetupTeardownIncluder(pageData).render(isSuite);
  }

  private SetupTeardownIncluder(PageData pageData) {
    this.pageData = pageData;
    testPage = pageData.getWikiPage();
    pageCrawler = testPage.getPageCrawler();
    newPageContent = new StringBuffer();
  }

  private String render(boolean isSuite) throws Exception {
    this.isSuite = isSuite;
    if (isTestPage()) includeSetupAndTeardownPages();
    return pageData.getHtml();
  }

  private boolean isTestPage() throws Exception {
    return pageData.hasAttribute("Test");
  }

  private void includeSetupAndTeardownPages() throws Exception {
    includeSetupPages();
    includePageContent();
    includeTeardownPages();
    updatePageContent();
  }

  private void includeSetupPages() throws Exception {
    if (isSuite) includeSuiteSetupPage();
    includeSetupPage();
  }

  private void includeSuiteSetupPage() throws Exception {
    include(SuiteResponder.SUITE_SETUP_NAME, "-setup");
  }

  private void includeSetupPage() throws Exception {
    include("SetUp", "-setup");
  }

  private void includePageContent() throws Exception {
    newPageContent.append(pageData.getContent());
  }

  private void includeTeardownPages() throws Exception {
    includeTeardownPage();
    if (isSuite) includeSuiteTeardownPage();
  }

  private void includeTeardownPage() throws Exception {
    include("TearDown", "-teardown");
  }

  private void includeSuiteTeardownPage() throws Exception {
    include(SuiteResponder.SUITE_TEARDOWN_NAME, "-teardown");
  }

  private void updatePageContent() throws Exception {
    pageData.setContent(newPageContent.toString());
  }

  private void include(String pageName, String arg) throws Exception {
    WikiPage inheritedPage = findInheritedPage(pageName);
    if (inheritedPage != null) {
      String pagePathName = getPathNameForPage(inheritedPage);
      buildIncludeDirective(pagePathName, arg);
    }
  }

  private WikiPage findInheritedPage(String pageName) throws Exception {
    return PageCrawlerImpl.getInheritedPage(pageName, testPage);
  }

  private String getPathNameForPage(WikiPage page) throws Exception {
    WikiPagePath pagePath = pageCrawler.getFullPath(page);
    return PathParser.render(pagePath);
  }

  private void buildIncludeDirective(String pagePathName, String arg) {
    newPageContent.append("\n!include ").append(arg).append(" .").append(pagePathName).append("\n");
  }
}   
```

### Stepdown 구조 시각화

```text
render(PageData)
    └─ render(PageData, boolean)
        └─ render(boolean)
            ├─ isTestPage()
            └─ includeSetupAndTeardownPages()
                ├─ includeSetupPages()
                │    ├─ includeSuiteSetupPage()       [isSuite일 때]
                │    │    └─ include()
                │    │         ├─ findInheritedPage()
                │    │         ├─ getPathNameForPage()
                │    │         │    └─ PathParser.render()
                │    │         └─ buildIncludeDirective()
                │    └─ includeSetupPage()
                │         └─ include()
                │              ├─ findInheritedPage()
                │              ├─ getPathNameForPage()
                │              │    └─ PathParser.render()
                │              └─ buildIncludeDirective()
                ├─ includePageContent()
                ├─ includeTeardownPages()
                │    ├─ includeTeardownPage()
                │    │    └─ include()
                │    │         ├─ findInheritedPage()
                │    │         ├─ getPathNameForPage()
                │    │         │    └─ PathParser.render()
                │    │         └─ buildIncludeDirective()
                │    └─ includeSuiteTeardownPage()    [isSuite일 때]
                │         └─ include()
                │              ├─ findInheritedPage()
                │              ├─ getPathNameForPage()
                │              │    └─ PathParser.render()
                │              └─ buildIncludeDirective()
                └─ updatePageContent()
```
