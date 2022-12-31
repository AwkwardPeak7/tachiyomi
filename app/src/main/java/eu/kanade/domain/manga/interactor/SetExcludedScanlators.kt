package eu.kanade.domain.manga.interactor

import eu.kanade.data.DatabaseHandler

class SetExcludedScanlators(
    private val handler: DatabaseHandler,
) {

    suspend fun await(mangaId: Long, excludedScanlators: List<String>) {
        val excludedScanlatorsSet = excludedScanlators.toSet()
        handler.await(inTransaction = true) {
            val currentExcluded = handler.awaitList {
                excluded_scanlatorsQueries.getExcludedScanlatorsByMangaId(mangaId)
            }.toSet()
            val toAdd = excludedScanlatorsSet.minus(currentExcluded)
            for (scanlator in toAdd) {
                excluded_scanlatorsQueries.insert(mangaId, scanlator)
            }
            val toRemove = currentExcluded.minus(excludedScanlatorsSet)
            excluded_scanlatorsQueries.remove(mangaId, toRemove)
        }
    }
}
