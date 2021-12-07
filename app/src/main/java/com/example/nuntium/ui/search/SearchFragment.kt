package com.example.nuntium.ui.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.CheckResult
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.nuntium.App
import com.example.nuntium.R
import com.example.nuntium.adapters.PageAdapter
import com.example.nuntium.databinding.FragmentSearchBinding
import com.example.nuntium.response.Article
import com.example.nuntium.ui.manager.ContainsFragment
import com.example.nuntium.util.UtilList.removeNullable
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject


class SearchFragment : ContainsFragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private lateinit var adapter: PageAdapter
    private var isEmpty = true

    companion object {
        const val REQUEST_CODE_SPEECH_INPUT = 12
    }

    @Inject
    lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.injectSearchFragment(this)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNetworkState().observe(viewLifecycleOwner, {
            if (!it)
                binding.connectionChecker.visibility = View.VISIBLE
            else
                binding.connectionChecker.visibility = View.GONE
        })

        adapter = PageAdapter(listener)
        binding.recyclerView.adapter = adapter
        binding.edSearch.textChanges().debounce(500)
            .onEach {
                viewModel.setQuery(it.toString())
            }.launchIn(this)
        viewModel.currentSate.observe(viewLifecycleOwner, {
            getChangesEditText(it.isEmpty())
        })
        viewModel.query.observe(viewLifecycleOwner, {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })
        binding.btnVoiceAndCancel.setOnClickListener {
            if (isEmpty) {
                speak()
            } else {
                binding.edSearch.text.clear()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (data != null && resultCode == RESULT_OK) {
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                binding.edSearch.setText(result!![0])
            }
        }
    }

    private fun speak() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "HI speak something")
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "" + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    @ExperimentalCoroutinesApi
    @CheckResult
    fun EditText.textChanges(): Flow<CharSequence?> {
        return callbackFlow {
            val listener = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    isEmpty = s.toString().isEmpty()
                    getChangesEditText(isEmpty)
                    trySend(s)
                }
            }
            addTextChangedListener(listener)
            awaitClose { removeTextChangedListener(listener) }
        }.onStart { emit(text) }
    }

    private val listener = object : PageAdapter.OnItemClickListener {
        override fun onItemClick(article: Article) {
            removeNullable(article)
            val bundle = Bundle()
            bundle.putSerializable("article", article)
            findNavController().navigate(R.id.action_searchFragment_to_articleFragment, bundle)
        }
    }

    fun getChangesEditText(isEmpty: Boolean) {
        if (isEmpty) {
            binding.edSearch.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_search),
                null,
                null,
                null
            )
            binding.btnVoiceAndCancel.setImageResource(R.drawable.ic_microphone)
        } else {
            binding.edSearch.setCompoundDrawables(
                null,
                null,
                null,
                null
            )
            binding.btnVoiceAndCancel.setImageResource(R.drawable.ic_close)
        }
    }
}