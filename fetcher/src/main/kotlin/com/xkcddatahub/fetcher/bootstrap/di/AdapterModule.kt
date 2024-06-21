package com.xkcddatahub.fetcher.bootstrap.di

import com.xkcddatahub.fetcher.adapters.output.database.postgres.WebComicPostgresAdapter
import com.xkcddatahub.fetcher.adapters.output.http.rest.xkcd.XkcdClientAdapter
import com.xkcddatahub.fetcher.application.ports.WebComicsPort
import com.xkcddatahub.fetcher.application.ports.XkcdClientPort
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.koin.dsl.module

val adapterModule =
    module {
        single<DSLContext> { DSL.using(get()) }
        single<XkcdClientPort> { XkcdClientAdapter(get()) }
        single<WebComicsPort> { WebComicPostgresAdapter(get()) }
    }
