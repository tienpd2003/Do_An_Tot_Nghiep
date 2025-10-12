import React from 'react'
import Header from '../components/Header'
import ProductCard from '../components/ProductCard'
import products from '../data/products'

export default function Home() {
  return (
    <div>
      <Header />
      <main className="p-8 max-w-6xl mx-auto">
        <h1 className="text-3xl font-semibold mb-6">Products</h1>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {products.map(p => (
            <ProductCard key={p.id} product={p} />
          ))}
        </div>
      </main>
    </div>
  )
}
