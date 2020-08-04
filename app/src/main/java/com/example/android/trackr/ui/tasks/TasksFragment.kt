/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackr.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.trackr.R
import com.example.android.trackr.db.AppDatabase
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : Fragment() {

    private lateinit var viewModel: TasksViewModel

    private val tasksAdapter = TasksAdapter(object : TasksAdapter.TaskItemListener {
        override fun onItemClicked() {
            findNavController().navigate(R.id.nav_task_detail)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasks_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = tasksAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =
            TasksViewModelFactory(AppDatabase.getInstance(requireContext()).taskDao()).create(
                TasksViewModel::class.java
            )

        viewModel.tasks.observe(viewLifecycleOwner) {
            tasksAdapter.addHeadersAndSubmitList(requireContext(), it)
        }
    }
}