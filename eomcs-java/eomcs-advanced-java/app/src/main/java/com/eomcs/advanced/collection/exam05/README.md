# Exam05 - Map 인터페이스와 HashMap, Hashtable, Properties, TreeMap, LinkedHashMap 클래스

## 개념

`Map<K,V>`는 키(key)와 값(value)의 쌍으로 항목을 관리하는 인터페이스이다.
`Collection` 계층과 독립된 별도 계층이며, 키는 중복될 수 없다.

### Map 구현체 비교

| 구현체 | 도입 | 순서 | 동기화 | `null` 키 | `null` 값 | 검색 성능 |
|---|---|---|---|---|---|---|
| `HashMap` | Java 1.2 | 미보장 | 없음 | 허용 | 허용 | O(1) |
| `Hashtable` | Java 1.0 | 미보장 | 전체 메서드 | 불허 | 불허 | O(1) |
| `LinkedHashMap` | Java 1.4 | 추가 순서 유지 (기본) / 접근 순서 (`accessOrder=true`) | 없음 | 허용 | 허용 | O(1) |
| `TreeMap` | Java 1.2 | 키 기준 정렬 | 없음 | 불허 | 허용 | O(log n) |
| `Properties` | Java 1.0 | 미보장 | 전체 메서드 | 불허 | 불허 | O(1) |

### Map 인터페이스 주요 메서드

| 메서드 | 설명 |
|---|---|
| `V put(K key, V value)` | 키-값 쌍 추가. 기존 키면 값 교체 후 이전 값 반환 |
| `V get(Object key)` | 키에 대응하는 값 반환. 없으면 `null` |
| `V getOrDefault(Object key, V d)` | 키가 없으면 기본값 `d` 반환 |
| `V putIfAbsent(K key, V value)` | 키가 없을 때만 추가. 기존 값 반환(없으면 `null`) |
| `V remove(Object key)` | 키-값 쌍 제거. 제거된 값 반환 |
| `boolean containsKey(Object key)` | 키 존재 여부 반환 |
| `boolean containsValue(Object value)` | 값 존재 여부 반환 |
| `int size()` | 항목 개수 반환 |
| `boolean isEmpty()` | 항목이 없으면 `true` 반환 |
| `void clear()` | 모든 항목 제거 |
| `Set<K> keySet()` | 모든 키를 `Set`으로 반환 |
| `Collection<V> values()` | 모든 값을 `Collection`으로 반환 |
| `Set<Map.Entry<K,V>> entrySet()` | 모든 키-값 쌍을 `Entry` Set으로 반환 |

### TreeMap 추가 메서드 (SortedMap / NavigableMap)

| 메서드 | 설명 |
|---|---|
| `K firstKey()` | 가장 작은 키 반환 |
| `K lastKey()` | 가장 큰 키 반환 |
| `Map.Entry<K,V> firstEntry()` | 가장 작은 키의 Entry 반환 |
| `Map.Entry<K,V> lastEntry()` | 가장 큰 키의 Entry 반환 |
| `Map.Entry<K,V> floorEntry(K key)` | `key` 이하의 가장 큰 키의 Entry 반환 |
| `Map.Entry<K,V> ceilingEntry(K key)` | `key` 이상의 가장 작은 키의 Entry 반환 |
| `SortedMap<K,V> headMap(K toKey)` | `toKey` 미만의 항목 반환 |
| `SortedMap<K,V> tailMap(K fromKey)` | `fromKey` 이상의 항목 반환 |
| `SortedMap<K,V> subMap(K from, K to)` | `from` 이상 `to` 미만의 항목 반환 |
| `NavigableMap<K,V> descendingMap()` | 역순으로 정렬된 Map 반환 |

### LinkedHashMap 동작 모드

`LinkedHashMap`은 `HashMap`을 상속하면서 내부적으로 이중 연결 리스트를 추가로 유지하여 항목 순서를 보장한다.

| 모드 | 생성 방법 | 동작 |
|---|---|---|
| 추가 순서 (기본) | `new LinkedHashMap<>()` | 항목을 추가한 순서를 유지. 중복 키로 `put()`하면 값만 교체되고 순서는 유지. `remove()` 후 재추가하면 맨 뒤로 이동 |
| 접근 순서 | `new LinkedHashMap<>(capacity, loadFactor, true)` | `get()` / `put()`으로 접근한 항목이 맨 뒤로 이동. 맨 앞 항목이 가장 오래 전에 접근된 항목 |

