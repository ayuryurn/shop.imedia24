package de.imedia24.shop.db.repository

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.domain.product.ProductResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductEntity, String> {

//    fun findBySku(sku: String): ProductResponse?
}