package eu.bbsapps.forgottenfilmsapp.presentation.admin.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.UserResponse
import eu.bbsapps.forgottenfilmsapp.presentation.components.OutlinedTextFieldState
import eu.bbsapps.forgottenfilmsapp.presentation.components.OutlinedTextFieldWithHint
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*

@Composable
fun UsersListDialog(
    users: List<UserResponse>,
    width: Dp,
    userSearchState: OutlinedTextFieldState,
    onValueChange: (String) -> Unit,
    onChangeFocus: (FocusState) -> Unit,
    onDismiss: () -> Unit,
    onDeleteUserClicked: (UserResponse) -> Unit
) {
    val fontSize = if (width < Constants.BIG_SCREEN_THRESHOLD) 18.sp else 26.sp
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.surface.copy(0.8f))
            .fillMaxSize()
            .clickable { onDismiss() }
            .padding(largePaddingValue), contentAlignment = Alignment.Center
    ) {
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
                Text(
                    text = stringResource(R.string.users),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = if (width < Constants.BIG_SCREEN_THRESHOLD) smallMediumFontValue else mediumBigFontValue
                )
                Spacer(modifier = Modifier.height(smallSpacerValue))
                OutlinedTextFieldWithHint(
                    modifier = Modifier.clickable {},
                    text = userSearchState.text,
                    hint = userSearchState.hint,
                    onValueChange = { onValueChange(it) },
                    onFocusChange = { onChangeFocus(it) },
                    isHintVisible = userSearchState.isHintVisible,
                    error = userSearchState.error,
                    fontSize = fontSize
                )
                Spacer(modifier = Modifier.height(smallSpacerValue))
                Column(
                    Modifier
                        .height(width / 1.5f)
                        .verticalScroll(rememberScrollState())
                ) {
                    users.forEach {
                        UserItem(
                            user = it,
                            fontSize = fontSize,
                            iconSize = if (width < Constants.BIG_SCREEN_THRESHOLD) smallIconSize else mediumBigIconSize
                        ) { userResponse ->
                            onDeleteUserClicked(userResponse)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(smallSpacerValue))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        color = Red,
                        fontSize = fontSize,
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: UserResponse,
    fontSize: TextUnit,
    iconSize: Dp,
    onDeleteClicked: (UserResponse) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = mediumPaddingValue)
    ) {
        Text(
            modifier = Modifier.weight(7f),
            text = user.email,
            color = MaterialTheme.colors.onSurface,
            fontSize = fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Box(
            Modifier
                .weight(1f)
                .fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            Icon(
                imageVector = Icons.Default.DeleteForever,
                contentDescription = "Изтрий",
                tint = Red,
                modifier = Modifier
                    .size(iconSize)
                    .clickable { onDeleteClicked(user) })
        }
    }
}