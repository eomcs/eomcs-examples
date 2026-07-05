/**
 * 클래스와 상속 (Classes and Inheritance)
 *
 * 체스 엔진 예제로 클래스, 생성자, 접근 제어자, abstract 클래스,
 * 상속, super 사용법을 살펴본다.
 */

type ChessColor = 'Black' | 'White'
type ChessFile = 'A' | 'B' | 'C' | 'D' | 'E' | 'F' | 'G' | 'H'
type ChessRank = 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8

class ChessPosition {
  // 생성자 파라미터에 접근 제어자를 붙이면 프로퍼티 선언과 할당이 자동으로 처리된다.
  constructor(
    private readonly file: ChessFile,
    private readonly rank: ChessRank
  ) {}

  distanceFrom(position: ChessPosition) {
    return {
      rank: Math.abs(position.rank - this.rank),
      file: Math.abs(position.file.charCodeAt(0) - this.file.charCodeAt(0)),
    }
  }

  toString() {
    return `${this.file}${this.rank}`
  }
}

abstract class ChessPiece {
  // protected는 이 클래스와 서브클래스에서 접근할 수 있다.
  protected position: ChessPosition

  constructor(
    // private readonly는 이 클래스 안에서만 읽을 수 있고, 생성자 이후에는 바꿀 수 없다.
    private readonly color: ChessColor,
    file: ChessFile,
    rank: ChessRank
  ) {
    this.position = new ChessPosition(file, rank)
  }

  getColor() {
    return this.color
  }

  getPosition() {
    return this.position
  }

  moveTo(position: ChessPosition) {
    if (!this.canMoveTo(position)) {
      return false
    }

    this.position = position
    return true
  }

  // abstract 메서드는 서브클래스가 반드시 구현해야 한다.
  abstract canMoveTo(position: ChessPosition): boolean
}

class King extends ChessPiece {
  canMoveTo(position: ChessPosition) {
    let distance = this.position.distanceFrom(position)

    return distance.rank < 2 && distance.file < 2
  }
}

class Rook extends ChessPiece {
  constructor(color: ChessColor, file: ChessFile, rank: ChessRank) {
    // 자식 클래스에 생성자가 있으면 super()로 부모 생성자를 반드시 호출해야 한다.
    super(color, file, rank)
  }

  canMoveTo(position: ChessPosition) {
    let distance = this.position.distanceFrom(position)

    return distance.rank === 0 || distance.file === 0
  }
}

class TrackedKing extends King {
  moveTo(position: ChessPosition) {
    let from = this.position.toString()
    let moved = super.moveTo(position)
    let to = this.position.toString()

    console.log(`TrackedKing: ${from} -> ${to}`, moved)
    return moved
  }
}

let whiteKing = new King('White', 'E', 1)
let blackRook = new Rook('Black', 'A', 8)
let trackedKing = new TrackedKing('White', 'D', 4)

console.log(whiteKing.getColor()) // White
console.log(whiteKing.getPosition().toString()) // E1

console.log(whiteKing.moveTo(new ChessPosition('E', 2))) // true
console.log(whiteKing.getPosition().toString()) // E2

console.log(whiteKing.moveTo(new ChessPosition('E', 4))) // false
console.log(whiteKing.getPosition().toString()) // E2

console.log(blackRook.moveTo(new ChessPosition('A', 1))) // true
console.log(blackRook.moveTo(new ChessPosition('B', 2))) // false

trackedKing.moveTo(new ChessPosition('E', 5)) // TrackedKing: D4 -> E5 true
trackedKing.moveTo(new ChessPosition('E', 7)) // TrackedKing: E5 -> E5 false

// new ChessPiece('White', 'E', 1)
// Error: Cannot create an instance of an abstract class.

// class InvalidPiece extends ChessPiece {}
// Error: Non-abstract class 'InvalidPiece' does not implement inherited abstract member canMoveTo.

// whiteKing.color
// Error: Property 'color' is private and only accessible within class 'ChessPiece'.

// whiteKing.position
// Error: Property 'position' is protected and only accessible within class 'ChessPiece' and its subclasses.

// whiteKing.getPosition().file
// Error: Property 'file' is private and only accessible within class 'ChessPosition'.

// class InvalidRook extends ChessPiece {
//   constructor(color: ChessColor, file: ChessFile, rank: ChessRank) {
//     this.position = new ChessPosition(file, rank)
//   }
//
//   canMoveTo(position: ChessPosition) {
//     return true
//   }
// }
// Error: Constructors for derived classes must contain a 'super' call.

// class InvalidSuperAccess extends King {
//   readParentPosition() {
//     return super.position
//   }
// }
// Error: Only public and protected methods of the base class are accessible via the 'super' keyword.
