package eu.kanade.domain.manga.interactor

import eu.kanade.domain.manga.repository.MangaRepository

class SetFilteredScanlators(
    private val mangaRepository: MangaRepository,
) {

    suspend fun await(mangaId: Long, filteredScanlators: List<String>) {
        mangaRepository.setFilteredScanlators(mangaId, filteredScanlators)
    }
}
