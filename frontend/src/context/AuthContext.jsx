import React, { createContext, useState } from 'react'
import { post } from '../api/client'

export const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [token, setToken] = useState(null)

  async function login({ email, password }) {
    const res = await post('/auth/login', { email, password })
    if (res.status === 200) {
      setToken(res.data.token)
      setUser(res.data.user)
      return { ok: true }
    }
    return { ok: false, message: res.data?.message }
  }

  function logout() {
    setUser(null); setToken(null)
  }

  return (
    <AuthContext.Provider value={{ user, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}
