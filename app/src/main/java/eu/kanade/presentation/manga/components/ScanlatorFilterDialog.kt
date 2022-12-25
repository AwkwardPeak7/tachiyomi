package eu.kanade.presentation.manga.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import eu.kanade.presentation.components.TextButton
import eu.kanade.presentation.util.minimumTouchTargetSize
import eu.kanade.tachiyomi.R

@Composable
fun ScanlatorFilterDialog(
    availableScanlator: List<String>,
    filteredScanlators: List<String>,
    onDismissRequest: () -> Unit,
    onConfirm: (List<String>) -> Unit,
) {
    val mutableFilteredScanlators = remember { filteredScanlators.toMutableStateList() }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.filter_scanlators)) },
        text = {
            LazyColumn {
                availableScanlator.forEach { scanlator ->
                    item {
                        val isFiltered = mutableFilteredScanlators.contains(scanlator)
                        val onSelectionChanged = {
                            when (isFiltered) {
                                true -> mutableFilteredScanlators.remove(scanlator)
                                false -> mutableFilteredScanlators.add(scanlator)
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .clickable {
                                    onSelectionChanged()
                                }
                                .minimumTouchTargetSize()
                                .fillMaxWidth(),
                        ) {
                            Checkbox(
                                checked = !isFiltered,
                                onCheckedChange = null,
                            )
                            Text(
                                text = scanlator,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 24.dp),
                            )
                        }
                    }
                }
            }
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
        ),
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(mutableFilteredScanlators)
                    onDismissRequest()
                },
            ) {
                Text(text = stringResource(android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { mutableFilteredScanlators.clear() },
            ) {
                Text(text = stringResource(R.string.action_reset))
            }
        },
    )
}
