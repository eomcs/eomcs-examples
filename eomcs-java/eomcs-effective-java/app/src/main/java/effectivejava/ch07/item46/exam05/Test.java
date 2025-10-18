// # 아이템 46. 스트림에서는 부작용 없는 함수를 사용하라
// - 스트림은 그저 또 하나의 API가 아닌, 함수형 프로그래밍에 기초한 패러다임(paradigm)이다.
// - 스트림이 제공하는 표현력, 속도, 상황에 따라서는 병령성까지 끌어내려면
//   API는 말할 것도 없고 이 패러다임까지 함께 받아들여야 한다.
// [스트림 패러다임]
// - 계산을 일련의 변환으로 재구성하는 것.
//   각 변환 단계는 가능한 한 "이전 단계의 결과를 받아 처리하는 순수 함수"여야 한다.
// - "순수 함수"란 오직 입력만이 결과에 영향을 주는 함수를 말한다.
//   다른 가변 상태를 참조하지 않고, 함수 스스로도 다른 상태를 변경하지 않는다.
// - 이렇게 하려면 중간 단계든 종단 단계든 스트림 연산에 건네는 함수 객체는 모두 부작용이 없어야 한다.
//
package effectivejava.ch07.item46.exam05;

// [주제] 맵 수집기 사용 예 - 복잡한 toMap() 사용 예

import static java.util.function.BinaryOperator.maxBy;
import static java.util.stream.Collectors.toMap;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

class Album {
  private final String name;
  private final String artist;
  private final int releaseYear;
  private final int sales;

  public Album(String name, String artist, int releaseYear, int sales) {
    this.name = name;
    this.artist = artist;
    this.releaseYear = releaseYear;
    this.sales = sales;
  }

  public String name() {
    return name;
  }

  public String artist() {
    return artist;
  }

  public int releaseYear() {
    return releaseYear;
  }

  public int sales() {
    return sales;
  }

  @Override
  public String toString() {
    return String.format("%s by %s (%d) - %d sales", name, artist, releaseYear, sales);
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    // 아티스트별로 가장 많이 팔린 앨범을 맵으로 만들기
    Stream<Album> albums =
        Stream.of(
            new Album("Album A", "Artist 1", 2000, 700000),
            new Album("Album B", "Artist 2", 2001, 300000),
            new Album("Album C", "Artist 1", 2002, 500000),
            new Album("Album D", "Artist 3", 2003, 400000),
            new Album("Album E", "Artist 2", 2004, 600000));

    // 기존 toMap()의 한계
    // - 같은 key(아티스트 이름)를 가진 앨범이 여러 개 있을 때 예외 발생
    //    Map<String, Album> topAlbumsByArtist = albums.collect(
    //      toMap(Album::artist,
    //            album -> album));

    // toMap(keyMapper, valueMapper, mergeFunction):
    // - 스트림 원소 다수가 같은 key를 사용할 때 유용하다.
    // - 같은 key를 갖는 원소를 병합할 때 사용할 함수를 제공할 수 있다.
    //   즉 동일한 키가 여러 번 생성되면 mergeFunction을 사용해 값을 병합한다.
    Map<String, Album> topAlbumsByArtist =
        albums.collect(
            toMap(Album::artist, album -> album, maxBy(Comparator.comparing(Album::sales))));

    // 결과 출력
    topAlbumsByArtist.forEach(
        (artist, album) -> {
          System.out.println(artist + ": " + album);
        });
  }
}
