export default function meow(loudness: number) {
  let sound = 'meow'.toUpperCase()

  console.info(`${sound}! loudness=${loudness}`)

  return loudness
}

console.log('c.ts module loaded')