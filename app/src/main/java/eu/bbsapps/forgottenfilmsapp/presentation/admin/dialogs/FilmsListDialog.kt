package eu.bbsapps.forgottenfilmsapp.presentation.admin.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmFeedItem
import eu.bbsapps.forgottenfilmsapp.presentation.components.OutlinedTextFieldState
import eu.bbsapps.forgottenfilmsapp.presentation.components.OutlinedTextFieldWithHint
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*

@Composable
fun FilmsListDialog(
    films: List<FilmFeedItem>,
    width: Dp,
    filmSearchState: OutlinedTextFieldState,
    onSearchValueChange: (String) -> Unit,
    onSearchChangeFocus: (FocusState) -> Unit,
    onDeleteFilmClicked: (String) -> Unit,
    onAddFilmClicked: () -> Unit,
    filmTitleState: OutlinedTextFieldState,
    onFilmTitleValueChange: (String) -> Unit,
    onFilmTitleChangeFocus: (FocusState) -> Unit,
    filmDescriptionState: OutlinedTextFieldState,
    onFilmDescriptionValueChange: (String) -> Unit,
    onFilmDescriptionChangeFocus: (FocusState) -> Unit,
    filmUrlState: OutlinedTextFieldState,
    onFilmUrlValueChange: (String) -> Unit,
    onFilmUrlChangeFocus: (FocusState) -> Unit,
    filmCategoriesState: OutlinedTextFieldState,
    onFilmCategoriesValueChange: (String) -> Unit,
    onFilmCategoriesChangeFocus: (FocusState) -> Unit,
    filmImageUrlsState: OutlinedTextFieldState,
    onFilmImageUrlsValueChange: (String) -> Unit,
    onFilmImageUrlsChangeFocus: (FocusState) -> Unit,
    onFilmSave: () -> Unit,
    onDismiss: () -> Unit,
    isAddingFilm: Boolean
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.surface.copy(0.8f))
            .fillMaxSize()
            .clickable { onDismiss() }
            .padding(
                top = if (!isAddingFilm) largePaddingValue else mediumPaddingValue,
                start = largePaddingValue,
                end = largePaddingValue,
                bottom = largePaddingValue
            ), contentAlignment = if (!isAddingFilm) Alignment.Center else Alignment.TopCenter
    ) {

        val fontSize =
            if (width < Constants.BIG_SCREEN_THRESHOLD) smallFontValue else mediumFontValue

        Box(
            Modifier
                .clip(RoundedCornerShape(smallRoundedCornerValue))
                .background(MaterialTheme.colors.background)
                .clickable(enabled = false) { }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bigPaddingValue)
            ) {
                if (!isAddingFilm) {
                    Text(
                        text = stringResource(R.string.films),
                        color = MaterialTheme.colors.onSurface,
                        fontSize = if (width < Constants.BIG_SCREEN_THRESHOLD) smallMediumFontValue else mediumBigFontValue
                    )
                    Spacer(modifier = Modifier.height(smallSpacerValue))
                    OutlinedTextFieldWithHint(
                        modifier = Modifier.clickable {},
                        text = filmSearchState.text,
                        hint = filmSearchState.hint,
                        onValueChange = { onSearchValueChange(it) },
                        onFocusChange = { onSearchChangeFocus(it) },
                        isHintVisible = filmSearchState.isHintVisible,
                        error = filmSearchState.error,
                        fontSize = fontSize
                    )
                    Spacer(modifier = Modifier.height(smallSpacerValue))
                    Column(
                        Modifier
                            .height(width / 1.5f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onAddFilmClicked()
                                }
                                .padding(top = mediumPaddingValue)
                        ) {
                            Text(
                                text = stringResource(id = R.string.add_film),
                                color = MaterialTheme.colors.onSurface,
                                modifier = Modifier.weight(4f),
                                fontSize = fontSize
                            )
                            Box(
                                Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "DEL",
                                    tint = Red
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(tinySpacerValue))
                        Divider(color = Red)
                        films.forEach {
                            FilmItem(
                                film = it,
                                fontSize = fontSize,
                                iconSize =
                                if (width < Constants.BIG_SCREEN_THRESHOLD) smallIconSize else mediumBigIconSize
                            ) {
                                onDeleteFilmClicked(it)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(smallSpacerValue))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            color = Red,
                            fontSize = fontSize,
                            modifier = Modifier.clickable { onDismiss() })

                    }
                } else {
                    Text(
                        text = stringResource(R.string.add_film),
                        color = MaterialTheme.colors.onSurface,
                        fontSize = if (width < Constants.BIG_SCREEN_THRESHOLD) smallMediumFontValue else mediumBigFontValue,
                    )
                    Spacer(modifier = Modifier.height(smallSpacerValue))
                    Column(
                        modifier = Modifier
                            .height(if (width < Constants.BIG_SCREEN_THRESHOLD) width else width / 2)
                            .verticalScroll(rememberScrollState())
                    ) {
                        OutlinedTextFieldWithHint(
                            modifier = Modifier.clickable {},
                            text = filmTitleState.text,
                            hint = filmTitleState.hint,
                            onValueChange = { onFilmTitleValueChange(it) },
                            onFocusChange = { onFilmTitleChangeFocus(it) },
                            isHintVisible = filmTitleState.isHintVisible,
                            error = filmTitleState.error,
                            singleLine = false,
                            fontSize = fontSize
                        )
                        Spacer(modifier = Modifier.height(tinySmallSpacerValue))
                        OutlinedTextFieldWithHint(
                            modifier = Modifier.clickable {},
                            text = filmDescriptionState.text,
                            hint = filmDescriptionState.hint,
                            onValueChange = { onFilmDescriptionValueChange(it) },
                            onFocusChange = { onFilmDescriptionChangeFocus(it) },
                            isHintVisible = filmDescriptionState.isHintVisible,
                            error = filmDescriptionState.error,
                            singleLine = false,
                            fontSize = fontSize
                        )
                        Spacer(modifier = Modifier.height(tinySmallSpacerValue))
                        OutlinedTextFieldWithHint(
                            modifier = Modifier.clickable {},
                            text = filmCategoriesState.text,
                            hint = filmCategoriesState.hint,
                            onValueChange = { onFilmCategoriesValueChange(it) },
                            onFocusChange = { onFilmCategoriesChangeFocus(it) },
                            isHintVisible = filmCategoriesState.isHintVisible,
                            error = "Раздели отделните жанрове с ';'",
                            singleLine = false,
                            fontSize = fontSize
                        )
                        Spacer(modifier = Modifier.height(tinySmallSpacerValue))
                        OutlinedTextFieldWithHint(
                            modifier = Modifier.clickable {},
                            text = filmUrlState.text,
                            hint = filmUrlState.hint,
                            onValueChange = { onFilmUrlValueChange(it) },
                            onFocusChange = { onFilmUrlChangeFocus(it) },
                            isHintVisible = filmUrlState.isHintVisible,
                            error = filmUrlState.error,
                            singleLine = false,
                            fontSize = fontSize
                        )
                        Spacer(modifier = Modifier.height(tinySmallSpacerValue))
                        OutlinedTextFieldWithHint(
                            modifier = Modifier.clickable {},
                            text = filmImageUrlsState.text,
                            hint = filmImageUrlsState.hint,
                            onValueChange = { onFilmImageUrlsValueChange(it) },
                            onFocusChange = { onFilmImageUrlsChangeFocus(it) },
                            isHintVisible = filmImageUrlsState.isHintVisible,
                            error = "Раздели отделните снимки с ';'",
                            singleLine = false,
                            fontSize = fontSize
                        )
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            color = MaterialTheme.colors.onSurface,
                            fontSize = fontSize,
                            modifier = Modifier.clickable { onDismiss() }
                        )
                        Spacer(modifier = Modifier.width(bigSpacerValue))

                        val isSaveButtonEnabled = filmTitleState.text.isNotBlank()
                                && filmDescriptionState.text.isNotBlank()
                                && filmCategoriesState.text.isNotBlank()
                                && filmUrlState.text.isNotBlank()
                                && filmImageUrlsState.text.isNotBlank()

                        Text(
                            text = stringResource(id = R.string.save),
                            color = if (isSaveButtonEnabled) Red else Red.copy(0.4f),
                            fontWeight = FontWeight.Bold,
                            fontSize = fontSize,
                            modifier = Modifier.clickable(
                                enabled = isSaveButtonEnabled
                            ) {
                                onFilmSave()
                                onDismiss()
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun FilmItem(
    film: FilmFeedItem,
    fontSize: TextUnit,
    iconSize: Dp,
    onDeleteClicked: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = mediumPaddingValue)
    ) {
        Text(
            text = film.name,
            color = MaterialTheme.colors.onSurface,
            fontSize = fontSize,
            modifier = Modifier.weight(4f)
        )
        Box(
            Modifier
                .weight(1f)
                .fillMaxWidth(), contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                imageVector = Icons.Default.DeleteForever,
                contentDescription = "DEL",
                tint = Red,
                modifier = Modifier
                    .size(iconSize)
                    .clickable { onDeleteClicked(film.name) })
        }
    }
}