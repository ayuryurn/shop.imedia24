package de.imedia24.shop.exceptions

class ProductNotFoundException(message: String?,val path:String) : Exception(message) {
}