// Simple mock client - replace with axios/fetch wrapper when integrating real backend
export async function post(url, body) {
  // simulate network latency
  await new Promise(r => setTimeout(r, 400))
  // very small router
  if (url === '/auth/login') {
    const { email, password } = body
    if (email && password) {
      return { status: 200, data: { token: 'mock-token', user: { email } } }
    }
    return { status: 400, data: { message: 'Invalid' } }
  }
  return { status: 404 }
}
