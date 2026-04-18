# Advanced Java

## 빌드 및 실행

### 빌드 스크립트 파일(build.gradle)

메인 클래스 설정:
```markdown
application {
    mainClass = project.findProperty('mainClass') ?: 'com.eomcs.advanced.App'
}
```

### 애플리케이션 실행 
빌드 스크립트 파일에 설정된 기본 메인 클래스 실행:
```bash
./gradlew -q run
```

특정 클래스 실행:
```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.App2
```

프로그램 아규먼트 넘기기:
```bash
./gradlew -q run -PmainClass=com.eomcs.advanced.App3 --args="홍길동 20"
```

