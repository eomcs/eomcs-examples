package com.eomcs.cleancode.ch04.exam04;

public class BadAndGood19 {

  // Bad: 통합 예제 — 나쁜 주석이 가득한 소수 생성기
  //
  // - 클래스 Javadoc에 에라토스테네스의 생애까지 담긴 과도한 역사 설명이 있다.
  // - @author, @version 같은 메타 정보는 VCS가 관리해야 한다.
  // - s, f, i, j 같은 의미 없는 변수명이 주석(// size of array, // if i is uncrossed)에 의존한다.
  // - // declarations, // sieve 같은 구간 구분 주석이 나쁜 구조를 덮고 있다.
  // - "return null array if bad input." 같은 오해 소지 있는 주석도 포함된다.
  //
  // 첫 번째로 나오는 좋은 주석: 알고리즘 동작을 간결하게 설명하는 클래스 Javadoc.
  // 두 번째로 나오는 좋은 주석: 루프 한계값으로 제곱근을 사용한 수학적 이유 설명.
  static class GeneratePrimes {
    /**
     * @param maxValue is the generation limit.
     */
    public static int[] generatePrimes(int maxValue) {
      if (maxValue >= 2) {
        // declarations
        int s = maxValue + 1; // size of array
        boolean[] f = new boolean[s];

        // initialize array to true.
        for (int i = 0; i < s; i++) {
          f[i] = true;
        }

        // get rid of known non-primes
        f[0] = f[1] = false;

        // sieve
        for (int i = 2; i < Math.sqrt(s) + 1; i++) {
          if (f[i]) { // if i is uncrossed, cross its multiples.
            for (int j = 2 * i; j < s; j += i) {
              f[j] = false; // multiple is not prime
            }
          }
        }

        // how many primes are there?
        int count = 0;
        for (int i = 0; i < s; i++) {
          if (f[i]) {
            count++; // bump count.
          }
        }

        int[] primes = new int[count];

        // move the primes into the result
        for (int i = 0, j = 0; i < s; i++) {
          if (f[i]) { // if prime
            primes[j++] = i;
          }
        }

        return primes; // return the primes
      } else {
        return new int[0]; // return null array if bad input.
      }
    }
  }

  // Good: 통합 예제 — 리팩토링 후 소수 생성기
  //
  // - 클래스 Javadoc은 알고리즘 동작만 간결하게 설명한다 (좋은 주석 ①).
  // - 메서드를 작게 나눠 이름이 설명 역할을 대신한다.
  // - crossedOut, result 같은 의미 있는 변수명으로 주석이 필요 없어진다.
  // - determineIterationLimit()의 제곱근 사용 이유는 수학적 불변식이라
  //   코드만으로 표현하기 어려우므로 주석으로 의도를 설명한다 (좋은 주석 ②).
  /**
   * This class generates prime numbers up to a user-specified maximum.
   * The algorithm used is the Sieve of Eratosthenes:
   * Given an array of integers starting at 2, find the first uncrossed
   * integer and cross out all its multiples. Repeat until there are no
   * more multiples in the array.
   */
  static class PrimeGenerator {
    private static boolean[] crossedOut;
    private static int[] result;

    public static int[] generatePrimes(int maxValue) {
      if (maxValue < 2) {
        return new int[0];
      }
      uncrossIntegersUpTo(maxValue);
      crossOutMultiples();
      putUncrossedIntegersIntoResult();
      return result;
    }

    private static void uncrossIntegersUpTo(int maxValue) {
      crossedOut = new boolean[maxValue + 1];
      for (int i = 2; i < crossedOut.length; i++) {
        crossedOut[i] = false;
      }
    }

    private static void crossOutMultiples() {
      int limit = determineIterationLimit();
      for (int i = 2; i <= limit; i++) {
        if (notCrossed(i)) {
          crossOutMultiplesOf(i);
        }
      }
    }

    private static int determineIterationLimit() {
      // 배열 안의 모든 합성수는 배열 크기의 제곱근보다 작거나 같은 소인수를 갖는다.
      // 따라서 그 제곱근보다 큰 수의 배수는 이미 걸러졌으므로 순회할 필요가 없다.
      double iterationLimit = Math.sqrt(crossedOut.length);
      return (int) iterationLimit;
    }

    private static void crossOutMultiplesOf(int i) {
      for (int multiple = 2 * i; multiple < crossedOut.length; multiple += i) {
        crossedOut[multiple] = true;
      }
    }

    private static boolean notCrossed(int i) {
      return !crossedOut[i];
    }

    private static void putUncrossedIntegersIntoResult() {
      result = new int[numberOfUncrossedIntegers()];
      for (int j = 0, i = 2; i < crossedOut.length; i++) {
        if (notCrossed(i)) {
          result[j++] = i;
        }
      }
    }

    private static int numberOfUncrossedIntegers() {
      int count = 0;
      for (int i = 2; i < crossedOut.length; i++) {
        if (notCrossed(i)) {
          count++;
        }
      }
      return count;
    }
  }
}
