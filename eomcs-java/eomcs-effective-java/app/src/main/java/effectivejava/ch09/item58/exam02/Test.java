// # 아이템 58. 전통적인 for문 보다는 for-each 문을 사용하라
// - 스트림이 제격인 작업이 있고 반복이 제격인 작업이 있다.
// - 전통적인 for 문과 비교했을 때 for-each 문은 명료하고, 유연하고, 버그를 예방해준다.
//   성능 저하도 없다.
//   가능한 모든 곳에서 for문이 아닌 for-each 문을 사용하라.
//
package effectivejava.ch09.item58.exam02;

// [주제] 중첩 for vs 중첩 for-each 문

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

enum Suit {
  CLUB,
  DIAMOND,
  HEART,
  SPADE
}

enum Rank {
  ACE,
  DEUCE,
  THREE,
  FOUR,
  FIVE,
  SIX,
  SEVEN,
  EIGHT,
  NINE,
  TEN,
  JACK,
  QUEEN,
  KING
}

class Card {
  private final Suit suit;
  private final Rank rank;

  public Card(Suit suit, Rank rank) {
    this.suit = suit;
    this.rank = rank;
  }

  @Override
  public String toString() {
    return rank + " of " + suit;
  }
}

public class Test {
  public static void main(String[] args) {
    Collection<Suit> suits = Arrays.asList(Suit.values());
    Collection<Rank> ranks = Arrays.asList(Rank.values());

    // 전통적인 for 문 사용 예:
    try {
      List<Card> deck = new java.util.ArrayList<>();
      for (Iterator<Suit> i = suits.iterator(); i.hasNext(); ) {
        for (Iterator<Rank> j = ranks.iterator(); j.hasNext(); ) {
          // Suit 열거 상수를 꺼내기 위해 다음 방식으로 i.next() 호출하면
          // 안쪽 Rank를 반복하는 동안 Suit를 계속 순회하기 때문에
          // 열거 상수를 다 꺼내버려 예외가 발생한다.
          // 물론 바깥 반복문에서 Suit 열거 상수를 미리 꺼낸 후 그 상수를 사용하면 된다.
          //
          deck.add(new Card(i.next(), j.next())); // NoSuchElementException 예외 발생!
        }
      }
      System.out.println(deck);
    } catch (NoSuchElementException e) {
      System.out.println("예외 발생: " + e);
    }

    // 전통적인 for 문 사용 개선 예:
    List<Card> deck2 = new java.util.ArrayList<>();
    for (Iterator<Suit> i = suits.iterator(); i.hasNext(); ) {
      Suit suit = i.next(); // 바깥 반복문에서 미리 꺼내기
      for (Iterator<Rank> j = ranks.iterator(); j.hasNext(); ) {
        // 바깥 반복문에서 suit 열거 상수를 꺼냈기 때문에
        // 안쪽 반복문을 반복하더라도 문제가 없다.
        deck2.add(new Card(suit, j.next()));
      }
    }
    System.out.println(deck2);

    // for-each 문 사용 예:
    // - 이전의 for 문을 사용할 때보다 코드가 더 간결하고 명료하다.
    List<Card> deck3 = new java.util.ArrayList<>();
    for (Suit suit : suits) {
      for (Rank rank : ranks) {
        deck3.add(new Card(suit, rank));
      }
    }
    System.out.println(deck2);
  }
}
