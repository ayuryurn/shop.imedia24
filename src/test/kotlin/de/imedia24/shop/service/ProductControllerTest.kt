package de.imedia24.shop.service


import de.imedia24.shop.controller.ProductController
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.StockResponse
import de.imedia24.shop.exceptions.ProductNotFoundException
import de.imedia24.shop.serviceImpl.ProductServiceImpl
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.math.BigDecimal
import java.util.*


@ContextConfiguration(classes = [ProductController::class])
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
internal class ProductControllerTest(@Autowired private val  productController: ProductController,
                                     @Autowired @Qualifier("productServiceImpl") private  val productService: ProductService) {



    @Test
    @Throws(ProductNotFoundException::class)
    fun getProductBySku() {



        val productBySku = productController.getProductBySku("us-510")
        AssertionsForClassTypes.assertThat(productBySku.statusCode).isEqualTo(HttpStatus.OK)
        AssertionsForClassTypes.assertThat(productBySku.body).toString().contains("sku")


//        val sku = "101"
//        val productResponse = ProductResponse(
//            "50", "sierra", "large screen high resolutions", BigDecimal(150), StockResponse(1)
//        )
//        `when`(this.productServiceImpl.getProductBySku(sku)).thenReturn(productResponse)
//        val get = MockMvcRequestBuilders.get("/api/v1/products/{sku}", "sku")
//        MockMvcBuilders.standaloneSetup(this.productController)
//            .build()
//            .perform(get)
//            .andExpect(MockMvcResultMatchers.status().isOk)
//            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))


    }


    @Test
    @Throws(Exception::class)
    fun createProduct() {



        val sku = UUID.randomUUID().toString()
        val productResponse = ProductResponse(
            sku, "sierra", "large screen high resolutions", BigDecimal(150), StockResponse(1)
        )
        productController.createProduct(productResponse)

        val productBySku = productController.getProductBySku("$sku")
        AssertionsForClassTypes.assertThat(productBySku.statusCode).isEqualTo(HttpStatus.OK)
        AssertionsForClassTypes.assertThat(productBySku.body).toString().contains(productResponse.sku.toString())
        AssertionsForClassTypes.assertThat(productBySku.body).toString().contains(productResponse.name.toString())






//        var productResponse = ProductResponse(
//            "50", "sierra", "large screen high resolutions", BigDecimal(150), StockResponse(1)
//        )
//        val content = ObjectMapper().writeValueAsString(productResponse)
//        val requestBuilder = MockMvcRequestBuilders.post("/api/v1/products")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(content)
//        val actualPerformResult = MockMvcBuilders.standaloneSetup(productController)
//            .build()
//            .perform(requestBuilder)
//        actualPerformResult.andExpect(MockMvcResultMatchers.status().`is`(400))


    }



    @Test
    @Throws(Exception::class)
    fun updateProduct() {

        val productResponse = ProductResponse(
            "us-510", "nokia", "large screen high resolutions", BigDecimal(110), StockResponse(50)
        )
        productController.updateProduct("${productResponse.sku}",productResponse)

        val productBySku = productController.getProductBySku("/${productResponse.sku}")
        AssertionsForClassTypes.assertThat(productBySku.statusCode).isEqualTo(HttpStatus.OK)
        AssertionsForClassTypes.assertThat(productBySku.body).toString().contains(productResponse.sku.toString())
        AssertionsForClassTypes.assertThat(productBySku.body).toString().contains(productResponse.name.toString())
        AssertionsForClassTypes.assertThat(productBySku.body).toString().contains(productResponse.description.toString())
        AssertionsForClassTypes.assertThat(productBySku.body).toString().contains(productResponse.price.toString())
        AssertionsForClassTypes.assertThat(productBySku.body).toString().contains(productResponse.stock.toString())


//        var productResponse = ProductResponse(
//            "50", "sierra", "large screen high resolutions", BigDecimal(150), StockResponse(1)
//        )
//
//        val content = ObjectMapper().writeValueAsString(productResponse)
//        val requestBuilder: MockHttpServletRequestBuilder =
//            MockMvcRequestBuilders.patch("/api/v1/products/{sku}", "Sku")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content)
//        val actualPerformResult = MockMvcBuilders.standaloneSetup(productController)
//            .build()
//            .perform(requestBuilder)
//        actualPerformResult.andExpect(MockMvcResultMatchers.status().`is`(400))

    }

    @Test
    @Throws(Exception::class)
    fun deleteProductBySku() {

        val sku = "us-150"

        val productBySku = productController.getProductBySku("$sku")
        AssertionsForClassTypes.assertThat(productBySku.statusCode).isEqualTo(HttpStatus.OK)
        val deleteProductBySku = productController.deleteProductById(sku)
        AssertionsForClassTypes.assertThat(deleteProductBySku.statusCode).isEqualTo(HttpStatus.NO_CONTENT)

//        var sku = "120"
//        doNothing().`when`(this.productServiceImpl).deleteProduct(sku)
//        val requestBuilder = MockMvcRequestBuilders.delete("/api/v1/products/{sku}", "Sku")
//        val actualPerformResult = MockMvcBuilders.standaloneSetup(productController)
//            .build()
//            .perform(requestBuilder)
//        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent)

    }


    @Test
    @Throws(Exception::class)
    fun getAllProducts() {




//        `when`(this.productServiceImpl.findAllProducts()).thenReturn(ArrayList<ProductResponse>())
//
//        val requestBuilder = MockMvcRequestBuilders.get("/api/v1/products")
//        MockMvcBuilders.standaloneSetup(productController)
//            .build()
//            .perform(requestBuilder)
//            .andExpect(MockMvcResultMatchers.status().isOk)
//            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//            .andExpect(MockMvcResultMatchers.content().string("[]"))

    }


    @Test
    @Throws(Exception::class)
    fun getProductBySkus() {

        val of = setOf("150", "ux140")
        of.forEach{
                sku -> val productBySku = productController.getProductBySku("$sku")
            AssertionsForClassTypes.assertThat(productBySku.statusCode).isEqualTo(HttpStatus.OK)
        }

        val productBySkus = productController.getProductBySkus(of)
        AssertionsForClassTypes.assertThat(productBySkus.statusCode).isEqualTo(HttpStatus.OK)





//        `when`(this.productServiceImpl.findProductsBySkus( setOf<String>()))
//            .thenReturn(HashSet<ProductResponse>())
//        val getResult = MockMvcRequestBuilders.get("/api/v1/products")
//        val requestBuilder = getResult.param("skus", HashSet<String>().toString())
//        MockMvcBuilders.standaloneSetup(productController)
//            .build()
//            .perform(requestBuilder)
//            .andExpect(MockMvcResultMatchers.status().isOk)
//            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//            .andExpect(MockMvcResultMatchers.content().string("[]"))

    }
}