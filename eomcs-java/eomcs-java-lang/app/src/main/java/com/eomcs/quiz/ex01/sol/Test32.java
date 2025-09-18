package com.eomcs.quiz.ex01.sol;
// copyright by codefights.com
// 
// 테니스 경기에서 세트의 종료 조건은 
// 한 선수가 6게임을 이기고 다른 선수가 5게임 미만 일 때 종료한다.
// 만약 5대 5 듀스의 상황이라면 한 선수가 7게임을 이길 때 종료한다. 
// 양 선수의 이긴 게임 수를 보고 종료 조건인지 검사하라!
//
// 형식:
//   tennisSet(이긴게임수1, 이긴게임수2)
// 예) 
//   tennisSet(3, 6) ==> true
//   tennisSet(8, 5) ==> false
//   tennisSet(6, 5) ==> false
/*
In tennis, set is finished when one of the players wins 6 games and 
the other one less than 5 or when one of the players wins 7 games 
(in that case, the second one has less wins, no matter how many).

Determine if a given score is possible for an ended tennis set.

Example

for score1 = 3, score2 = 6 output should be true,
for score1 = 8, score2 = 5 or score1 = 6, score2 = 5 output should be false

[input] integer score1
number of games won by the 1st player, non-negative integer

[input] integer score2
number of games won by the 2nd player, non-negative integer

[output] boolean
true if score1 : score2 represents a possible score for an ended set, false otherwise
 */
//
// [시간 복잡도]
// - O(1): 시간 복잡도는 일정하다.
//
public class Test32 {

  public static void main(String[] args) {
    System.out.println(tennisSet(3, 6) == true);
    System.out.println(tennisSet(8, 5) == false);
    System.out.println(tennisSet(6, 5) == false);
  }

  static boolean tennisSet(int score1, int score2) {
    if (score1 < score2) {
      int tmp = score1;
      score1 = score2;
      score2 = tmp;
    }
    if (score1 == 6 && score2 < 5 || score1 == 7 && score2 < 7) {
      return true;
    }
    return false;
  }
}