`removeEldestEntry()`를 재정의하면 항목 수가 일정 크기를 초과할 때 가장 오래된 항목을 자동으로 제거한다.
접근 순서 모드와 결합하면 LRU(Least Recently Used) 캐시를 간단히 구현할 수 있다.

```java
final int CAPACITY = 3;
LinkedHashMap<String, Integer> lruCache =
    new LinkedHashMap<>(CAPACITY, 0.75f, true) {
      @Override
      protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
        return size() > CAPACITY; // 최대 용량 초과 시 맨 앞(가장 오래된) 항목 자동 제거
      }
    };
```

### Properties 주요 메서드

| 메서드 | 설명 |
|---|---|
| `Object setProperty(String key, String value)` | 키-값 설정. 이전 값 반환 |
| `String getProperty(String key)` | 키에 대응하는 값 반환. 없으면 `null` |
| `String getProperty(String key, String d)` | 키가 없으면 기본값 `d` 반환 |
| `Set<String> stringPropertyNames()` | 모든 키를 `Set<String>`으로 반환 |
| `void store(OutputStream out, String comment)` | `.properties` 형식으로 저장 |
| `void load(InputStream in)` | `.properties` 파일 로드 |

## App - Map 인터페이스와 HashMap 사용

```java
Map<String, Integer> map = new HashMap<>();
map.put("apple", 1000);
map.put("banana", 500);
map.put("strawberry", 800);

map.get("apple");                  // 1000
map.get("grape");                  // null (없는 키)
map.getOrDefault("grape", 0);      // 0 (기본값)

// 중복 키 - 값 교체, 이전 값 반환
Integer old = map.put("apple", 1200); // old == 1000

// putIfAbsent - 키가 없을 때만 추가
map.putIfAbsent("apple", 9999); // 이미 있으므로 무시
map.putIfAbsent("mango", 700);  // 없으므로 추가

map.containsKey("banana");  // true
map.containsValue(500);     // true
map.remove("banana");       // 제거 후 제거된 값(500) 반환

// keySet(): 키만 순회
for (String key : map.keySet()) {
  System.out.println(key + " → " + map.get(key));
}

// values(): 값만 순회
for (int value : map.values()) {
  System.out.println(value);
}

// entrySet(): 키-값 쌍 순회 (가장 효율적)
for (Map.Entry<String, Integer> entry : map.entrySet()) {
  System.out.println(entry.getKey() + " → " + entry.getValue());
}

// null 키/값 모두 허용
map.put(null, 0);
map.put("empty", null);
```

- `HashMap`은 해시 테이블 기반으로 O(1) 검색을 제공하지만 항목 순서를 보장하지 않는다.
- 키의 `hashCode()`와 `equals()`를 이용해 저장·검색하므로, 커스텀 객체를 키로 사용할 때는 두 메서드를 반드시 재정의해야 한다.
- `keySet()`으로 순회할 때 `get(key)`를 추가 호출하면 조회가 두 번 발생한다. `entrySet()`을 사용하면 한 번에 꺼낼 수 있어 더 효율적이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam05.App
  ```

## App2 - Hashtable 사용

```java
Hashtable<String, Integer> table = new Hashtable<>();
table.put("apple", 1000);
table.put("banana", 500);

// null 키/값 모두 불허
table.put(null, 0);       // NullPointerException
table.put("key", null);   // NullPointerException

// 레거시 메서드 (사용 비권장)
table.contains(500);              // containsValue()와 동일
table.keys().nextElement();       // keySet()과 유사
table.elements().nextElement();   // values()와 유사

// Map 타입으로 선언하면 HashMap과 교체 가능
Map<String, Integer> hashMap   = new HashMap<>();
Map<String, Integer> hashTable = new Hashtable<>();
hashMap.put(null, 0);    // HashMap: 허용
hashTable.put(null, 0);  // Hashtable: NullPointerException
```

- `Hashtable`은 모든 메서드가 `synchronized`되어 있어 멀티스레드 환경에서 안전하지만, 단일 스레드에서는 동기화 비용으로 `HashMap`보다 느리다.
- `contains()` / `keys()` / `elements()` 등의 레거시 메서드는 `containsValue()` / `keySet()` / `values()`와 동일하므로 사용하지 않는다.
- 멀티스레드 환경에서는 `Hashtable` 대신 `ConcurrentHashMap`을 사용한다. `ConcurrentHashMap`은 전체를 잠그지 않고 버킷 단위로 잠그므로 더 효율적이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam05.App2
  ```

