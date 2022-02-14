package eu.bbsapps.forgottenfilmsapp.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.presentation.Screen
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.tinySpacerValue

@Composable
fun MainTopBar(
    navController: NavController,
    onMoviesClicked: () -> Unit = {
        navController.popBackStack()
        navController.navigate(Screen.MoviesScreen.route)
    },
    onMyListClicked: () -> Unit = {
        navController.popBackStack()
        navController.navigate(Screen.MyListScreen.route)
    },
    onLogoClicked: () -> Unit = {
        navController.popBackStack()
    }
) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val width = maxWidth
        val logoSize = if (width < BIG_SCREEN_THRESHOLD) (width / 7) else (width / 9)
        val fontSize = if (width < BIG_SCREEN_THRESHOLD) 18.sp else 26.sp

        val currentFocusManager = LocalFocusManager.current

        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(tinySpacerValue))
            Image(
                painter = painterResource(id = R.drawable.logosmall),
                modifier = Modifier
                    .size(logoSize)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        currentFocusManager.clearFocus()
                        onLogoClicked()
                    },
                contentDescription = null
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.films),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = fontSize,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        currentFocusManager.clearFocus()
                        onMoviesClicked()
                    })
                Text(
                    text = stringResource(R.string.my_list),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = fontSize,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        currentFocusManager.clearFocus()
                        onMyListClicked()
                    })
            }

        }
    }
}