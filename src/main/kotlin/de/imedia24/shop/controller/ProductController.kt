package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import de.imedia24.shop.serviceImpl.ProductServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/api/v1")
 class ProductController(@Qualifier("productServiceImpl") private val productService: ProductService) {


    private val logger = LoggerFactory.getLogger(ProductController::class.java)

    //will retrieve product based on sku one at the time
    @GetMapping("/products/{sku}", produces = ["application/json;charset=utf-8"])
     fun getProductBySku(@PathVariable("sku") sku: String): ResponseEntity<EntityModel<ProductResponse>> {

        logger.info("Request for product $sku")

        val product = productService.getProductBySku(sku)

        logger.info("will create self url")
        val selfLink = linkTo(methodOn(ProductController::class.java).getProductBySku(sku))
            .withSelfRel()

        logger.info("will include self link in  retrieved product")
        val resource = EntityModel.of(product)
        resource.add(selfLink)

        return ResponseEntity.ok().body(resource)
    }

    //will create new product
    @PostMapping( "/products", consumes = ["application/json;charset=utf-8"])
    fun createProduct(@Valid @RequestBody productResponse:ProductResponse):ResponseEntity<EntityModel<ProductResponse>>{
        logger.info("Request to add product $productResponse")

        val createProduct = productService.createProduct(productResponse)


        logger.info("will create self url")
        val selfLink = linkTo(methodOn(ProductController::class.java).getProductBySku("${createProduct.sku}"))
            .withSelfRel()

        logger.info("will include self link in  retrieved product")
        val resource = EntityModel.of(createProduct)
        resource.add(selfLink)

        return ResponseEntity.status(HttpStatus.CREATED).body(resource)

    }

    //will update product if exists
    @PatchMapping("/products/{sku}",consumes = ["application/json;charset=utf-8"])
    fun updateProduct(@PathVariable sku:String,@Valid @RequestBody responseEntity:ProductResponse):ResponseEntity<EntityModel<ProductResponse>>{
        logger.info("Request to update product with sku $sku")
        val updateProduct = productService.updateProduct(sku, responseEntity)

        logger.info("will create self url")
        val selfLink = linkTo(methodOn(ProductController::class.java).getProductBySku("${updateProduct.sku}"))
            .withSelfRel()

        logger.info("will include self link in  retrieved product")
        val resource = EntityModel.of(updateProduct)
        resource.add(selfLink)

        return ResponseEntity.status(HttpStatus.CREATED).body(resource)

    }

    //will delete product based on skus if product exists
    @DeleteMapping("/products/{sku}")
    fun deleteProductById(@PathVariable sku:String):ResponseEntity<Any>{
        logger.info("Request to delete product with sku $sku")
        productService.deleteProduct(sku)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with sku $sku got deleted")
    }


    //will retrieve all the available products
    @GetMapping("/products", produces = ["application/json;charset=utf-8"])
    fun getAllProducts(): ResponseEntity<CollectionModel<EntityModel<ProductResponse>>> {
        logger.info("Request to all retrieve products")
        val allProducts = productService.findAllProducts()

       val list = arrayListOf<EntityModel<ProductResponse>>()

        allProducts.forEach{product->
            logger.info("will create self url for every product")
            val selfLink = linkTo(methodOn(ProductController::class.java).getProductBySku("${product.sku}"))
                .withSelfRel()

            logger.info("will include self link in  retrieved product")
            val resource = EntityModel.of(product)
            resource.add(selfLink)
            list.add(resource)
        }


        logger.info("will create self url for available products")
        val withRel = linkTo(methodOn(ProductController::class.java).getAllProducts())
            .withRel("find_all_products")

        logger.info("will include self link used to retrieve all products")
        val res = CollectionModel.of(list)
        res.add(withRel)


        return ResponseEntity.status(HttpStatus.OK).body(res)
    }


    //will retrieve products by skus if they are exists
    @GetMapping("/products",params = ["skus"], produces = ["application/json;charset=utf-8"])
    fun getProductBySkus(@RequestParam("skus") skus:Set<String>):ResponseEntity<CollectionModel<EntityModel<ProductResponse>>>{
        logger.info("Request to retrieve products with couple of $skus")
        val findProductsBySkus = productService.findProductsBySkus(skus)


        val list = ArrayList<EntityModel<ProductResponse>>()

        findProductsBySkus.forEach{product->
            logger.info("will create self url for every product")
            val withSelfRel =
                         linkTo(methodOn(ProductController::class.java).getProductBySku("${product.sku}"))
                    .withSelfRel()

            logger.info("will include self link in  retrieved product")
            val resource = EntityModel.of(product)
            resource.add(withSelfRel)
            list.add(resource)
        }


        logger.info("will create used self url to access available products")
        val withSelfRel =
            linkTo(methodOn(ProductController::class.java).getProductBySkus(skus)).withSelfRel()

        logger.info("will include self link used to retrieve selected products")
        val res = CollectionModel.of(list)
        res.add(withSelfRel)

        return ResponseEntity.status(HttpStatus.OK).body(res)
    }
}