## App3 - Properties 사용

```java
Properties props = new Properties();
props.setProperty("db.host", "localhost");
props.setProperty("db.port", "3306");
props.setProperty("db.name", "mydb");

props.getProperty("db.host");           // "localhost"
props.getProperty("db.timeout", "30"); // 없는 키 → 기본값 "30"

// 모든 키 순회
for (String key : props.stringPropertyNames()) {
  System.out.println(key + " = " + props.getProperty(key));
}

// .properties 파일로 저장
try (FileOutputStream fos = new FileOutputStream("db.properties")) {
  props.store(fos, "Database Configuration"); // 두 번째 인수는 주석
}

// .properties 파일에서 로드
Properties loaded = new Properties();
try (FileInputStream fis = new FileInputStream("db.properties")) {
  loaded.load(fis);
}

// 기본값 Properties 상속
Properties defaults = new Properties();
defaults.setProperty("timeout", "30");
defaults.setProperty("encoding", "UTF-8");

Properties config = new Properties(defaults); // defaults를 기본값으로 사용
config.setProperty("timeout", "60");          // defaults 값 재정의
config.getProperty("timeout");   // "60" (재정의된 값)
config.getProperty("encoding");  // "UTF-8" (defaults에서 상속)

// 시스템 프로퍼티
Properties sysProps = System.getProperties();
sysProps.getProperty("os.name");       // "Mac OS X"
sysProps.getProperty("java.version");  // "21.0.8"
sysProps.getProperty("user.home");     // 사용자 홈 디렉터리
```

- `Properties`는 `Hashtable`을 상속하므로 키와 값으로 `null`을 허용하지 않는다.
- `setProperty` / `getProperty`는 `String` 타입만 다루므로, `put` / `get`(Object 타입) 대신 이 메서드를 사용한다.
- 생성자에 다른 `Properties`를 전달하면 기본값 체계를 구성할 수 있다. 키를 찾지 못하면 기본값 `Properties`에서 재검색한다.
- `store()`로 저장된 파일에는 `#` 주석과 타임스탬프가 자동으로 추가된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam05.App3
  ```

## App4 - TreeMap 사용

```java
TreeMap<String, Integer> map = new TreeMap<>();
map.put("banana", 500);
map.put("apple", 1000);
map.put("mango", 700);
map.put("strawberry", 800);
map.put("cherry", 300);
// 키 알파벳 오름차순: {apple=1000, banana=500, cherry=300, mango=700, strawberry=800}

map.firstKey();   // "apple"
map.lastKey();    // "strawberry"
map.firstEntry(); // apple=1000
map.lastEntry();  // strawberry=800

// floorEntry(key): key 이하의 가장 큰 키의 Entry
map.floorEntry("c");  // banana=500 ("c" 이하: apple, banana → 최대 banana)
map.floorEntry("b");  // apple=1000 ("b" 이하: apple → 최대 apple)

// ceilingEntry(key): key 이상의 가장 작은 키의 Entry
map.ceilingEntry("c"); // cherry=300 ("c" 이상: cherry, mango, ... → 최소 cherry)
map.ceilingEntry("d"); // mango=700  ("d" 이상: mango, strawberry → 최소 mango)

// 범위 뷰 (원본의 뷰이므로 원본 수정 시 함께 반영)
map.headMap("mango");          // mango 미만:  {apple, banana, cherry}
map.tailMap("mango");          // mango 이상:  {mango, strawberry}
map.subMap("cherry", "mango"); // cherry 이상 mango 미만: {cherry}

map.descendingMap(); // 역순: {strawberry=800, mango=700, cherry=300, banana=500, apple=1000}

// 정수 키 - 자연 순서(오름차순)
TreeMap<Integer, String> scoreMap = new TreeMap<>();
scoreMap.put(85, "홍길동");
scoreMap.put(92, "임꺽정");
scoreMap.put(78, "유관순");
scoreMap.put(95, "이순신");
// {78=유관순, 85=홍길동, 92=임꺽정, 95=이순신}
scoreMap.firstEntry(); // 78=유관순 (최저점)
scoreMap.lastEntry();  // 95=이순신 (최고점)

