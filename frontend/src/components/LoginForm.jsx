import React, { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import useAuth from '../hooks/useAuth'

export default function LoginForm() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [remember, setRemember] = useState(false)
  const [error, setError] = useState(null)
  const auth = useAuth()

  function validate() {
    if (!email) return 'Email is required'
    if (!password) return 'Password is required'
    return null
  }

  function handleSubmit(e) {
    e.preventDefault()
    const v = validate()
    if (v) {
      setError(v)
      return
    }
    setError(null)
    // call auth context
    auth.login({ email, password }).then(res => {
      if (res.ok) {
        alert(`Đăng nhập thành công (giả lập)\nEmail: ${email}`)
        navigate('/')
      } else {
        setError(res.message || 'Login failed')
      }
    })
  }

  return (
    <form className="login-form" onSubmit={handleSubmit}>
      {error && <div className="error">{error}</div>}

      <label className="field">
        <span>Email</span>
        <input
          type="email"
          value={email}
          onChange={e => setEmail(e.target.value)}
          placeholder="you@example.com"
          required
        />
      </label>

      <label className="field">
        <span>Password</span>
        <input
          type="password"
          value={password}
          onChange={e => setPassword(e.target.value)}
          placeholder="••••••••"
          required
        />
      </label>

      <label className="inline">
        <input type="checkbox" checked={remember} onChange={e => setRemember(e.target.checked)} />
        <span>Remember me</span>
      </label>

      <button className="btn" type="submit">Sign in</button>

      <div className="footer-row">
        <Link to="#" className="link">Forgot password?</Link>
        <Link to="#" className="link">Create account</Link>
      </div>
    </form>
  )
}
