package com.example.nuntium.ui.random

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.paging.cachedIn
import com.example.nuntium.App
import com.example.nuntium.R
import com.example.nuntium.adapters.HeadlinePageAdapter
import com.example.nuntium.adapters.PageAdapter
import com.example.nuntium.databinding.FragmentRandomBinding
import com.example.nuntium.response.Article
import com.example.nuntium.ui.manager.ContainsFragment
import com.example.nuntium.util.UtilList.removeNullable
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RandomFragment : ContainsFragment(R.layout.fragment_random), CoroutineScope {

    private val binding by viewBinding(FragmentRandomBinding::bind)
    private lateinit var verticalAdapter: PageAdapter
    private lateinit var horizontalAdapter: HeadlinePageAdapter

    @Inject
    lateinit var viewModel: RandomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.injectRandomFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNetworkState().observe(viewLifecycleOwner, {
            if (!it)
                binding.connectionChecker.visibility = View.VISIBLE
            else
                binding.connectionChecker.visibility = View.GONE
        })
        verticalAdapter = PageAdapter(listenerVertical)
        horizontalAdapter = HeadlinePageAdapter(listener)
        binding.recyclerViewVertical.adapter = verticalAdapter
        binding.recyclerViewHorizontal.adapter = horizontalAdapter

        viewModel.getHeadlineHorizontal().cachedIn(viewLifecycleOwner.lifecycle)
            .observe(viewLifecycleOwner, {
                horizontalAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            })

        viewModel.getHeadlineVertical().cachedIn(this).observe(viewLifecycleOwner, {
            verticalAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })
    }

    private val listener = object : HeadlinePageAdapter.OnItemClickListener {
        override fun onItemClick(article: Article) {
            removeNullable(article)
            val bundle = Bundle()
            bundle.putSerializable("article", article)
            findNavController().navigate(R.id.action_homeFragment_to_articleFragment, bundle)
        }

        override fun onItemClickBookmark(article: Article, position: Int) {
            launch(Dispatchers.Main) {
                removeNullable(article)
                val check = viewModel.getArticle(article)
                if (check?.title == article.title) {
                    viewModel.remove(article)
                } else {
                    viewModel.insert(article)
                }
                horizontalAdapter.notifyItemChanged(position)
            }
        }

        override fun itemCheck(article: Article, btnBookmark: ImageButton) {
            launch(Dispatchers.Main) {
                val check = viewModel.getArticle(article)
                if (check?.title == article.title) {
                    btnBookmark.setImageResource(R.drawable.ic_bookmark_on)
                } else {
                    btnBookmark.setImageResource(R.drawable.ic_bookmark_icon)
                }
            }
        }
    }

    private val listenerVertical = object : PageAdapter.OnItemClickListener {
        override fun onItemClick(article: Article) {
            removeNullable(article)
            val bundle = Bundle()
            bundle.putSerializable("article", article)
            findNavController().navigate(R.id.action_homeFragment_to_articleFragment, bundle)
        }
    }
}