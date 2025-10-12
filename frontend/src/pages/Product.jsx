import React from 'react'
import { useParams, Link } from 'react-router-dom'
import Header from '../components/Header'
import products from '../data/products'

export default function Product() {
  const { id } = useParams()
  const product = products.find(p => String(p.id) === id)
  if (!product) return (
    <div>
      <Header />
      <main className="p-8 max-w-4xl mx-auto">Product not found</main>
    </div>
  )

  return (
    <div>
      <Header />
      <main className="p-8 max-w-4xl mx-auto">
        <div className="flex gap-8">
          <img src={product.image} alt={product.name} className="w-80 h-56 object-cover rounded-md" />
          <div>
            <h1 className="text-2xl font-semibold">{product.name}</h1>
            <p className="text-muted mt-2">{product.description}</p>
            <div className="mt-4 text-xl font-bold">${product.price}</div>
            <div className="mt-6">
              <button className="btn">Add to cart</button>
            </div>
            <div className="mt-4">
              <Link to="/">Back to products</Link>
            </div>
          </div>
        </div>
      </main>
    </div>
  )
}
