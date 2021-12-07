package com.example.nuntium.ui.manager

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.nuntium.util.NetworkConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class ContainsFragment(idLayout: Int) : Fragment(idLayout), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private lateinit var networkConnection: NetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        networkConnection = NetworkConnection(requireContext())
    }

    fun checkNetworkState() = networkConnection

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}