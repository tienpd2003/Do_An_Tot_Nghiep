import React from 'react'
import { Link } from 'react-router-dom'
import Logo from '../assets/logo.svg'

export default function Header() {
  return (
    <header className="bg-white shadow-sm">
      <div className="max-w-6xl mx-auto p-4 flex items-center justify-between">
        <Link to="/" className="flex items-center gap-3">
          <img src={Logo} alt="logo" className="w-10" />
          <span className="font-semibold text-lg">Nordic Tech</span>
        </Link>
        <nav className="flex items-center gap-4">
          <Link to="/login" className="text-sm">Sign in</Link>
        </nav>
      </div>
    </header>
  )
}
