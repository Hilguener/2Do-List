package com.hilguener.a2do.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hilguener.a2do.dao.TaskDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskDAO: TaskDAO
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val sortOrder = MutableStateFlow(Sort.BY_CATEGORY)
    val hideCompleted = MutableStateFlow(false)

    private val tasksFlow =
        combine(
            searchQuery, sortOrder, hideCompleted
        ) { query, sortOrder, hideCompleted ->
            Triple(query, sortOrder, hideCompleted)
        }.flatMapLatest {(query, sortOrder, hideCompleted) ->
            taskDAO.getTasks(query, sortOrder, hideCompleted)
        }
    val tasks = tasksFlow.asLiveData()
}

enum class Sort {
    BY_NAME,
    BY_CATEGORY
}