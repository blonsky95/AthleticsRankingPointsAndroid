package com.example.athleticsrankingpoints.data.cache

interface Cacheable {
  val key: String
}

class Cache<T: Cacheable>(private val storage: HashMap<String,T> = HashMap()) {

  val isLoaded
    get() = storage.isNotEmpty()

  operator fun get(key: String) = storage[key]

  fun save(item: T) {
    storage[item.key]=item
  }

  fun saveAll(items: List<T>) {
    storage.putAll(items.map { it.key to it })
  }

  fun clearAndSaveAll(items: List<T>) {
    removeAll()
    saveAll(items)
  }

  fun removeAll() = storage.clear()

  fun getAll() = storage.values.toList()

  fun getAllByKeys(keys: List<String>): List<T> = keys.mapNotNull { storage[it] }

}