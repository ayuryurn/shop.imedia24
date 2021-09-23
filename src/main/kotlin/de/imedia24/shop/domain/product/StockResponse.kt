package de.imedia24.shop.domain.product

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class StockResponse (

    //I have marked all fields with ? nullable in order to activate some hibernate validation annotations like @NotNull

    @field:NotNull @field:Min (1,message="quantity must be greater than 1") var quantity:Int?){

}
