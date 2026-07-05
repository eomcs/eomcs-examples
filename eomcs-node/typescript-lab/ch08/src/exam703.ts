/**
 * 타입 안전한 프로토콜 (Typesafe Protocols)
 *
 * 커맨드별 입력 튜플과 출력 타입을 1:1로 매핑하면,
 * 요청-응답 프로토콜을 타입 안전하게 호출할 수 있다.
 */

export {}

type Matrix = number[][]

type MatrixProtocol = {
  determinant: {
    in: [Matrix]
    out: number
  }
  'dot-product': {
    in: [Matrix, Matrix]
    out: Matrix
  }
  invert: {
    in: [Matrix]
    out: Matrix
  }
}

type Protocol = {
  [command: string]: {
    in: unknown[]
    out: unknown
  }
}

function determinant(matrix: Matrix): number {
  if (matrix.length !== 2 || matrix[0].length !== 2 || matrix[1].length !== 2) {
    throw new Error('Only 2x2 matrices are supported in this example')
  }

  return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
}

function dotProduct(left: Matrix, right: Matrix): Matrix {
  return left.map((row, rowIndex) =>
    row.map((value, columnIndex) => value * right[rowIndex][columnIndex])
  )
}

function invert(matrix: Matrix): Matrix {
  let det = determinant(matrix)

  if (det === 0) {
    throw new Error('Matrix is not invertible')
  }

  return [
    [matrix[1][1] / det, -matrix[0][1] / det],
    [-matrix[1][0] / det, matrix[0][0] / det],
  ]
}

let matrixWorker = {
  determinant: async (matrix: Matrix) => determinant(matrix),
  'dot-product': async (left: Matrix, right: Matrix) => dotProduct(left, right),
  invert: async (matrix: Matrix) => invert(matrix),
}

function createProtocol<P extends Protocol>(worker: {
  [K in keyof P]: (...args: P[K]['in']) => Promise<P[K]['out']>
}) {
  return <K extends keyof P>(command: K) =>
    (...args: P[K]['in']) =>
      worker[command](...args)
}

let runWithMatrixProtocol = createProtocol<MatrixProtocol>(matrixWorker)
let parallelDeterminant = runWithMatrixProtocol('determinant')
let parallelDotProduct = runWithMatrixProtocol('dot-product')
let parallelInvert = runWithMatrixProtocol('invert')

parallelDeterminant([
  [1, 2],
  [3, 4],
]).then((result) => console.info('determinant:', result))

parallelDotProduct(
  [
    [1, 2],
    [3, 4],
  ],
  [
    [10, 20],
    [30, 40],
  ]
).then((result) => console.info('dot-product:', result))

parallelInvert([
  [1, 2],
  [3, 4],
]).then((result) => console.info('invert:', result))

// parallelDeterminant([[1, 2]], [[3, 4]])
// Error: Expected 1 arguments, but got 2.

// parallelDotProduct([[1, 2]])
// Error: Expected 2 arguments, but got 1.

// runWithMatrixProtocol('transpose')
// Error: Argument of type '"transpose"' is not assignable to parameter of type 'keyof MatrixProtocol'.

// README의 브라우저 예제에서는 createProtocol(script)가 내부에서 Worker를 만들고
// worker.postMessage({ command, args })로 요청한다.
// 이 파일은 Node에서도 실행 가능하도록 같은 타입 구조를 로컬 worker 객체로 흉내 낸다.

