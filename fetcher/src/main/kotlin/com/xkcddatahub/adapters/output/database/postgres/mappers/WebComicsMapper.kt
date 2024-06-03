package com.xkcddatahub.adapters.output.database.postgres.mappers

import com.xkcddatahub.adapters.output.database.postgres.data.DatabaseWebComics
import com.xkcddatahub.domain.entity.WebComics

object WebComicsMapper {
    fun WebComics.toData() =
        DatabaseWebComics(
            id = id,
            year = year,
            month = month,
            day = day,
            title = title,
            safeTitle = safeTitle,
            transcript = transcript,
            alternativeText = alternativeText,
            imageUrl = imageUrl,
            news = news,
            link = link
        )
}
