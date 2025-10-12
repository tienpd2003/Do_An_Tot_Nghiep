import products from '../data/products'

export async function fetchProducts() {
  await new Promise(r => setTimeout(r, 300))
  return products
}

export async function fetchProductById(id) {
  await new Promise(r => setTimeout(r, 200))
  return products.find(p => String(p.id) === String(id))
}
