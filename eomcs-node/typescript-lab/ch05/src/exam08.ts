/**
 * Mixin 패턴
 *
 * TypeScript에는 mixin 키워드가 없지만, 클래스를 받아 새 클래스를 반환하는 함수로
 * 믹스인을 구현할 수 있다. 상속보다 "할 수 있는 일"을 조합할 때 유용하다.
 */

type MixinClassConstructor<T> = new (...args: any[]) => T

// 1. getDebugValue()를 가진 클래스에 debug() 메서드를 추가하는 믹스인
function withEZDebug<C extends MixinClassConstructor<{
  getDebugValue(): object
}>>(Class: C) {
  return class extends Class {
    debug() {
      let name = Class.name
      let value = this.getDebugValue()

      return `${name}(${JSON.stringify(value)})`
    }
  }
}

class HardToDebugUserForMixin {
  constructor(
    private id: number,
    private firstName: string,
    private lastName: string
  ) {}

  getDebugValue() {
    return {
      id: this.id,
      name: `${this.firstName} ${this.lastName}`,
    }
  }
}

let DebuggableUser = withEZDebug(HardToDebugUserForMixin)
let debuggableUser = new DebuggableUser(3, 'Emma', 'Gluzman')

console.log(debuggableUser.debug())
// HardToDebugUserForMixin({"id":3,"name":"Emma Gluzman"})

// withEZDebug(class UserWithoutDebugValue {})
// Error: Property 'getDebugValue' is missing.

// 2. 믹스인은 상태(인스턴스 프로퍼티)를 가질 수 있다.
function withActivatable<C extends MixinClassConstructor<object>>(Class: C) {
  return class extends Class {
    private active = false

    activate() {
      this.active = true
      return this
    }

    deactivate() {
      this.active = false
      return this
    }

    isActive() {
      return this.active
    }
  }
}

class MixinProject {
  constructor(public name: string) {}

  rename(name: string) {
    this.name = name
    return this
  }
}

let ActivatableProject = withActivatable(MixinProject)
let project = new ActivatableProject('TypeScript Lab')

project.activate().rename('Chapter 5')

console.log(project.name) // Chapter 5
console.log(project.isActive()) // true

// 3. 여러 믹스인을 순서대로 적용할 수 있다.
function withTimestamp<C extends MixinClassConstructor<object>>(Class: C) {
  return class extends Class {
    readonly createdAt = new Date(2026, 5, 29)

    getCreatedAtIso() {
      return this.createdAt.toISOString()
    }
  }
}

let DebuggableActivatableProject = withEZDebug(
  withActivatable(
    withTimestamp(
      class DebuggableProject {
        constructor(
          public id: string,
          public title: string
        ) {}

        getDebugValue() {
          return {
            id: this.id,
            title: this.title,
          }
        }
      }
    )
  )
)

let mixedProject = new DebuggableActivatableProject('p-1', 'Mixin Example')

mixedProject.activate()

console.log(mixedProject.isActive()) // true
console.log(mixedProject.getCreatedAtIso()) // 2026-06-28T15:00:00.000Z 또는 현지 TZ 기준 ISO
console.log(mixedProject.debug())
// DebuggableProject({"id":"p-1","title":"Mixin Example"})

// 4. 믹스인은 구체적인 메서드를 제공한다. abstract 메서드는 선언할 수 없다.
// 아래처럼 믹스인 안의 익명 클래스에 abstract 메서드를 둘 수 없다.
//
// function invalidAbstractMixin<C extends MixinClassConstructor<object>>(Class: C) {
//   return abstract class extends Class {
//     abstract save(): void
//   }
// }
// Error: 'abstract' modifier cannot be used with an anonymous class declaration.

// 5. 생성자를 가진 클래스에도 믹스인을 적용할 수 있다.
// 믹스인이 반환한 클래스는 원래 생성자 인수 목록을 보존한다.
let anotherDebuggableUser = new DebuggableUser(4, 'Ada', 'Lovelace')

console.log(anotherDebuggableUser.debug())
// HardToDebugUserForMixin({"id":4,"name":"Ada Lovelace"})

// new DebuggableUser('4', 'Ada', 'Lovelace')
// Error: Argument of type 'string' is not assignable to parameter of type 'number'.
