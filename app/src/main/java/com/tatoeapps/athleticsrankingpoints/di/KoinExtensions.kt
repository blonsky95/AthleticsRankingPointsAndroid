package com.tatoeapps.athleticsrankingpoints.di

import com.tatoeapps.athleticsrankingpoints.data.cache.Cache
import com.tatoeapps.athleticsrankingpoints.data.cache.Cacheable
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.ext.getFullName


inline fun <reified T : Cacheable> Module.singleCache() = single(named<T>()) { Cache<T>() }

inline fun <reified T : Cacheable> Scope.getCache() = get<Cache<T>>(named(T::class.getFullName()))

