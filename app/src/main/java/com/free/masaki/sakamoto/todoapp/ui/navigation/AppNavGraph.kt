package com.free.masaki.sakamoto.todoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.free.masaki.sakamoto.todoapp.ui.screen.todoedit.TodoEditScreen
import com.free.masaki.sakamoto.todoapp.ui.screen.todolist.TodoListScreen
import com.free.masaki.sakamoto.todoapp.ui.screen.trash.TrashScreen

object Routes {
    const val TODO_LIST = "todo_list"
    const val TODO_EDIT = "todo_edit"
    const val TRASH = "trash"
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Routes.TODO_LIST,
    ) {
        composable(Routes.TODO_LIST) {
            TodoListScreen(
                onAddClick = { navController.navigate(Routes.TODO_EDIT) },
                onEditClick = { todoId ->
                    navController.navigate("${Routes.TODO_EDIT}?todoId=$todoId")
                },
                onTrashClick = { navController.navigate(Routes.TRASH) },
            )
        }

        composable(
            route = "${Routes.TODO_EDIT}?todoId={todoId}",
            arguments = listOf(
                navArgument("todoId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {
            TodoEditScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.TRASH) {
            TrashScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
