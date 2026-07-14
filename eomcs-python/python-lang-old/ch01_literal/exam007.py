# 인코딩 선언(encoding declarations)
# coding=utf-16
# - 첫 번째 줄에 주석으로 인코딩을 선언할 수 있다.
# - 두 번째 줄 주석으로 인코딩을 선언할 경우 첫 번째 줄이 주석이어야 한다. 
# - 형식
#   coding=문자집합 또는 coding:문자집합
# - 인코딩 선언이 없다면 소스 파일의 기본 인코딩은 UTF-8로 간주한다.
# - 문자집합으로 지정할 수 있는 이름은 다음 링크의 문서 내용을 참조하라.
#     https://docs.python.org/3/library/codecs.html#standard-encodings

print("안녕하세요!")

# 권장 형식
# 예1) GNU Emacs 에서 인식하는 포맷
# -*- coding: 문자집합 -*-

# 예2) Bram Moolenaar's VIM 에서 인식하는 포맷
# vim:fileencoding=문자집합

