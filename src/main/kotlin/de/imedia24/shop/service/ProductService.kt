package de.imedia24.shop.service

import de.imedia24.shop.domain.product.ProductResponse


interface ProductService {

    fun getProductBySku(sku: String): ProductResponse
    fun createProduct(productResponse: ProductResponse):ProductResponse
    fun updateProduct(sku: String, responseEntity: ProductResponse):ProductResponse
    fun deleteProduct(sku: String)
    fun findAllProducts():List<ProductResponse>
    fun findProductsBySkus(skus: Set<String>):HashSet<ProductResponse>
}