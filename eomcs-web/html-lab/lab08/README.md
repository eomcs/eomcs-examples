# 웹 서버 구축과 배포

- Docker를 이용한 웹 서버 구축과 배포 실습

## Windows 11에서 준비

WSL 설치 상태 확인:

```bash
wsl --status
```

WSL 설치:

```bash
wsl --install
```

Windows OS 환경이 준비되었는지 확인:

```bash
dism.exe /online /get-features /format:table | findstr /i "Microsoft-Windows-Subsystem-Linux VirtualMachinePlatform"
```

준비가 된 상태:
```text
Microsoft-Windows-Subsystem-Linux    Enabled
VirtualMachinePlatform                Enabled
```

준비가 안되었다면:

```bash
dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart

dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart

```


## Docker Desktop 설치

Docker Desktop 설치는 [Docker 공식 홈페이지](https://www.docker.com/products/docker-desktop/)에서 운영체제에 맞는 설치 파일을 다운로드하여 설치한다.

## Dockerfile 작성

Dockerfile은 Docker 이미지를 만드는 절차를 작성한 텍스트 파일이다. 이 실습에서는 Apache HTTP Server가 설치된 Docker 공식 이미지 `httpd:2.4`를 사용하고, `lab08/www` 디렉토리에 들어 있는 모든 파일과 하위 디렉토리를 웹 서버의 문서 루트인 `/usr/local/apache2/htdocs/`에 복사한다. [httpd 공식 이미지 안내](https://hub.docker.com/_/httpd)

### 파일 생성하기

`lab08/www` 디렉토리를 만들고 배포할 파일과 하위 디렉토리를 모두 넣는다. 첫 화면으로 표시할 파일은 `lab08/www/index.html`로 준비한다.

`lab08` 디렉토리에 확장자 없이 이름이 `Dockerfile`인 파일을 만들고 다음 내용을 작성한다.

```dockerfile
FROM httpd:2.4
COPY ./www/ /usr/local/apache2/htdocs/
```

### 명령어의 역할

| 명령어 | 설명 |
| --- | --- |
| `FROM httpd:2.4` | Apache HTTP Server 2.4 계열의 공식 이미지를 기본 이미지로 사용한다. |
| `COPY ./www/ /usr/local/apache2/htdocs/` | 빌드 컨텍스트의 `www` 디렉토리에 있는 모든 파일과 하위 디렉토리를 이미지의 웹 문서 루트에 복사한다. |

`httpd`는 이미지 이름이고, `2.4`는 사용할 버전 계열을 지정하는 **태그**이다. 각 명령어의 문법은 [Dockerfile 공식 문서](https://docs.docker.com/reference/dockerfile/)에서 확인할 수 있다.

### 기본 이미지에 포함된 설정

- Apache HTTP Server와 실행에 필요한 라이브러리가 설치되어 있다.
- 기본 웹 문서 루트는 `/usr/local/apache2/htdocs/`이다.
- `EXPOSE 80`으로 컨테이너가 사용하는 TCP 포트 정보가 기록되어 있다.
- `CMD ["httpd-foreground"]`로 컨테이너가 시작될 때 웹 서버를 실행하도록 설정되어 있다.

이 설정들을 기본 이미지에서 상속하므로, 실습용 Dockerfile에는 기본 이미지 선택과 파일 복사만 작성하면 된다. [공식 이미지의 Dockerfile](https://github.com/docker-library/httpd/blob/master/2.4/Dockerfile)

### 파일 복사 범위

**빌드 컨텍스트**는 Docker에 빌드 재료로 전달하는 파일과 디렉토리의 범위이다. 다음 섹션의 `docker build -t my-apache2 .`를 `lab08` 디렉토리에서 실행하면 `lab08`이 빌드 컨텍스트가 된다. `COPY`의 원본 경로 `./www/`는 이 빌드 컨텍스트를 기준으로 하므로 `lab08/www/`를 가리킨다.

- `lab08/www/index.html`은 이미지의 `/usr/local/apache2/htdocs/index.html`로 복사된다.
- `www` 디렉토리의 내용이 문서 루트에 바로 복사된다. 문서 루트 아래에 `www` 디렉토리가 추가로 만들어지는 것은 아니다.
- `www` 안의 모든 파일과 하위 디렉토리는 구조를 유지하여 함께 복사된다. `.dockerignore`를 사용하는 경우 배포할 파일이 제외되지 않도록 한다.
- `www` 밖에 있는 `lab08/README.md`, `lab08/Dockerfile` 등은 웹 문서 루트에 복사되지 않는다.
- 페이지에서 사용하는 이미지, CSS, JavaScript와 링크 대상 HTML 파일도 `www` 안에 넣고, 배포 후의 경로에 맞게 참조한다.

파일 복사는 빌드 시점에 이루어진다. 이후 HTML 파일을 수정한 내용을 반영하려면 이미지를 다시 빌드하고 새 이미지로 컨테이너를 다시 생성한다. [Docker의 COPY 설명](https://docs.docker.com/reference/dockerfile/#copy)

### 서버 실행 방식과 포트

기본 실행 명령인 `httpd-foreground`는 내부적으로 `httpd -DFOREGROUND`를 실행하여 Apache HTTP Server가 포그라운드에서 계속 동작하도록 한다. 컨테이너는 주 실행 프로세스가 종료되면 함께 종료되므로, 웹 서버를 계속 실행하는 방식이 필요하다. [공식 이미지의 시작 스크립트](https://github.com/docker-library/httpd/blob/master/2.4/httpd-foreground)

뒤에서 사용하는 `docker run -d`는 호스트 터미널에서 컨테이너를 분리해 실행하는 옵션이다. 컨테이너 내부에서 Apache2를 포그라운드로 실행하는 것과 함께 사용할 수 있다.

기본 이미지의 `EXPOSE 80`은 컨테이너가 사용하는 포트에 대한 정보이다. 호스트에서 접속하려면 다음 섹션의 `-p 8080:80`으로 호스트의 8080번 포트를 컨테이너의 80번 포트에 연결한다. 실행 후 브라우저에서 `http://localhost:8080/`에 접속하면 복사한 `index.html`을 확인할 수 있다. [Docker의 EXPOSE 설명](https://docs.docker.com/reference/dockerfile/#expose)

## Docker 이미지 빌드

- Dockerfile이 있는 디렉토리에서 다음 명령어를 실행하여 Docker 이미지를 빌드한다.

```bash
docker build -t my-apache2 .
```

## Docker 컨테이너 실행

- 빌드한 이미지를 기반으로 Docker 컨테이너를 실행한다.

```bash
# 도커 컨테이너 실행
docker run -dit --name my-webserver -p 8080:80 my-apache2

# 도커 컨테이너 상태 확인
docker ps

# 도커 컨테이너 종료
docker stop my-webserver

# 도커 컨테이너 삭제
docker rm my-webserver
```
