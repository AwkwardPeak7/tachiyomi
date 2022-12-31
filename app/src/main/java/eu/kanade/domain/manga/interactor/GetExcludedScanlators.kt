package eu.kanade.domain.manga.interactor

import eu.kanade.data.DatabaseHandler
import kotlinx.coroutines.flow.Flow

class GetExcludedScanlators(
    private val handler: DatabaseHandler,
) {

    suspend fun await(mangaId: Long): List<String> {
        return handler.awaitList {
            excluded_scanlatorsQueries.getExcludedScanlatorsByMangaId(mangaId)
        }
    }

    fun subscribe(mangaId: Long): Flow<List<String>> {
        return handler.subscribeToList {
            excluded_scanlatorsQueries.getExcludedScanlatorsByMangaId(mangaId)
        }
    }
}
