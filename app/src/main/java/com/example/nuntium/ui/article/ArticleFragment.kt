package com.example.nuntium.ui.article

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.nuntium.App
import com.example.nuntium.R
import com.example.nuntium.databinding.FragmentArticleBinding
import com.example.nuntium.response.Article
import com.example.nuntium.ui.manager.ContainsFragment
import com.example.nuntium.util.MySharedPreference
import com.example.nuntium.util.ShareArticle.formatterArticle
import com.example.nuntium.util.ShareArticle.shareSubject
import com.example.nuntium.util.ThemeHelper
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleFragment : ContainsFragment(R.layout.fragment_article) {

    private val binding by viewBinding(FragmentArticleBinding::bind)
    private var article: Article? = null
    private var isDark = false
    private var isHave = false
    private var isStart = true

    @Inject
    lateinit var viewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.injectArticleFragment(this)
        article = arguments?.getSerializable("article") as Article
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //check theme
        val theme = MySharedPreference(requireContext()).getThemeChecker()
        isDark = theme == ThemeHelper.darkMode

        launch {
            viewModel.getArticle(article!!).observe(viewLifecycleOwner, {
                isHave = it
                if (it) {
                    binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_on)
                } else {
                    binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_icon)
                }
            })
        }



        if (isDark) {
            binding.tvTitle.setTextColor(Color.parseColor("#FFFFFF"))
            binding.tvDescription.setTextColor(Color.parseColor("#FFFFFF"))
        }

        Glide.with(binding.root)
            .load(article?.urlToImage)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.error_item_img)
            .error(R.drawable.error_item_img)
            .into(binding.iv)
        binding.tvTitle.text = article?.title
        binding.tvDescription.text = article?.description
        binding.tvTypeNews.text = article?.source?.name

        binding.btnBookmark.setOnClickListener {
            if (isHave) {
                isHave = false
                viewModel.remove(article!!)
                if (isDark) {
                    binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_icon)
                } else {
                    if (isStart) {
                        binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_icon)
                    } else {
                        binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_icon_end)
                    }
                }
            } else {
                isHave = true
                viewModel.insert(article!!)
                if (isDark) {
                    binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_on)
                } else {
                    if (isStart) {
                        binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_on)
                    } else {
                        binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_dark_on)
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnShare.setOnClickListener {
            shareSubject(requireContext(), Bundle(), formatterArticle(article!!))
        }
        binding.btnWeb.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", article?.url)
            findNavController().navigate(R.id.action_articleFragment_to_webFragment, bundle)
        }
        motionTransitionListener()
    }

    private fun motionTransitionListener() {
        binding.motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) = Unit

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) = Unit

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                when (currentId) {
                    R.id.start -> {
                        isStart = true
                        getWhiteIcons()
                    }
                    R.id.end -> {
                        isStart = false
                        if (isDark) {
                            getWhiteIcons()
                            binding.tvDescription.setTextColor(Color.parseColor("#FFFFFF"))
                        } else {
                            getDarkIcons()
                        }
                    }
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) = Unit
        })
    }

    override fun onPause() {
        super.onPause()
        article = arguments?.getSerializable("article") as Article
    }

    override fun onResume() {
        super.onResume()
        article = arguments?.getSerializable("article") as Article
    }

    private fun getWhiteIcons() {
        binding.tvTitle.setTextColor(Color.parseColor("#FFFFFF"))
        binding.btnBack.setImageResource(R.drawable.ic_left_icon)
        if (isHave) {
            if (isDark) {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_on)
            } else {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_on)
            }
        } else {
            binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_icon)
        }
        binding.btnShare.setImageResource(R.drawable.ic_share_icon)
        binding.btnWeb.setImageResource(R.drawable.ic_open_in_browser_light)
    }

    private fun getDarkIcons() {
        binding.tvTitle.setTextColor(Color.parseColor("#333647"))
        binding.btnBack.setImageResource(R.drawable.ic_left_icon_end)
        if (isHave) {
            if (isDark) {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_dark_on)
            } else {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_dark_on)
            }
        } else {
            binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_icon_end)
        }
        binding.btnShare.setImageResource(R.drawable.ic_share_icon_end)
        binding.btnWeb.setImageResource(R.drawable.ic_open_in_browser_dark)
    }
}