package com.example.nuntium.ui.bookmark

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nuntium.App
import com.example.nuntium.R
import com.example.nuntium.adapters.DatabaseAdapter
import com.example.nuntium.databinding.FragmentBookmarkBinding
import com.example.nuntium.response.Article
import com.example.nuntium.ui.manager.ContainsFragment
import com.example.nuntium.util.UtilList.removeNullable
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import javax.inject.Inject

class BookmarkFragment : ContainsFragment(R.layout.fragment_bookmark) {

    private val binding by viewBinding(FragmentBookmarkBinding::bind)
    private lateinit var adapter: DatabaseAdapter

    @Inject
    lateinit var viewModel: BookmarkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.injectBookmarkFragment(this)
        println()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DatabaseAdapter(listener)
        binding.recyclerView.adapter = adapter

        viewModel.getList().observe(viewLifecycleOwner, {
            if (it.isNotEmpty())
                binding.layoutHolder.visibility = View.GONE else
                binding.layoutHolder.visibility = View.VISIBLE
            adapter.submitList(it)
        })

        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding.recyclerView)
        }
    }

    private val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val article = adapter.currentList[position]
            viewModel.delete(article)
            Snackbar.make(viewHolder.itemView, "Subscription Deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    viewModel.insert(article)
                }.show()
        }
    }

    private val listener = object : DatabaseAdapter.OnItemClickListener {
        override fun onItemClick(article: Article) {
            removeNullable(article)
            val bundle = Bundle()
            bundle.putSerializable("article", article)
            findNavController().navigate(R.id.action_bookmarkFragment_to_articleFragment, bundle)
        }
    }

}