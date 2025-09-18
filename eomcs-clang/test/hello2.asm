.data

    HelloWorldString:
    .ascii "Hello!\n"

.text

.globl _main

_main:
    movl $0x2000004, %eax
    movl $1, %ebx
    movq HelloWorldString@GOTPCREL(%rip), %rsi
    movq $100, %rdx
    syscall

    movl $0x2000001, %eax
    movl $0, %ebx
    syscall

# as -arch x86_64  -o hello.o hello.asm
# ld -o hello hello.o