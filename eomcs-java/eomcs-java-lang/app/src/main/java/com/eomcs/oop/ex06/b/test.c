#include <stdio.h>

float eom_plusf(float a, float b) {
    return a + b;
}

int eom_plusi(int a, int b) {
    return a + b;
}

int main(void) {
    printf("Hello!\n");

    int r = eom_plusi(100, 200);
    printf("r = %d\n", r);

    float r2 = eom_plusf(100.5f, 200.2f);
    printf("r2 = %f\n", r2);
}
