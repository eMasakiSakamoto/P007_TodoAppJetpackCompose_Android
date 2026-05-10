package com.free.masaki.sakamoto.todoapp.ui.screen.todolist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.free.masaki.sakamoto.todoapp.R
import com.free.masaki.sakamoto.todoapp.presentation.todolist.TodoListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onAddClick: () -> Unit,
    onEditClick: (Long) -> Unit,
    onTrashClick: () -> Unit,
    viewModel: TodoListViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("タスク一覧") },
                actions = {
                    TextButton(onClick = onTrashClick) {
                        Text("ゴミ箱")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            if (state.todos.isEmpty()) {
                Text("タスクがありません")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state.todos) { todo ->
                        val titleStyle = if (todo.isCompleted) {
                            MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.LineThrough)
                        } else {
                            MaterialTheme.typography.titleMedium
                        }
                        val bodyStyle = if (todo.isCompleted) {
                            MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough)
                        } else {
                            MaterialTheme.typography.bodyMedium
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onEditClick(todo.id) },
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(todo.title, style = titleStyle)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(todo.description, style = bodyStyle)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row {
                                        TextButton(onClick = { viewModel.moveToTrash(todo.id)}) {
                                            Text("ゴミ箱へ移動")
                                        }
                                        TextButton(onClick = { viewModel.onToggleCompletion(todo) }) {
                                            Text(if (todo.isCompleted) "未完了" else "完了")
                                        }
                                    }
                                }

                                if (todo.isCompleted) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_complete),
                                        contentDescription = "complete",
                                        modifier = Modifier
                                            .size(96.dp)
                                            .padding(8.dp)
                                            .align(Alignment.TopEnd),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
