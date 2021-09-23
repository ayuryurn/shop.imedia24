package de.imedia24.shop.serviceImpl

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.entity.StockEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.StockResponse
import de.imedia24.shop.exceptions.ProductAlreadyExist
import de.imedia24.shop.exceptions.ProductNotFoundException
import de.imedia24.shop.service.ProductService
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

@Service("productServiceImpl")
class ProductServiceImpl(private val productRepository: ProductRepository):ProductService {

    override fun getProductBySku(sku: String): ProductResponse {

        val findById = productRepository.findById(sku)
        if(findById.isEmpty) {
            throw ProductNotFoundException("Product with sku $sku not exist",sku)
        }

    return ProductResponse(findById.get().sku,findById.get().name,findById.get().description,findById.get().price,
        StockResponse(findById.get().stock?.quantity))

    }

   override  fun createProduct(productResponse: ProductResponse):ProductResponse {
        val findById = productResponse.sku?.let { productRepository.findById(it) }
        if(findById!!?.isPresent) throw ProductAlreadyExist("Product with SKU ${findById.get().sku} already exist")

        val stockEntity = StockEntity(null, productResponse.stock?.quantity)
        val productEntity =   ProductEntity(productResponse.sku,
                                 productResponse.name,
                                 productResponse.description,
                                 productResponse.price,
                                    ZonedDateTime.now(),
                                 ZonedDateTime.now(),
                                 stockEntity
                                 )
        productRepository.save(productEntity)
     return productResponse
    }

     override fun updateProduct(sku: String, responseEntity: ProductResponse):ProductResponse {

        getProductBySku(sku)
        val findById = productRepository.findById(sku)
        val productEntity =
            ProductEntity(responseEntity.sku, responseEntity.name, responseEntity.description, responseEntity.price,
                ZonedDateTime.now(), ZonedDateTime.now(),StockEntity(findById.get().stock?.id,responseEntity.stock?.quantity))


        productRepository.save(productEntity)


           return ProductResponse(productEntity.sku, productEntity.name, productEntity.description, productEntity.price
                , StockResponse(productEntity.stock?.quantity))




    }

    override fun deleteProduct(sku: String) {
        val findProductBySku = getProductBySku(sku)

        findProductBySku.sku?.let { productRepository.deleteById(it) }

    }

   override  fun findAllProducts():List<ProductResponse> {
       val lst = ArrayList<ProductResponse>()
        productRepository.findAll().forEach{
            product->lst.add(ProductResponse(product.sku,
            product.name,
            product.description,
            product.price,
            StockResponse(product.stock?.quantity)))
        }
        return lst
    }

    override fun findProductsBySkus(skus: Set<String>):HashSet<ProductResponse> {
        val products = HashSet<ProductResponse>()
         skus.forEach{
             sku-> products.add(getProductBySku(sku))
         }
        return products
    }

}
