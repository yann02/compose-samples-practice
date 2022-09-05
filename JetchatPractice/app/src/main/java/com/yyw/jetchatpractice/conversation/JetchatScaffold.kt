/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yyw.jetchatpractice.conversation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.yyw.jetchatpractice.components.JetchatDrawerContent
import com.yyw.jetchatpractice.ui.theme.JetchatPracticeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetchatDrawer(
    chartName: String = "#composers",
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    onProfileClicked: (String) -> Unit,
    onChatClicked: (String) -> Unit,
    content: @Composable () -> Unit
) {
    JetchatPracticeTheme {
        ModalNavigationDrawer(
            drawerShape = RoundedCornerShape(topEnd = 0.dp),
            drawerState = drawerState,
            drawerContent = {
                JetchatDrawerContent(
                    chartName = chartName,
                    onProfileClicked = onProfileClicked,
                    onChatClicked = onChatClicked
                )
            },
            content = content
        )
    }
}
