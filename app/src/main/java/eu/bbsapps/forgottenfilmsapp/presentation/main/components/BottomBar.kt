package eu.bbsapps.forgottenfilmsapp.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.presentation.Screen
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.smallIconSize
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.tinyPaddingValue

data class BottomBarItem(val title: String, val icon: ImageVector, val type: BottomBarEntry)

val bottomItems = listOf(
    BottomBarItem(
        resource.getString(R.string.home),
        Icons.Default.Home,
        BottomBarEntry.HOME,
    ),
    BottomBarItem(
        resource.getString(R.string.search),
        Icons.Default.Search,
        BottomBarEntry.SEARCH,
    ),
    BottomBarItem(
        resource.getString(R.string.more),
        Icons.Default.List,
        BottomBarEntry.MORE,
    ),
    BottomBarItem("", Icons.Default.Home, BottomBarEntry.NONE),
)

enum class BottomBarEntry {
    HOME, SEARCH, MORE, NONE
}

@Composable
fun BottomBar(
    currentlySelected: BottomBarEntry,
    navController: NavController,
    onHomeClicked: () -> Unit = { navController.navigateUp() },
    onSearchClicked: () -> Unit = {
        navController.popBackStack()
        navController.navigate(Screen.SearchScreen.route)
    },
    onMoreClicked: () -> Unit = {
        navController.popBackStack()
        navController.navigate(Screen.MoreScreen.route)
    }
) {

    Surface(elevation = 24.dp) {
        BoxWithConstraints(Modifier.fillMaxWidth()) {
            val width = maxWidth
            val iconSize = if (width < BIG_SCREEN_THRESHOLD) smallIconSize else (width / 20)
            val fontSize = if (width < BIG_SCREEN_THRESHOLD) 14.sp else 20.sp

            val currentFocusManager = LocalFocusManager.current

            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
                    .padding(tinyPaddingValue)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                val currentlySelectedItem = bottomItems[when (currentlySelected) {
                    BottomBarEntry.HOME -> 0
                    BottomBarEntry.SEARCH -> 1
                    BottomBarEntry.MORE -> 2
                    else -> 3
                }]
                bottomItems.forEach {
                    if (it.type != BottomBarEntry.NONE)
                        BottomBarElement(
                            item = it,
                            isCurrentlySelected = it == currentlySelectedItem,
                            iconSize = iconSize,
                            fontSize = fontSize
                        ) {
                            currentFocusManager.clearFocus()
                            when (it.type) {
                                BottomBarEntry.HOME -> onHomeClicked()
                                BottomBarEntry.SEARCH -> onSearchClicked()
                                BottomBarEntry.MORE -> onMoreClicked()
                                BottomBarEntry.NONE -> {
                                }
                            }
                        }
                }
            }
        }
    }
}

@Composable
fun BottomBarElement(
    item: BottomBarItem,
    isCurrentlySelected: Boolean,
    iconSize: Dp,
    fontSize: TextUnit,
    onBottomElementClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(iconSize + 32.dp)
            .padding(top = tinyPaddingValue, bottom = tinyPaddingValue)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onBottomElementClicked() }) {
        val color =
            if (isCurrentlySelected) MaterialTheme.colors.onSurface else
                MaterialTheme.colors.onSurface.copy(0.4f)
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(iconSize)
        )
        Text(text = item.title, color = color, fontSize = fontSize)
    }
}