export let locale = {
  code: 'ko',
  greeting: '안녕하세요',
  dateFormat: 'YYYY.MM.DD',
}

export function formatName(firstName: string, lastName: string) {
  return `${lastName}${firstName}`
}

