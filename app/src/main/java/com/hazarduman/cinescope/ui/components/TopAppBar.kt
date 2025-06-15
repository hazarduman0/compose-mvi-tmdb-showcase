package com.hazarduman.cinescope.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hazarduman.cinescope.ui.model.TopBarType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    topBarType: TopBarType
) {
    val topBarHeight = 72.dp

    when (topBarType) {
        is TopBarType.NoTopBar -> {}
        is TopBarType.BackTopBar -> {
            TopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(topBarHeight),
                title = { /* No title */ },
                navigationIcon = {
                    BackIconButton(onClick = topBarType.onBack)
                },
                actions = {
                    val actions = topBarType.actions
                    if (actions != null) actions()
                }
            )
        }

        is TopBarType.CloseTopBar -> {
            TopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(topBarHeight),
                title = { /* No title */ },
                navigationIcon = {
                    IconButton(onClick = { topBarType.onClose() }) {
                        Icon(Icons.Filled.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    val actions = topBarType.actions
                    if (actions != null) actions()
                }
            )
        }

        is TopBarType.TitleTopBar -> {
            if (topBarType.titleCentered) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(topBarHeight)
                        .statusBarsPadding(),
                    contentAlignment = Alignment.Center
                ) {
                    TopAppBarTitle(
                        modifier = Modifier.padding(horizontal = 56.dp),
                        title = topBarType.title,
                        textAlign = TextAlign.Center
                    )
                    val actions = topBarType.actions
                    if (actions != null) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp)
                        ) {
                            actions()
                        }
                    }
                }
            } else {
                TopAppBar(
                    modifier = Modifier
                        .statusBarsPadding()
                        .height(topBarHeight),
                    title = {
                        TopAppBarTitle(
                            title = topBarType.title
                        )
                    },
                    actions = {
                        val actions = topBarType.actions
                        if (actions != null) actions()
                    }
                )
            }
        }

        is TopBarType.BackTitleTopBar -> {
            if (topBarType.titleCentered) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(topBarHeight)
                        .statusBarsPadding(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BackIconButton(onClick = topBarType.onBack)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 56.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            TopAppBarTitle(
                                title = topBarType.title,
                                textAlign = TextAlign.Center
                            )
                        }
                        val actions = topBarType.actions
                        if (actions != null) {
                            Box(modifier = Modifier.padding(end = 8.dp)) {
                                actions()
                            }
                        }
                    }
                }
            } else {
                TopAppBar(
                    modifier = Modifier
                        .statusBarsPadding()
                        .height(topBarHeight),
                    title = {
                        TopAppBarTitle(
                            title = topBarType.title
                        )
                    },
                    navigationIcon = {
                        BackIconButton(onClick = topBarType.onBack)
                    },
                    actions = {
                        val actions = topBarType.actions
                        if (actions != null) actions()
                    }
                )
            }
        }

        is TopBarType.CustomTopBar -> {
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(topBarHeight)
            ) {
                topBarType.content()
            }
        }
    }
}

@Composable
private fun BackIconButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
    }
}

@Composable
private fun TopAppBarTitle(
    modifier: Modifier = Modifier,
    title: String,
    textAlign: TextAlign? = null
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier,
        maxLines = 1,
        textAlign = textAlign
    )
}