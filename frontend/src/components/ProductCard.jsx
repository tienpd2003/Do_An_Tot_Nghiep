import React from 'react'
import { Link } from 'react-router-dom'

export default function ProductCard({ product }) {
  return (
    <article className="bg-white rounded-lg shadow p-4">
      <Link to={`/product/${product.id}`}>
        <img src={product.image} alt={product.name} className="w-full h-44 object-cover rounded-md" />
        <h3 className="mt-3 font-medium">{product.name}</h3>
        <p className="text-muted text-sm">{product.brand}</p>
        <div className="mt-2 font-bold">${product.price}</div>
      </Link>
    </article>
  )
}
