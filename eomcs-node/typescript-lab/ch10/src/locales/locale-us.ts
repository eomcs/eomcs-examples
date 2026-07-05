export let locale = {
  code: 'us',
  greeting: 'Hello',
  dateFormat: 'MM/DD/YYYY',
}

export function formatName(firstName: string, lastName: string) {
  return `${firstName} ${lastName}`
}

