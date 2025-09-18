#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main() {
  int i = 0;
  pid_t processId = 0;
  
  processId = fork(); // 현재 실행 위치에서 프로세스 복제
  
  for (i = 0; i < 10000; i++) {
    printf("[%d] ==> %d\n", processId, i);
    int temp = rand() * rand(); // 실행 시간 지연을 통해 CPU 사용을 다른 프로세스에게 넘기도록 한다.
  }
  
  return 0;
}
