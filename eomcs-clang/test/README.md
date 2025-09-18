# C 컴파일 단계와 산출물

- Preprocessing
  - `$ clang -E hello.c`
    - hello.i
- Parsing and Semantic Analysis
  - `$ clang -Xclang -ast-dump -fsyntax-only hello.c > hello.ast`
    - hello.ast
- Code Generation and Optimization
  - `$ clang -S -emit-llvm hello.i`
    - hello.ll
  - `$ clang -c -emit-llvm hello.ll`
    - hello.bc
  - `$ clang -S hello.bc` 또는 `$ clang -S hello.ll`
    - hello.s
- Assembler
  - `$ clang -c hello.s`
   - hello.o
- Linker
  - `$ clang hello.o -o hello`
    - hello
    
