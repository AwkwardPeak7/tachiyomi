package eu.kanade.domain.manga.model

data class MangaExtra(
    val mangaId: Long,
    val filteredScanlators: List<String> = emptyList(),
)
