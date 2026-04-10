## 모듈을 스크립트로 실행하기

파이썬 모듈은 실행 가능한 문(statement)과 함수 또는 클래스 정의를 포함하는 파일입니다.  
이러한 문은 `import` 문에서 해당 모듈 이름이 처음으로 호출될 때 실행됩니다.  
파일 이름은 .py 접미사가 붙은 모듈 이름입니다. 모듈 내부에서는  
모듈의 이름이 문자열 형태로 전역 변수 `__name__`의 값으로 제공됩니다.

모듈을 **직접** 실행할 경우(즉, 다른 모듈에 임포트되지 않고 실행하는 경우),  
모듈 내부의 코드는 마치 임포트된 것처럼 실행됩니다. 그러나 `__name__` 변수는  
`"__main__"`으로 설정됩니다.

`__name__`과 `__main__`을 다음과 같이 사용할 수 있습니다:

```python
if __name__ == "__main__":
   # 여기에서 작업을 수행합니다
```

이 블록 내부의 문(statement)은 모듈이 직접 실행될 때만 실행되며, 다른 모듈에  
임포트될 경우에는 실행되지 않습니다. 예를 들어, 두 개의 파일을 고려해 봅시다:

main_program:
```python
import some_module

print(f"main_program __name__ is: {__name__}")

if __name__ == "__main__":
   print("main_program executed directly")
else:
   print("main_program executed when imported")
```

some_module:
```python
print(f"some_module __name__ is: {__name__}")

if __name__ == "__main__":
   print("some_module executed directly")
else:
   print("some_module executed when imported")
```

`main_program`을 실행한 후 출력 결과:
```text
some_module __name__ is: some_module
some_module executed when imported
main_program __name__ is: __main__
main_program executed directly
```

`some_module`을 실행한 후 출력 결과:
```text
some_module __name__ is: __main__
some_module executed directly
```

더 구조적이고 자세한 정보는 [이 Hyperskill 지식 페이지](https://hyperskill.org/learn/step/6057?utm_source=jba&utm_medium=jba_courses_links)를 참고하시기 바랍니다.

### 작업
<i>이 작업에서 파일 이름은 위의 예제와 동일하지만 코드가 약간 다릅니다.</i>

`task.py`와 `some_module.py`를 수정하여 `task.py`를 실행했을 때, 다음 출력이 나오도록 만드십시오:

```text
This is a message from some_module.
This is a message from __main__.
This is a message from the function in the imported module.
This should be printed ONLY when task.py is called directly.