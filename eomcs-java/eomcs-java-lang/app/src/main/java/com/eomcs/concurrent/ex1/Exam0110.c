#include <stdio.h>

int main() {
  int i = 0;
  for (i = 0; i < 100; i++) {
    printf("----> %d\n", i);
  }
  
  int j = 0;
  for (j = 0; j < 100; j++) {
    printf("=> %d\n", j);
  }
  return 0;
}