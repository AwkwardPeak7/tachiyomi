package eu.kanade.presentation.manga.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.DisabledByDefault
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
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
    excludedScanlators: List<String>,
    onDismissRequest: () -> Unit,
    onConfirm: (List<String>) -> Unit,
) {
    val mutableExcludedScanlators = remember { excludedScanlators.toMutableStateList() }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.exclude_scanlators)) },
        text = {
            LazyColumn {
                availableScanlator
                    .sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it })
                    .forEach { scanlator ->
                        item {
                            val isExcluded = mutableExcludedScanlators.contains(scanlator)
                            val onSelectionChanged = {
                                when (isExcluded) {
                                    true -> mutableExcludedScanlators.remove(scanlator)
                                    false -> mutableExcludedScanlators.add(scanlator)
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
                                Icon(
                                    imageVector = when (isExcluded) {
                                        true -> Icons.Rounded.DisabledByDefault
                                        false -> Icons.Rounded.CheckBoxOutlineBlank
                                    },
                                    tint = if (isExcluded) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        LocalContentColor.current
                                    },
                                    contentDescription = null,
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
            Row {
                TextButton(onClick = mutableExcludedScanlators::clear) {
                    Text(text = stringResource(R.string.action_reset))
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = onDismissRequest) {
                    Text(text = stringResource(android.R.string.cancel))
                }
                TextButton(
                    onClick = {
                        onConfirm(mutableExcludedScanlators)
                        onDismissRequest()
                    },
                ) {
                    Text(text = stringResource(android.R.string.ok))
                }
            }
        },
    )
}
