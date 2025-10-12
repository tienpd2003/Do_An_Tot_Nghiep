import React from 'react'
import LoginForm from '../components/LoginForm'
import Logo from '../assets/logo.svg'

export default function Login() {
  return (
    <div className="page-root">
      <main className="login-shell">
        <aside className="brand-panel">
          <img src={Logo} alt="Shop logo" className="logo" />
          <h1 className="brand-title">Nordic Tech</h1>
          <p className="brand-sub">Timeless European design. Premium electronics.</p>
        </aside>

        <section className="form-panel">
          <h2>Welcome back</h2>
          <p className="muted">Sign in to access your account and exclusive offers</p>
          <LoginForm />
        </section>
      </main>
    </div>
  )
}
