package eu.kanade.domain.chapter.interactor

import eu.kanade.domain.chapter.repository.ChapterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAvailableScanlators(
    private val repository: ChapterRepository,
) {

    private fun List<String>.cleanupAvailableScanlators(): List<String> {
        return mapNotNull {
            if (it.isNotBlank()) it.trim() else null
        }
            .distinct()
    }

    suspend fun await(mangaId: Long): List<String> {
        return repository.getScanlatorsByMangaId(mangaId)
            .cleanupAvailableScanlators()
    }

    fun subscribe(mangaId: Long): Flow<List<String>> {
        return repository.getScanlatorsByMangaIdAsFlow(mangaId)
            .map { it.cleanupAvailableScanlators() }
    }
}
