package eu.kanade.domain.manga.interactor

import eu.kanade.domain.manga.model.MangaExtra
import eu.kanade.domain.manga.repository.MangaRepository
import eu.kanade.tachiyomi.util.system.logcat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import logcat.LogPriority

class GetMangaExtra(
    private val mangaRepository: MangaRepository,
) {

    suspend fun await(mangaId: Long): MangaExtra {
        val mangaExtra = try {
            mangaRepository.getMangaExtra(mangaId)
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
            null
        }

        return mangaExtra ?: MangaExtra(mangaId = mangaId)
    }

    fun subscribe(mangaId: Long): Flow<MangaExtra> {
        return mangaRepository.getMangaExtraAsFlow(mangaId)
            .map { it ?: MangaExtra(mangaId = mangaId) }
    }
}
