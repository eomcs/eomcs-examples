/**
 * this를 반환 타입으로 사용 (Using this as a Return Type)
 *
 * 메서드가 현재 인스턴스를 반환하면 메서드 체이닝 API를 만들 수 있다.
 * 반환 타입을 클래스 이름 대신 this로 쓰면 서브클래스에서도 구체 타입이 보존된다.
 */

class NumberCollection {
  protected values: number[] = []

  has(value: number): boolean {
    return this.values.includes(value)
  }

  add(value: number): this {
    if (!this.has(value)) {
      this.values.push(value)
    }

    return this
  }

  addMany(...values: number[]): this {
    values.forEach(value => this.add(value))

    return this
  }

  toArray(): number[] {
    return [...this.values]
  }
}

class MutableNumberCollection extends NumberCollection {
  delete(value: number): boolean {
    let index = this.values.indexOf(value)

    if (index === -1) {
      return false
    }

    this.values.splice(index, 1)
    return true
  }

  clear(): this {
    this.values = []

    return this
  }
}

let numbers = new NumberCollection()

numbers
  .add(1)
  .add(2)
  .addMany(2, 3, 4)

console.log(numbers.toArray()) // [1, 2, 3, 4]
console.log(numbers.has(3)) // true

let mutableNumbers = new MutableNumberCollection()

mutableNumbers
  .add(10)
  .add(20)
  .delete(10)

console.log(mutableNumbers.toArray()) // [20]

mutableNumbers
  .addMany(30, 40)
  .clear()
  .add(50)

console.log(mutableNumbers.toArray()) // [50]

// mutableNumbers.add('10')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.

// numbers.delete(1)
// Error: Property 'delete' does not exist on type 'NumberCollection'.

// this 반환 타입을 쓰면 부모 클래스의 add를 오버라이드하지 않아도
// MutableNumberCollection에서 add() 호출 결과가 MutableNumberCollection으로 유지된다.
let chainedMutableNumbers = new MutableNumberCollection()
  .add(1)
  .add(2)
  .clear()
  .add(3)

console.log(chainedMutableNumbers.toArray()) // [3]

// 클래스 이름을 반환 타입으로 쓰면 서브클래스 체이닝에서 타입 정보가 사라진다.
class NumberCollectionWithClassReturn {
  protected values: number[] = []

  add(value: number): NumberCollectionWithClassReturn {
    this.values.push(value)

    return this
  }
}

class MutableNumberCollectionWithClassReturn extends NumberCollectionWithClassReturn {
  clear(): this {
    this.values = []

    return this
  }
}

let classReturnCollection = new MutableNumberCollectionWithClassReturn()

classReturnCollection.clear().add(1)

// classReturnCollection.add(1).clear()
// Error: Property 'clear' does not exist on type 'NumberCollectionWithClassReturn'.

// this 반환 타입을 쓰면 같은 상황에서 서브클래스 메서드를 계속 호출할 수 있다.
new MutableNumberCollection()
  .add(1)
  .clear()
  .add(2)

// 메서드가 새 객체를 반환하는 불변 API에서도 this 반환 타입은 유용하다.
class TaggedBuilder {
  protected tags: string[] = []

  addTag(tag: string): this {
    this.tags.push(tag)

    return this
  }

  build() {
    return this.tags.join(', ')
  }
}

class NamedTaggedBuilder extends TaggedBuilder {
  private name = 'untitled'

  setName(name: string): this {
    this.name = name

    return this
  }

  build() {
    return `${this.name}: ${super.build()}`
  }
}

let namedTags = new NamedTaggedBuilder()
  .setName('features')
  .addTag('chainable')
  .addTag('typed')
  .build()

console.log(namedTags) // features: chainable, typed
