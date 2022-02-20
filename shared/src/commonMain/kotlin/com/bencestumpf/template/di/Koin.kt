package com.bencestumpf.template.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initKoin() = startKoin {
    modules(
        module {
            single<CoroutineDispatcher>(named(CoroutineDispatchers.Default)) { Dispatchers.Default }
        },
        datastoreModule,
        repositoryModule,
        usecaseModule,
    )
}

val datastoreModule = module {
}
val repositoryModule = module {
}
val usecaseModule = module {

}

object CoroutineDispatchers {
    const val Default = "default"
}
