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

package effectivejava.ch02.item1;

import java.util.EnumSet;

public class Exam04 {
  public static void main(String[] args) {
    // 장점4: 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.

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

    // - 파라미터 값에 따라 noneOf() 메서드가 반환하는 객체의 클래스가 다르다.
    // - API 사용자 입장에서는 반환된 객체의 클래스가 무엇인지 알 필요가 없다.
    // - 추후 API 설계자가 API를 변경하더라도 API 사용자에게 미치는 영향이 없다.

  }

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
    BLACK(0, 0, 0, "#000000"),
    WHITE(255, 255, 255, "#FFFFFF"),
    RED(255, 0, 0, "#FF0000"),
    LIME(0, 255, 0, "#00FF00"),
    BLUE(0, 0, 255, "#0000FF"),
    YELLOW(255, 255, 0, "#FFFF00"),
    CYAN(0, 255, 255, "#00FFFF"),
    MAGENTA(255, 0, 255, "#FF00FF"),
    SILVER(192, 192, 192, "#C0C0C0"),
    GRAY(128, 128, 128, "#808080"),
    MAROON(128, 0, 0, "#800000"),
    OLIVE(128, 128, 0, "#808000"),
    GREEN(0, 128, 0, "#008000"),
    PURPLE(128, 0, 128, "#800080"),
    TEAL(0, 128, 128, "#008080"),
    NAVY(0, 0, 128, "#000080"),
    ORANGE(255, 165, 0, "#FFA500"),
    ALICE_BLUE(240, 248, 255, "#F0F8FF"),
    ANTIQUE_WHITE(250, 235, 215, "#FAEBD7"),
    AQUAMARINE(127, 255, 212, "#7FFFD4"),
    AZURE(240, 255, 255, "#F0FFFF"),
    BEIGE(245, 245, 220, "#F5F5DC"),
    BISQUE(255, 228, 196, "#FFE4C4"),
    BLANCHED_ALMOND(255, 235, 205, "#FFEBCD"),
    BLUE_VIOLET(138, 43, 226, "#8A2BE2"),
    BROWN(165, 42, 42, "#A52A2A"),
    BURLYWOOD(222, 184, 135, "#DEB887"),
    CADET_BLUE(95, 158, 160, "#5F9EA0"),
    CHARTREUSE(127, 255, 0, "#7FFF00"),
    CHOCOLATE(210, 105, 30, "#D2691E"),
    CORAL(255, 127, 80, "#FF7F50"),
    CORNFLOWER_BLUE(100, 149, 237, "#6495ED"),
    CORNSILK(255, 248, 220, "#FFF8DC"),
    CRIMSON(220, 20, 60, "#DC143C"),
    DARK_BLUE(0, 0, 139, "#00008B"),
    DARK_CYAN(0, 139, 139, "#008B8B"),
    DARK_GOLDENROD(184, 134, 11, "#B8860B"),
    DARK_GRAY(169, 169, 169, "#A9A9A9"),
    DARK_GREEN(0, 100, 0, "#006400"),
    DARK_KHAKI(189, 183, 107, "#BDB76B"),
    DARK_MAGENTA(139, 0, 139, "#8B008B"),
    DARK_OLIVE_GREEN(85, 107, 47, "#556B2F"),
    DARK_ORANGE(255, 140, 0, "#FF8C00"),
    DARK_ORCHID(153, 50, 204, "#9932CC"),
    DARK_RED(139, 0, 0, "#8B0000"),
    DARK_SALMON(233, 150, 122, "#E9967A"),
    DARK_SEA_GREEN(143, 188, 143, "#8FBC8F"),
    DARK_SLATE_BLUE(72, 61, 139, "#483D8B"),
    DARK_SLATE_GRAY(47, 79, 79, "#2F4F4F"),
    DARK_TURQUOISE(0, 206, 209, "#00CED1"),
    DARK_VIOLET(148, 0, 211, "#9400D3"),
    DEEP_PINK(255, 20, 147, "#FF1493"),
    DEEP_SKY_BLUE(0, 191, 255, "#00BFFF"),
    DIM_GRAY(105, 105, 105, "#696969"),
    DODGER_BLUE(30, 144, 255, "#1E90FF"),
    FIREBRICK(178, 34, 34, "#B22222"),
    FLORAL_WHITE(255, 250, 240, "#FFFAF0"),
    FOREST_GREEN(34, 139, 34, "#228B22"),
    FUCHSIA(255, 0, 255, "#FF00FF"),
    GAINSBORO(220, 220, 220, "#DCDCDC"),
    GHOST_WHITE(248, 248, 255, "#F8F8FF"),
    GOLD(255, 215, 0, "#FFD700"),
    GOLDENROD(218, 165, 32, "#DAA520"),
    GREEN_YELLOW(173, 255, 47, "#ADFF2F"),
    HONEYDEW(240, 255, 240, "#F0FFF0");

    private final int r;
    private final int g;
    private final int b;
    private final String hex;

    Color(int r, int g, int b, String hex) {
      this.r = r;
      this.g = g;
      this.b = b;
      this.hex = hex;
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

    public String getHex() {
      return hex;
    }
  }
}
