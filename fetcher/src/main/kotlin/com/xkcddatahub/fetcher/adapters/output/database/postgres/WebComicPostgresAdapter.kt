package com.xkcddatahub.fetcher.adapters.output.database.postgres

import com.xkcddatahub.fetcher.adapters.output.database.postgres.jooq.tables.WebComics.Companion.WEB_COMICS
import com.xkcddatahub.fetcher.application.ports.WebComicsPort
import com.xkcddatahub.fetcher.entity.WebComics
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.jooq.DSLContext
import org.jooq.impl.DSL.max

class WebComicPostgresAdapter(
    private val jooq: DSLContext,
) : WebComicsPort {
    override suspend fun save(comics: WebComics): Boolean =
        with(WEB_COMICS) {
            jooq.insertInto(this)
                .set(ID, comics.id)
                .set(YEAR, comics.year)
                .set(MONTH, comics.month)
                .set(DAY, comics.day)
                .set(TITLE, comics.title)
                .set(SAFE_TITLE, comics.safeTitle)
                .set(TRANSCRIPT, comics.transcript)
                .set(ALTERNATIVE_TEXT, comics.alternativeText)
                .set(IMAGE_URL, comics.imageUrl)
                .set(NEWS, comics.news)
                .set(LINK, comics.link)
                .awaitFirstOrElse { 0 } == 1
        }

    override suspend fun getLatestStoredComicId(): Int =
        jooq.select(max(WEB_COMICS.ID))
            .awaitFirst()?.get("id") as Int? ?: 0
}
