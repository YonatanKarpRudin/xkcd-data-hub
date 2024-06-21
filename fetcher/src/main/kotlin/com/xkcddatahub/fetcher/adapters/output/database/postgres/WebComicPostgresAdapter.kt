package com.xkcddatahub.fetcher.adapters.output.database.postgres

import com.xkcddatahub.fetcher.application.ports.WebComicsPort
import com.xkcddatahub.fetcher.entity.WebComics

class WebComicPostgresAdapter : WebComicsPort {
    override suspend fun save(comics: WebComics): Boolean = true
//        dbQuery {
//            val data = comics.toData()
//            WebComicsTable.insert {
//                it[id] = data.id
//                it[year] = data.year
//                it[month] = data.month
//                it[day] = data.day
//                it[title] = data.title
//                it[safeTitle] = data.safeTitle
//                it[transcript] = data.transcript
//                it[alternativeText] = data.alternativeText
//                it[imageUrl] = data.imageUrl
//                it[news] = data.news
//                it[link] = data.link
//            }.insertedCount == 1
//        }

    override suspend fun getLatestStoredComicId(): Int = 1
//        dbQuery {
//            WebComicsTable
//                .select(WebComicsTable.id.max())
//                .single()[WebComicsTable.id.max()]
//                ?: 0
//        }
}
