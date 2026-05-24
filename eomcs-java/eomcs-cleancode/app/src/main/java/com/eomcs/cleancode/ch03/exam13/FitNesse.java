package com.eomcs.cleancode.ch03.exam13;

import java.util.HashMap;
import java.util.Map;

// FitNesse 프레임워크 관련 클래스들의 스텁 구현.
// 실제 FitNesse 라이브러리 없이 예제 코드가 컴파일되도록 최소한으로 구현한다.
public class FitNesse {

  public static class WikiPagePath {
    private final String path;
    WikiPagePath(String path) { this.path = path; }
    String getPath() { return path; }
  }

  public interface PageCrawler {
    WikiPagePath getFullPath(WikiPage page);
  }

  public static class WikiPage {
    private final String name;
    private final Map<String, WikiPage> children = new HashMap<>();

    public WikiPage(String name) { this.name = name; }
    public String getName() { return name; }

    public void addChild(WikiPage child) { children.put(child.getName(), child); }

    public PageCrawler getPageCrawler() {
      return page -> new WikiPagePath(page.getName());
    }
  }

  public static class PageCrawlerImpl {
    public static WikiPage getInheritedPage(String pageName, WikiPage context) {
      // 데모용 스텁: 항상 null 반환 (실제 구현은 페이지 계층을 탐색)
      return null;
    }
  }

  public static class PathParser {
    public static String render(WikiPagePath path) {
      return path.getPath();
    }
  }

  public static class SuiteResponder {
    public static final String SUITE_SETUP_NAME = "SuiteSetUp";
    public static final String SUITE_TEARDOWN_NAME = "SuiteTearDown";
  }

  public static class PageData {
    private final WikiPage wikiPage;
    private final Map<String, Boolean> attributes = new HashMap<>();
    private String content;

    public PageData(WikiPage wikiPage, String content, String... attributeNames) {
      this.wikiPage = wikiPage;
      this.content = content;
      for (String attr : attributeNames) {
        attributes.put(attr, true);
      }
    }

    public WikiPage getWikiPage() { return wikiPage; }
    public boolean hasAttribute(String name) { return attributes.getOrDefault(name, false); }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getHtml() { return "<html>" + content + "</html>"; }
  }
}
