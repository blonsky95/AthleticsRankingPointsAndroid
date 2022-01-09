package com.example.athleticsrankingpoints.data.cache

interface Cacheable {
  val key: String
}

class Cache(private val storage: HashMap<String,Cacheable> = HashMap()) {

  val isLoaded
    get() = storage.isNotEmpty()

  operator fun get(key: String) = storage[key]

  fun save(item: Cacheable) {
    storage[item.key]=item
  }

  fun saveAll(items: List<Cacheable>) {
    storage.putAll(items.map { it.key to it })
  }

  fun clearAndSaveAll(items: List<Cacheable>) {
    removeAll()
    saveAll(items)
  }

  fun removeAll() = storage.clear()

  fun getAll() = storage.values.toList()

  fun getAllByKeys(keys: List<String>): List<Cacheable> = keys.mapNotNull { storage[it] }

}