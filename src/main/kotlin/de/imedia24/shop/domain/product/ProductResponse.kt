package de.imedia24.shop.domain.product


import java.math.BigDecimal
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class ProductResponse(

    //I have marked all fields with ? nullable in order to activate some hibernate validation annotations like @NotNull

    @field:NotNull @field:Size(min=3,message = "sku must contains at least 3 characters") val sku: String?,
    @field:NotNull @field:Size(min=3,message = "name must contains at least 3 characters") val name: String?,
    val description: String?,
    @field:NotNull @field:Min(1,message = "price must be greater than 0$")
                            val price: BigDecimal?,
   @field:NotNull @field:Valid val stock: StockResponse?)
{





//    companion object {
//        fun ProductEntity.toProductResponse() = ProductResponse(
//            sku = sku,
//            name = name,
//            description = description ?: "",
//            price = price,
//            stock = StockResponse(stock?.quantity)
//        )
//    }

}
