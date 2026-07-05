/**
 * 제네릭 클래스 (Polymorphism)
 *
 * 클래스와 인터페이스에서도 제네릭 타입 파라미터를 사용할 수 있다.
 * 제네릭 클래스는 인스턴스 전체에서 같은 타입 관계를 유지할 때 유용하다.
 */

class GenericPairMap<K, V> {
  private entries: Array<[K, V]> = []

  constructor(initialKey: K, initialValue: V) {
    this.set(initialKey, initialValue)
  }

  get(key: K): V | undefined {
    let entry = this.entries.find(([entryKey]) => Object.is(entryKey, key))

    return entry?.[1]
  }

  set(key: K, value: V): void {
    let entry = this.entries.find(([entryKey]) => Object.is(entryKey, key))

    if (entry) {
      entry[1] = value
      return
    }

    this.entries.push([key, value])
  }

  has(key: K): boolean {
    return this.entries.some(([entryKey]) => Object.is(entryKey, key))
  }

  toArray(): Array<[K, V]> {
    return [...this.entries]
  }

  // 인스턴스 메서드는 클래스 제네릭 K, V와 자체 제네릭 K1, V1을 모두 사용할 수 있다.
  merge<K1, V1>(map: GenericPairMap<K1, V1>): GenericPairMap<K | K1, V | V1> {
    let [firstEntry] = this.entries
    let mergedMap = new GenericPairMap<K | K1, V | V1>(firstEntry[0], firstEntry[1])

    for (let [key, value] of this.entries.slice(1)) {
      mergedMap.set(key, value)
    }

    for (let [key, value] of map.toArray()) {
      mergedMap.set(key, value)
    }

    return mergedMap
  }

  // 정적 메서드는 클래스 레벨의 K, V에 접근할 수 없다.
  // 따라서 static 메서드 자체에 K, V를 새로 선언해야 한다.
  static of<K, V>(key: K, value: V): GenericPairMap<K, V> {
    return new GenericPairMap(key, value)
  }
}

// 1. 명시적 바인딩
let explicitMap = new GenericPairMap<string, number>('a', 1)

explicitMap.set('b', 2)

console.log(explicitMap.get('a')) // 1
console.log(explicitMap.get('b')) // 2

// explicitMap.set('c', '3')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// explicitMap.get(1)
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.

// 2. 생성자 인수에서 타입 추론
let inferredMap = new GenericPairMap('enabled', true)

inferredMap.set('visible', false)

console.log(inferredMap.get('enabled')) // true
console.log(inferredMap.get('visible')) // false

// inferredMap.set('count', 1)
// Error: Argument of type 'number' is not assignable to parameter of type 'boolean'.

// 3. 인스턴스 메서드 자체의 제네릭
let numberToStringMap = new GenericPairMap<number, string>(1, 'one')
let stringToBooleanMap = new GenericPairMap<string, boolean>('two', true)
let mergedMap = numberToStringMap.merge(stringToBooleanMap)

console.log(mergedMap.get(1)) // one
console.log(mergedMap.get('two')) // true

// mergedMap의 키 타입은 number | string이고, 값 타입은 string | boolean이다.
let mergedValue = mergedMap.get('two')

if (typeof mergedValue === 'boolean') {
  console.log(mergedValue.valueOf()) // true
}

// mergedMap.get(true)
// Error: Argument of type 'boolean' is not assignable to parameter of type 'string | number'.

// 4. 정적 메서드는 자체 제네릭을 선언해야 한다.
let staticCreatedMap = GenericPairMap.of('port', 3000)

console.log(staticCreatedMap.get('port')) // 3000

// staticCreatedMap.set('host', 'localhost')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// class InvalidStaticGenericMap<K, V> {
//   static of(key: K, value: V) {
//     return [key, value]
//   }
// }
// Error: Static members cannot reference class type parameters.

// 5. 인터페이스에도 제네릭 타입 파라미터를 사용할 수 있다.
interface Repository<Entity, Id> {
  get(id: Id): Entity | undefined
  save(id: Id, entity: Entity): void
}

type GenericUserRecord = {
  id: string
  name: string
}

class MemoryRepository<Entity, Id> implements Repository<Entity, Id> {
  private items = new GenericPairMap<Id, Entity | undefined>(
    undefined as Id,
    undefined
  )

  get(id: Id): Entity | undefined {
    return this.items.get(id)
  }

  save(id: Id, entity: Entity): void {
    this.items.set(id, entity)
  }
}

let userRepository = new MemoryRepository<GenericUserRecord, string>()

userRepository.save('u-1', { id: 'u-1', name: 'Ada' })

console.log(userRepository.get('u-1')?.name) // Ada

// userRepository.save(1, { id: 'u-1', name: 'Ada' })
// Error: Argument of type 'number' is not assignable to parameter of type 'string'.

// userRepository.save('u-2', { id: 'u-2' })
// Error: Property 'name' is missing.
