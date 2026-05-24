package com.eomcs.cleancode.ch03.exam13;

import com.eomcs.cleancode.ch03.exam13.FitNesse.PageCrawler;
import com.eomcs.cleancode.ch03.exam13.FitNesse.PageCrawlerImpl;
import com.eomcs.cleancode.ch03.exam13.FitNesse.PageData;
import com.eomcs.cleancode.ch03.exam13.FitNesse.PathParser;
import com.eomcs.cleancode.ch03.exam13.FitNesse.SuiteResponder;
import com.eomcs.cleancode.ch03.exam13.FitNesse.WikiPage;
import com.eomcs.cleancode.ch03.exam13.FitNesse.WikiPagePath;

public class Good {

  // Good: 리팩토링 후 — SetupTeardownIncluder 클래스
  //
  // 개선 1 - 함수가 작아졌다: 각 단계(setup 포함, 본문 추가, teardown 포함)가 별도 메서드로 분리되었다.
  //
  // 개선 2 - 추상화 수준이 일치한다: render()는 고수준 흐름만, include()는 세부 구현만 담당한다.
  //
  // 개선 3 - 중복이 제거되었다: 페이지 찾기 → 경로 구하기 → 디렉티브 추가 패턴이
  //          include() 하나로 통합되었다.
  //
  // 개선 4 - 플래그 인자가 인스턴스 필드로 이동했다: isSuite 필드가 클래스 상태로 관리되어
  //          메서드 시그니처가 단순해졌다.
  //
  // 개선 5 - 이름이 명확하다: SetupTeardownIncluder, includeSetupPages(), includeTeardownPages()
  //          등 이름만으로 역할을 알 수 있다.
  static class SetupTeardownIncluder {
    private PageData pageData;
    private boolean isSuite;
    private WikiPage testPage;
    private StringBuffer newPageContent;
    private PageCrawler pageCrawler;

    // Stepdown 구조 — render(PageData)를 시작으로 추상화 수준이 단계적으로 낮아진다.
    //
    // render(PageData)
    //  └─ render(PageData, boolean)
    //       └─ render(boolean)
    //            ├─ isTestPage()
    //            └─ includeSetupAndTeardownPages()
    //                 ├─ includeSetupPages()
    //                 │    ├─ includeSuiteSetupPage()       [isSuite일 때]
    //                 │    │    └─ include()
    //                 │    │         ├─ findInheritedPage()
    //                 │    │         ├─ getPathNameForPage()
    //                 │    │         │    └─ PathParser.render()
    //                 │    │         └─ buildIncludeDirective()
    //                 │    └─ includeSetupPage()
    //                 │         └─ include()
    //                 │              ├─ findInheritedPage()
    //                 │              ├─ getPathNameForPage()
    //                 │              │    └─ PathParser.render()
    //                 │              └─ buildIncludeDirective()
    //                 ├─ includePageContent()
    //                 ├─ includeTeardownPages()
    //                 │    ├─ includeTeardownPage()
    //                 │    │    └─ include()
    //                 │    │         ├─ findInheritedPage()
    //                 │    │         ├─ getPathNameForPage()
    //                 │    │         │    └─ PathParser.render()
    //                 │    │         └─ buildIncludeDirective()
    //                 │    └─ includeSuiteTeardownPage()    [isSuite일 때]
    //                 │         └─ include()
    //                 │              ├─ findInheritedPage()
    //                 │              ├─ getPathNameForPage()
    //                 │              │    └─ PathParser.render()
    //                 │              └─ buildIncludeDirective()
    //                 └─ updatePageContent()
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
      if (isTestPage()) {
        includeSetupAndTeardownPages();
      }
      return pageData.getHtml();
    }

    private boolean isTestPage() {
      return pageData.hasAttribute("Test");
    }

    private void includeSetupAndTeardownPages() throws Exception {
      includeSetupPages();
      includePageContent();
      includeTeardownPages();
      updatePageContent();
    }

    private void includeSetupPages() throws Exception {
      if (isSuite) {
        includeSuiteSetupPage();
      }
      includeSetupPage();
    }

    private void includeSuiteSetupPage() throws Exception {
      include(SuiteResponder.SUITE_SETUP_NAME, "-setup");
    }

    private void includeSetupPage() throws Exception {
      include("SetUp", "-setup");
    }

    private void includePageContent() {
      newPageContent.append(pageData.getContent());
    }

    private void includeTeardownPages() throws Exception {
      includeTeardownPage();
      if (isSuite) {
        includeSuiteTeardownPage();
      }
    }

    private void includeTeardownPage() throws Exception {
      include("TearDown", "-teardown");
    }

    private void includeSuiteTeardownPage() throws Exception {
      include(SuiteResponder.SUITE_TEARDOWN_NAME, "-teardown");
    }

    private void updatePageContent() {
      pageData.setContent(newPageContent.toString());
    }

    private void include(String pageName, String arg) throws Exception {
      WikiPage inheritedPage = findInheritedPage(pageName);
      if (inheritedPage != null) {
        String pagePathName = getPathNameForPage(inheritedPage);
        buildIncludeDirective(pagePathName, arg);
      }
    }

    private WikiPage findInheritedPage(String pageName) {
      return PageCrawlerImpl.getInheritedPage(pageName, testPage);
    }

    private String getPathNameForPage(WikiPage page) {
      WikiPagePath pagePath = pageCrawler.getFullPath(page);
      return PathParser.render(pagePath);
    }

    private void buildIncludeDirective(String pagePathName, String arg) {
      newPageContent
          .append("\n!include ")
          .append(arg)
          .append(" .")
          .append(pagePathName)
          .append("\n");
    }
  }
}
