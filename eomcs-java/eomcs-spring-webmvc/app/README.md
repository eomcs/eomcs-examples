# eomcs-spring-webmvc

Spring WebMVC 프레임워크 학습 예제

## src-00 : 웹 프로젝트 폴더 준비

프로젝트 기본 폴더 및 파일 생성
```
프로젝트폴더$ gradle init
```

프로젝트 폴더 기본 구조
```
build.gradle  <-- gradle 빌드 도구 설정 파일
settings.gradle  <-- gradle 빌드 도구 설정 파일
gradlew      <-- gradle 빌드 도구를 실행시키는 스크립트 파일(unix)
gradlew.bat  <-- gradle 빌드 도구를 실행시키는 스크립트 파일(windows)
.gradle/     <-- gradle이 실행 중에 사용하는 내부 폴더
gradle/      <-- gradle 빌드 도구를 다운로드 받는 자바 프로그램이 들어 있는 폴더.
                 gradlew 와 gradlew.bat 스크립트를 실행할 때 사용한다.
src/
  main/
    java/
    resources/
  test/
    java/
    resources/
```

### 이클립스 IDE 용 웹 프로젝트로 만들기

웹 리소스 파일을 저장할 폴더 준비
```
src/main/webapp   <--- 폴더 생성
```

build.gradle 파일 편집
```
plugins {
    id 'java'
    //id 'application' <--- 일반 자바 애플리케이션이 아니기 때문에 삭제한다.
    id 'eclipse-wtp'   <--- 이클립스 IDE 용 웹 프로젝트 관련 파일을 생성해주는 플러그인
    id 'war'           <--- 웹 애플리케이션 배포 파일(.war)을 만들어주는 플러그인
}

repositories {
    jcenter()
}

dependencies {
  implementation 'com.google.guava:guava:28.2-jre'
  testImplementation 'junit:junit:4.12'
}

// 'application' 플러그인을 설정하는 코드이다.
// 'application' 플러그인을 사용하지 않기 때문에 삭제한다.
/*
application {
    mainClassName = 'com.eomcs.App'
}
*/
```

gradle 을 실행하여 이클립스 IDE 용 웹 프로젝트 관련 파일을 생성 및 개정한다.
```
프로젝트폴더$ gradle eclipse
```

이클립스 IDE 로 웹 프로젝트를 import 한다.

## src-01 : 서블릿 프로그래밍 적용

servlet-api 라이브러리를 프로젝트에 등록한다.

build.gradle 변경
```
plugins {
    id 'java'
    id 'eclipse-wtp'
    id 'war'
}

repositories {
    jcenter()
}

dependencies {
    compileOnly 'javax.servlet:javax.servlet-api:4.0.1'   <--- servlet-api 추가
    implementation 'javax.servlet:jstl:1.2'               <--- jstl 추가

    implementation 'com.google.guava:guava:28.2-jre'
    testImplementation 'junit:junit:4.12'
}
```

gradle 을 실행하여 이클립스 IDE 용 웹 프로젝트 관련 파일을 개정한다.
```
프로젝트폴더$ gradle eclipse
```

이클립스 IDE에서 웹 프로젝트를 refresh 한다.


- HelloServlet 생성
- 서블릿 컨테이너에 배치 및 실행 테스트

## src-02 : 스프링 WebMVC 적용

- build.gradle 변경
  - `spring-webmvc` 라이브러리 추가
  - `log4j-core` 라이브러리(log4j 2.x) 추가
- /WEB-INF/web.xml 파일 준비
  - 프론트 컨트롤러 역할을 수행할 서블릿을 배치한다.
- /WEB-INF/app-servlet.xml 파일 생성
  - Spring IoC 컨테이너 설정 파일
- HelloController 생성

## src-03 : IoC 설정 파일의 위치

- /webapp/config/app-servlet.xml 로 위치 이동
  - 클라이언트가 자원을 가져갈 수 있는 경로에는 설정 파일을 두면 안된다.
  - 즉 /config 폴더 같은 위치에는 설정파일을 두지 말라.
- /WEB-INF/web.xml 변경

## src-04 : IoC 설정 파일의 위치

- /WEB-INF/app-servlet.xml 로 위치 이동
- /WEB-INF/web.xml 변경
  - contextConfigLocation 초기화 파라미터 삭제
  - /WEB-INF/서블릿이름-servlet.xml 파일을 기본으로 찾는다.
  - 없으면 예외가 발생한다.
- DispatcherServlet에서 IoC 컨테이너를 설정하기 싫다면,
  - contextConfigLocation의 값을 빈채로 두어라.

## src-05 : ContextLoaderListener와 DispatcherServlet의 IoC 컨테이너
 
- /WEB-INF/config/app-context.xml 로 위치 이동 및 이름 변경
  - <mvc:annotation-driven/> 태그 추가
  - ContextLoaderListener는 WebMVC 관련 애노테이션을 처리할 객체가 없기 때문에
    페이지 컨트롤러 객체를 생성하지 않는다.
  - 생성하고 싶으면 <mvc:annotation-driven/> 태그를 사용하여 
    WebMVC 관련 애노테이션을 처리하는 객체를 등록해야 한다.
