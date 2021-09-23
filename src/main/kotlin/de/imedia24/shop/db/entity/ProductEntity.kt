package de.imedia24.shop.db.entity

import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.persistence.*


@Entity
@Table(name = "products")
data class ProductEntity(

    //I have marked all fields with ? nullable in order to activate some hibernate validation annotations like @NotNull

    @Id
    @Column(name = "sku", nullable = false)
    val sku: String?,

    @Column(name = "name", nullable = false)
    val name: String?,

    @Column(name = "description")
    val description: String? = null,

    @Column(name = "price", nullable = false,columnDefinition = "NUMERIC(19,2)")
    val price: BigDecimal?,

    @UpdateTimestamp
    @Column(name = "created_at", nullable = false,updatable = false)
    val createdAt: ZonedDateTime?,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: ZonedDateTime?,

    @OneToOne( cascade = [(CascadeType.ALL)])
    @JoinColumn(name="stock")
     val stock:StockEntity?

) {
    constructor() : this("","","", BigDecimal(1), ZonedDateTime.now(), ZonedDateTime.now(),StockEntity(BigDecimal(1),0)) {

    }
}
