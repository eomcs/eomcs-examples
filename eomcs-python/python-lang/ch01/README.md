# 준비

## Dockerfile 준비

[Dockerfile](./Dockerfile)을 파일을 준비한다.

## Compose 파일 준비

[compose.yml](./compose.yml) 파일을 준비한다.

## 컨테이너 준비

**컨테이너 생성:**

```bash
docker compose up -d --build
```

**컨테이너 접속:**

```bash
docker compose exec python-lab bash
```

**파이썬 설치 