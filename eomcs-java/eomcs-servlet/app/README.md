# eomcs-servlet
자바 Servlet/JSP 웹 프로그래밍 예제 모음

## src-00

### 자바 웹 프로젝트 생성과 배포

1) `github.com` 에 생성한 저장소를 로컬에 복제한다.

```
~/git$ git clone 저장소URL
```

2) git 저장소를 자바 애플리케이션 프로젝트로 사용할 기본 폴더를 구성한다.
```
~/git/저장소$ gradle init
```

3) 웹 자원을 저장할 폴더를 추가한다.
```
~/git/저장소$ cd app/src/main
~/git/저장소/app/src/main$ mkdir webapp
``` 

4) 그래이들 빌드 스크립트 파일(build.gradle)을 설정한다.

- `java`, `eclipse-wtp`, `war` 플러그인 추가
- 자바 소스 파일의 문자 집합 설정
- 이클립스에서 출력할 프로젝트 이름 설정
- .war 파일의 이름 설정
- `servlet-api` 라이브러리 추가

build.gradle 파일의 예:
```
// Gradle이 사용할 플러그인을 장착한다.  
plugins {
    id 'java'
    id 'eclipse-wtp'
    id 'war'
}

// 자바 소스를 컴파일 할 때 적용할 옵션을 설정한다.
tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8';  // $javac -encoding UTF-8 ...
} 

// 이클립스에서 출력할 프로젝트 이름을 설정한다.
eclipse {
    project {
        name = "bitcamp-java-web"
    }
}

// .war 파일을 생성할 때 사용할 파일명을 설정한다.
// 이 이름은 웹 애플리케이션 이름으로도 사용한다.
war {
  archiveBaseName = "web"
}

repositories {
    jcenter()
}

dependencies {
    // 웹 애플리케이션을 제작할 때 사용하는 Servlet/JSP 라이브러리를 추가한다.
    // => 단 컴파일 할 때만 사용하고 배치할 때는 제외한다.
    // => implementation 대신에 providedCompile 옵셥을 사용하라.
    providedCompile 'javax.servlet:javax.servlet-api:4.0.1'
    
    testImplementation 'junit:junit:4.13'

    implementation 'com.google.guava:guava:29.0-jre'
}
```

5) 이클립스 IDE로 임포트한다.

이클립스 IDE에서 사용할 설정 파일을 생성한다.
```
~/src/저장소$ gradle eclipse
```

이클립스 IDE로 임포트 한다.
```
File > import... > Existing projects into Workspace
```

6) 테스트할 예제 파일을 준비한다.

- `src/main/webapp/hello.html` 예제 HTML 파일 추가
- `src/main/webapp/test.jpeg` 예제 이미지 파일 추가
- `src/main/java/com/eomcs/web/HelloServlet.java` 소스 파일 추가

7) CLI(Command Line Interface; 콘솔창/명령창) 환경에서 웹 프로젝트를 톰캣 서버에 배치하기

- .war 파일 생성
  - `$ gradle build` 실행
- .war 파일을 톰캣 서버에 배포
  - `$CATALINA_HOME/webapps/` 폴더에 복사
- 톰캣 서버 실행  
  - 현재 실행 중이면 재실행한다.
  - `$CATALINA_HOME/bin/startup.bat` 파일 실행(Windows)
  - `$CATALINA_HOME/bin/startup.sh` 파일 실행(Linux/macOS/Unix)
  - webapps/*.war 파일이 자동으로 압축이 풀린다.
- 테스트
  - `http://localhost:8080/web/hello.html` 요청

8) 이클립스IDE 에서 웹 프로젝트를 톰캣 서버에 배치하기

- 톰캣 서버가 설치된 경로를 이클립스에 등록한다.
  - 이클립스 메뉴 > Window >  Preferences...
    - Server > Runtime Environments > add 버튼 클릭
    - 톰캣 서버의 디렉토리를 등록한다.
- 웹 프로젝트를 배치할 때 사용할 테스트 서버 환경을 구축한다.
  - 이클립스 > Servers 뷰 > 새 서버 실행 환경 등록
- 웹 프로젝트를 테스트 서버에 배포한다.
  - 이클립스 > Servers 뷰 > 서버 실행 환경 > add
    - 프로젝트를 추가한다.
  - 이클립스 > Servers 뷰 > 서버 실행 환경 > publish
    - 웹 프로젝트에서 만든 파일을 테스트 서버의 배치 폴더에 복사한다.
    - 테스트 서버의 특정 폴더에 웹 프로젝트 배포 폴더를 생성한다.
      - $HOME/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core
        - tmpX 폴더를 생성한다.
        - tmpX/wtpwebapps/ 폴더에 웹 애플리케이션을 배포한다.
    - 배포 대상과 위치
      - 자바 클래스 ==> wtpwebapps/웹애플리케이션폴더/WEB-INF/classes/
        - src/main/java 에 있는 모든 소스 파일을 컴파일한다.
        - 클래스 파일(.class)을 해당 폴더에 복사한다.
      - 기타 자원 파일 ==> wtpwebapps/웹애플리케이션폴더/WEB-INF/classes/
        - src/main/resources 에 있는 모든 파일을 해당 폴더에 복사한다.
      - 웹 파일 ==> wtpwebapps/웹애플리케이션폴더/
        - src/main/webapp 에 있는 모든 파일을 해당 폴더에 복사한다.
      - 라이브러리 파일 ==> wtpwebapps/웹애플리케이션폴더/WEB-INF/lib/
        - 프로젝트에서 사용하는 의존 라이브러리 파일(*.jar)을 해당 폴더에 복사한다. 

9) 테스트 서버 실행한다.
- 이클립스 > Servers 뷰 > 서버 실행 환경 > start 실행
- 테스트
  - `http://localhost:8080/web/hello.html` 요청
  - `http://localhost:8080/web/test.jpg` 요청
  - `http://localhost:8080/web/hello` 요청
    

10) git 저장소 백업에서 제외할 파일을 설정한다.

- `.gitignore` 파일 추가

11) `github.com` 서버에 업로드 한다.

```
~/git/저장소$ git add .
~/git/저장소$ git commit -m "메시지"
~/git/저장소$ git push

```
