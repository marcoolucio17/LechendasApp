package com.example.lechendasapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lechendasapp.data.model.Climate
import com.example.lechendasapp.data.repository.ClimateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

