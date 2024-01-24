package com.hilguener.a2do.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.hilguener.a2do.R
import com.hilguener.a2do.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var taskAdapter: TaskAdapter
    private val taskViewModel: TasksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        taskAdapter = TaskAdapter()

        binding.apply {
            recyclerViewTasks.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(this@TasksActivity)
                setHasFixedSize(true)
            }
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivity(intent)
        }


        taskViewModel.tasks.observe(this) {
            taskAdapter.submitList(it)
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                taskViewModel.searchQuery.value = newText ?: "tarefa não encontrada"
                return true
            }
        })

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all_completed_tasks -> {

                true
            }

            R.id.action_sort_by_category -> {
                taskViewModel.sortOrder.value = Sort.BY_CATEGORY
                true
            }

            R.id.action_sort_by_name -> {
                taskViewModel.sortOrder.value = Sort.BY_NAME
                true
            }

            R.id.check_box_completed -> {
                item.isCheckable = !item.isChecked
                taskViewModel.hideCompleted.value = item.isChecked
                true
            }

            // Adicione mais casos conforme necessário
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
