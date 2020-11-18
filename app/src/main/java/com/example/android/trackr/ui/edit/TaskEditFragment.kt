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

package com.example.android.trackr.ui.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.trackr.R
import com.example.android.trackr.databinding.FragmentTaskEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskEditFragment : Fragment(R.layout.fragment_task_edit) {

    private val args: TaskEditFragmentArgs by navArgs()
    private val viewModel: TaskEditViewModel by viewModels()
    private lateinit var binding: FragmentTaskEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.taskId = args.taskId
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTaskEditBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_save -> {
                    viewModel.save()
                    true
                }
                else -> false
            }
        }
        val menuItemSave = binding.toolbar.menu.findItem(R.id.action_save)
        viewModel.modified.observe(viewLifecycleOwner) { modified ->
            menuItemSave.isEnabled = modified
        }
    }
}