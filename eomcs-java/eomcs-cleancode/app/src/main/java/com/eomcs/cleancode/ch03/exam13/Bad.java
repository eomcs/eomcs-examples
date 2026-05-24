package com.eomcs.cleancode.ch03.exam13;

import com.eomcs.cleancode.ch03.exam13.FitNesse.PageCrawlerImpl;
import com.eomcs.cleancode.ch03.exam13.FitNesse.PageData;
import com.eomcs.cleancode.ch03.exam13.FitNesse.PathParser;
import com.eomcs.cleancode.ch03.exam13.FitNesse.SuiteResponder;
import com.eomcs.cleancode.ch03.exam13.FitNesse.WikiPage;
import com.eomcs.cleancode.ch03.exam13.FitNesse.WikiPagePath;

public class Bad {

  // Bad: 함수 초기 버전 — 하나의 함수가 너무 많은 일을 한다.
  //
  // 문제 1 - 함수가 너무 크다: 테스트 페이지 확인, SuiteSetUp 포함, SetUp 포함,
  //          본문 추가, TearDown 포함, SuiteTearDown 포함, HTML 변환을 모두 수행한다.
  //
  // 문제 2 - 추상화 수준이 섞여 있다:
  //          고수준 판단(pageData.hasAttribute("Test"))과
  //          세부 구현(buffer.append("!include -setup .").append(...))이 한 함수에 뒤섞인다.
  //
  // 문제 3 - 중복이 많다: 페이지를 찾아 경로를 구해 버퍼에 추가하는 패턴이 4번 반복된다.
  //
  // 문제 4 - 플래그 인자가 있다: includeSuiteSetup 하나가 함수 내부에서 두 갈래 흐름을 만든다.
  //
  // 문제 5 - 이름이 모호하다: testableHtml()이라는 이름만으로는
  //          setup/teardown 페이지를 포함한다는 사실을 알 수 없다.
  static String testableHtml(PageData pageData, boolean includeSuiteSetup) throws Exception {
    WikiPage wikiPage = pageData.getWikiPage();
    StringBuffer buffer = new StringBuffer();

    if (pageData.hasAttribute("Test")) {
      if (includeSuiteSetup) {
        WikiPage suiteSetup = PageCrawlerImpl.getInheritedPage(SuiteResponder.SUITE_SETUP_NAME, wikiPage);
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
        WikiPage suiteTeardown = PageCrawlerImpl.getInheritedPage(SuiteResponder.SUITE_TEARDOWN_NAME, wikiPage);
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
}
