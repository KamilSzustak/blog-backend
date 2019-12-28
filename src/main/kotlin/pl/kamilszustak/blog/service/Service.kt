package pl.kamilszustak.blog.service

interface Service<T> {

    fun getAll(): List<T>

    fun getById(id: Int): T?

    fun add(item: T)

    fun deleteById(id: Int)

    fun put(item: T)
}