// null 키 불허
map.put(null, 0); // NullPointerException
```

- `TreeMap`은 레드-블랙 트리(Red-Black Tree) 기반으로, 항목을 추가할 때마다 키를 자연 순서로 정렬하여 저장한다.
- 검색·삽입·삭제 모두 O(log n)이므로 `HashMap`(O(1))보다 느리다. 정렬된 Map이 필요할 때만 사용한다.
- `floorEntry(key)`는 "key 이하의 최대 키", `ceilingEntry(key)`는 "key 이상의 최소 키"를 반환한다. 문자열 비교는 사전순(lexicographic)이므로 `"b" < "banana"`, `"c" < "cherry"`임에 주의한다.
- `headMap` / `tailMap` / `subMap`이 반환하는 Map은 원본의 뷰(view)이다. 원본을 수정하면 뷰에도 반영된다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam05.App4
  ```

## App5 - LinkedHashMap 사용

```java
// 1. 추가 순서 유지 (기본 동작)
Map<String, Integer> linked = new LinkedHashMap<>();
linked.put("banana", 500);
linked.put("apple", 1000);
linked.put("mango", 700);
linked.put("strawberry", 800);
System.out.println(linked); // {banana=500, apple=1000, mango=700, strawberry=800} - 추가 순서 유지

// 중복 키 - 값만 교체, 순서 유지
linked.put("apple", 1200); // apple은 여전히 두 번째 위치
// remove 후 재추가 - 맨 뒤로 이동
linked.remove("apple");
linked.put("apple", 1000); // apple이 맨 뒤로 이동

// 2. 접근 순서 모드 (accessOrder = true)
// 생성자: LinkedHashMap(initialCapacity, loadFactor, accessOrder)
LinkedHashMap<String, Integer> accessMap =
    new LinkedHashMap<>(16, 0.75f, true);
accessMap.put("A", 1);
accessMap.put("B", 2);
accessMap.put("C", 3);
accessMap.put("D", 4);
// 초기 상태: {A=1, B=2, C=3, D=4}

accessMap.get("B");     // B 접근 → 맨 뒤로 이동: {A, C, D, B}
accessMap.get("A");     // A 접근 → 맨 뒤로 이동: {C, D, B, A}
accessMap.put("C", 30); // C 접근 → 맨 뒤로 이동: {D, B, A, C}

// 3. LRU 캐시 구현 - accessOrder + removeEldestEntry() 재정의
final int CAPACITY = 3;
LinkedHashMap<String, Integer> lruCache =
    new LinkedHashMap<>(CAPACITY, 0.75f, true) {
      @Override
      protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
        return size() > CAPACITY; // 최대 용량 초과 시 가장 오래된 항목 자동 제거
      }
    };

lruCache.put("page1", 1);
lruCache.put("page2", 2);
lruCache.put("page3", 3);
// {page1, page2, page3}

lruCache.get("page1");    // page1 최근 접근 → 맨 뒤로: {page2, page3, page1}
lruCache.put("page4", 4); // 용량 초과 → 가장 오래된 page2 제거: {page3, page1, page4}
lruCache.put("page5", 5); // 용량 초과 → 가장 오래된 page3 제거: {page1, page4, page5}
```

- `LinkedHashMap`은 `HashMap`을 상속하며, 내부적으로 이중 연결 리스트를 추가로 유지하여 순서를 보장한다.
- 기본 모드(추가 순서)에서 중복 키로 `put()`하면 값만 교체되고 순서는 유지된다. `remove()` 후 재추가하면 맨 뒤로 이동한다.
- 접근 순서 모드(`accessOrder=true`)에서는 `get()` / `put()`으로 접근한 항목이 맨 뒤로 이동한다. 맨 앞 항목이 가장 오래 전에 접근한 항목이다.
- `removeEldestEntry()`를 재정의하면 항목 수가 일정 크기를 초과할 때 자동으로 가장 오래된 항목을 제거할 수 있다. 접근 순서 모드와 결합하면 LRU(Least Recently Used) 캐시를 구현할 수 있다.
- `HashMap`보다 메모리를 약간 더 사용하지만, 검색·삽입·삭제 성능은 동일하게 O(1)이다.
- 실행 명령:
  ```
  ./gradlew -q run -PmainClass=com.eomcs.advanced.collection.exam05.App5
  ```
