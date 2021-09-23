package de.imedia24.shop.db.entity

import java.math.BigDecimal
import java.math.BigInteger
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
@Table(name="stock")

 class StockEntity(

    //I have marked all fields with ? nullable in order to activate some hibernate validation annotations like @NotNull


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: BigDecimal?,
    val quantity: Int?

        ) {
    constructor() : this(BigDecimal(1), 5) {

    }
}
