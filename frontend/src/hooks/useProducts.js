import { useState, useEffect } from 'react'
import { fetchProducts } from '../api/products'

export default function useProducts() {
  const [items, setItems] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    let mounted = true
    fetchProducts().then(data => {
      if (mounted) { setItems(data); setLoading(false) }
    })
    return () => { mounted = false }
  }, [])

  return { items, loading }
}
