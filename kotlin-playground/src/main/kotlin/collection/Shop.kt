package collection

import com.sun.tools.javac.comp.Todo

fun Shop.getWaitingCustomers(): List<Customer> {
    return this.customers
        .filter { it.orders.any { !it.isDelivered }}
}

fun Shop.countProductSales(product: Product): Int {
    return this.customers
        .flatMap { it.orders }
        .flatMap { it.products }
        .count { it.name == product.name }
}

fun Shop.getCustomers(
    minAmount: Double
): List<Customer> {
    return this.customers
        .filter { it.orders.sumOf { it.products.sumOf { it.price }} >= minAmount }
}

data class Shop(
    val name: String,
    val customers: List<Customer>
)

data class Customer(
    val name: String,
    val city: City,
    val orders: List<Order>
)

data class Order(
    val products: List<Product>,
    val isDelivered: Boolean
)

data class Product(
    val name: String,
    val price: Double
)

data class City(
    val name: String
)