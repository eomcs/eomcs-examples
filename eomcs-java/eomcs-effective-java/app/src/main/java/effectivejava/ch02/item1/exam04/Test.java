// # 아이템 1. 생성자 대신 정적 팩터리 메서드를 고려하라
//
// ## 장점
// - 이름을 가질 수 있다.
// - 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.
// - 반환 타입의 하위 타입 객체를 반환할 수 있다.
// - 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
// - 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
//
// ## 단점
// - 상속을 하려면 public이나 protected 생성자가 필요하다.
// - 정적 팩터리 메서드만 제공하는 클래스는 확장할 수 없다.
// - 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.
// - API 문서에서 정적 팩터리 메서드를 잘 찾아봐야 한다.
// - 따라서 메서드 이름은 널리 알려진 규약을 따라 짓는 것이 좋다.
//   예) `from`, `valueOf`, `of`, `instance` | `getInstance`,
//       `create` | `newInstance`, `getType`, `newType`,  `type`
//       등의 이름을 사용한다.

package effectivejava.ch02.item1.exam04;

import java.util.EnumSet;

enum MovieCategory {
  ACTION,
  DRAMA,
  COMEDY,
  THRILLER,
  HORROR,
  ROMANCE,
  SCI_FI, // 과학소설
  FANTASY,
  ANIMATION,
  DOCUMENTARY,
  MUSICAL,
  ADVENTURE,
  CRIME,
  FAMILY,
  WAR,
  WESTERN
}

enum Color {
  BLACK(0, 0, 0),
  WHITE(255, 255, 255),
  RED(255, 0, 0),
  LIME(0, 255, 0),
  BLUE(0, 0, 255),
  YELLOW(255, 255, 0),
  CYAN(0, 255, 255),
  MAGENTA(255, 0, 255),
  SILVER(192, 192, 192),
  GRAY(128, 128, 128),
  MAROON(128, 0, 0),
  OLIVE(128, 128, 0),
  GREEN(0, 128, 0),
  PURPLE(128, 0, 128),
  TEAL(0, 128, 128),
  NAVY(0, 0, 128),
  ORANGE(255, 165, 0),
  ALICE_BLUE(240, 248, 255),
  ANTIQUE_WHITE(250, 235, 215),
  AQUAMARINE(127, 255, 212),
  AZURE(240, 255, 255),
  BEIGE(245, 245, 220),
  BISQUE(255, 228, 196),
  BLANCHED_ALMOND(255, 235, 205),
  BLUE_VIOLET(138, 43, 226),
  BROWN(165, 42, 42),
  BURLYWOOD(222, 184, 135),
  CADET_BLUE(95, 158, 160),
  CHARTREUSE(127, 255, 0),
  CHOCOLATE(210, 105, 30),
  CORAL(255, 127, 80),
  CORNFLOWER_BLUE(100, 149, 237),
  CORNSILK(255, 248, 220),
  CRIMSON(220, 20, 60),
  DARK_BLUE(0, 0, 139),
  DARK_CYAN(0, 139, 139),
  DARK_GOLDENROD(184, 134, 11),
  DARK_GRAY(169, 169, 169),
  DARK_GREEN(0, 100, 0),
  DARK_KHAKI(189, 183, 107),
  DARK_MAGENTA(139, 0, 139),
  DARK_OLIVE_GREEN(85, 107, 47),
  DARK_ORANGE(255, 140, 0),
  DARK_ORCHID(153, 50, 204),
  DARK_RED(139, 0, 0),
  DARK_SALMON(233, 150, 122),
  DARK_SEA_GREEN(143, 188, 143),
  DARK_SLATE_BLUE(72, 61, 139),
  DARK_SLATE_GRAY(47, 79, 79),
  DARK_TURQUOISE(0, 206, 209),
  DARK_VIOLET(148, 0, 211),
  DEEP_PINK(255, 20, 147),
  DEEP_SKY_BLUE(0, 191, 255),
  DIM_GRAY(105, 105, 105),
  DODGER_BLUE(30, 144, 255),
  FIREBRICK(178, 34, 34),
  FLORAL_WHITE(255, 250, 240),
  FOREST_GREEN(34, 139, 34),
  FUCHSIA(255, 0, 255),
  GAINSBORO(220, 220, 220),
  GHOST_WHITE(248, 248, 255),
  GOLD(255, 215, 0),
  GOLDENROD(218, 165, 32),
  GREEN_YELLOW(173, 255, 47),
  HONEYDEW(240, 255, 240);

  private final int r;
  private final int g;
  private final int b;

  Color(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public int getR() {
    return r;
  }

  public int getG() {
    return g;
  }

  public int getB() {
    return b;
  }
}

public class Test {
  public static void main(String[] args) {
    // 장점4: 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
    // - 파라미터 값에 따라 noneOf() 메서드가 반환하는 객체의 클래스가 다르다.
    // - API 사용자 입장에서는 반환된 객체의 클래스가 무엇인지 알 필요가 없다.
    // - 추후 API 설계자가 API를 변경하더라도 API 사용자에게 미치는 영향이 없다.

    // 64개 이하의 enum 상수를 담을 수 있는 EnumSet 객체 생성하기
    EnumSet<MovieCategory> movieCategories = EnumSet.noneOf(MovieCategory.class);
    movieCategories.add(MovieCategory.ACTION);
    movieCategories.add(MovieCategory.SCI_FI);
    movieCategories.add(MovieCategory.FANTASY);
    System.out.println(movieCategories);

    // 65개 이상의 enum 상수를 담을 수 있는 EnumSet 객체 생성하기
    EnumSet<Color> colors = EnumSet.noneOf(Color.class);
    colors.add(Color.RED);
    colors.add(Color.GREEN);
    colors.add(Color.BLUE);
    System.out.println(colors);

    // 리턴 객체 확인하기
    System.out.println(movieCategories.getClass()); // java.util.RegularEnumSet
    System.out.println(colors.getClass()); // java.util.JumboEnumSet
  }
}