- /WEB-INF/web.xml 변경
  - ContextLoaderListener 추가
  - ContextLoaderListener가 사용할 contextConfigLocation 파라미터 설정
  - DispatcherServlet에 contextConfigLocation 초기화 파라미터 추가.
    값은 빈채로 된다.

## src-06 : ContextLoaderListener와 DispatcherServlet의 관계

- ContextLoaderListener의 IoC 컨테이너
  - 모든 프론트 컨트롤러 및 페이지 컨트롤러가 공유할 객체를 보관한다.
  - /WEB-INF/config/app-context.xml 변경
- DispatcherServlet의 IoC 컨테이너
  - 페이지 컨트롤러, 인터셉터 등 웹 관련 객체를 보관한다.
  - /WEB-INF/app-servlet.xml 변경
  - /WEB-INF/admin-servlet.xml 추가
- /WEB-INF/web.xml 변경

## src-07 : Java Config로 DispatcherServlet의 IoC 컨테이너 설정하기

- bitcamp.AppConfig 클래스 생성
- /WEB-INF/web.xml 변경

## src-08 : SerlvetContainerInitializer 구현체의 활용

- Spring WebMVC의 WebApplicationInitializer를 이해하기 위한 기반 기술 소개.
- eomcs-web-library 프로젝트 준비
  - 자세한 것은 해당 프로젝트의 README.md 파일을 읽어 볼 것.
- eomcs-spring-webmvc/lib 폴더 생성
  - -web-library.jar 파일 넣기
- build.gradle 에 lib 폴더에 있는 .jar 파일을 의존 라이브러리에 추가하기
- MyWebInitializerImpl 클래스 생성
  - 이 클래스에서 DispatcherServlet 서블릿 등록하기
- web.xml 변경
  - DispatcherServlet 배치 정보 삭제

## src-09 : WebApplicationInitializer 구현체를 통해 DispatcherServlet 등록하기

- build.gradle 변경
  - 기존에 테스트를 위해 포함했던 eomcs-web-library.jar 파일 제거
- WebApplicationInitializerImpl 생성
  - 직접 IoC 컨테이너 준비
  - DispatcherServlet 생성
  - ServletContext를 통해 배치

## src-10 : WebApplicationInitializer 구현체를 통해 DispatcherServlet 등록하기 II

- WebApplicationInitializerImpl 변경
  - 직접 인터페이스를 구현하는 대신에 추상 클래스를 상속 받아 적절한 메서드를 오버라이딩 한다.
  - AbstractAnnotationConfigDispatcherServletInitializer 클래스 상속 받기

## src-11 : WebApplicationInitializer 구현체를 통해 DispatcherServlet 등록하기 III

- WebApplicationInitializerImpl 변경
  - 직접 인터페이스를 구현하는 대신에 추상 클래스를 상속 받아 적절한 메서드를 오버라이딩 한다.
  - AbstractDispatcherServletInitializer 클래스를 상속 받기

## src-12 : WebApplicationInitializer 구현체를 통해 DispatcherServlet 등록하기 응용 - XML 설정

- AppDispatcherServletInitializer 생성
  - ContextLoaderListener가 사용할 IoC 컨테이너 준비
  - DispatcherServlet이 사용할 IoC 컨테이너 준비
  - `/app/*` URL 매핑
  - 서블릿 이름은 `app` 으로 설정
- AdminDispatcherServletInitializer 생성
  - ContextLoaderListener가 사용할 IoC 컨테이너 준비하지 않는다.
    - 다른 서블릿이 이미 준비했기 때문이다.
  - DispatcherServlet이 사용할 IoC 컨테이너 준비
  - `/admin/*` URL 매핑
  - 서블릿 이름은 `admin` 으로 설정

## src-13 : WebApplicationInitializer 구현체를 통해 DispatcherServlet 등록하기 응용 - Java Config 설정

- RootConfig 생성
  - ContextLoaderListener의 IoC 컨테이너가 사용할 Java Config 클래스
- AppServletConfig 생성
  - `app` DispatcherServlet의 IoC 컨테이너가 사용할 Java Config 클래스
- AdminServletConfig 생성
  - `admin` DispatcherServlet의 IoC 컨테이너가 사용할 Java Config 클래스
- AppDispatcherServletInitializer 생성
  - AbstractAnnotationConfigDispatcherServletInitializer 를 상속 받는다.
  - `/app/*` URL 매핑
  - 서블릿 이름은 `app` 으로 설정
- AdminDispatcherServletInitializer 생성
  - AbstractAnnotationConfigDispatcherServletInitializer 를 상속 받는다.
  - `/admin/*` URL 매핑
  - 서블릿 이름은 `admin` 으로 설정
    -
## src-14 : Request Handler 정의하는 방법

- @Controller를 사용하여 페이지 컨트롤러 표시하기
- Request Handler의 아규먼트
- Request Handler의 리턴 타입과 View Resolver
- Request Handler의 URL @PathVariable 과  @MatrixVariable
- Request Handler의 @SessionAttributes와 @ModelAttribute
- 인터셉터 다루기
