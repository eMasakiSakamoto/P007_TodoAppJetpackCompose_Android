package com.free.masaki.sakamoto.todoapp.ui.screen.trash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.free.masaki.sakamoto.todoapp.presentation.trash.TrashViewModel
import com.free.masaki.sakamoto.todoapp.ui.common.ErrorDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(
    onBack: () -> Unit,
    viewModel: TrashViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    if (state.showErrorDialog) {
        ErrorDialog(
            message = state.errorMessage,
            onDismiss = { viewModel.dismissErrorDialog() },
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ゴミ箱") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("戻る")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            if (state.todos.isEmpty()) {
                Text("ゴミ箱は空です")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state.todos) { todo ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(todo.title, style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                                Row {
                                    TextButton(onClick = { viewModel.restore(todo.id) }) {
                                        Text("復元")
                                    }
                                    TextButton(onClick = { viewModel.deletePermanently(todo.id) }) {
                                        Text("完全削除")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
