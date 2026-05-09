package com.free.masaki.sakamoto.todoapp

import androidx.compose.runtime.Composable
import com.free.masaki.sakamoto.todoapp.ui.navigation.AppNavGraph
import com.free.masaki.sakamoto.todoapp.ui.theme.TodoAppTheme

@Composable
fun TodoAppRoute() {
    TodoAppTheme {
        AppNavGraph()
    }
}
