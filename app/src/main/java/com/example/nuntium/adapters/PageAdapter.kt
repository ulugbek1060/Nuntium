package com.example.nuntium.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.nuntium.R
import com.example.nuntium.response.Article
import com.example.nuntium.databinding.ItemArticleBinding
import com.facebook.shimmer.ShimmerFrameLayout

class PageAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Article, PageAdapter.Vh>(MyDiffUtil) {

    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }

    inner class Vh(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun onBind(article: Article) {
            binding.apply {
                shimmerShow(ivShimmer, tvShimmerPublishedTime, tvShimmerTitle)
                Glide.with(itemView)
                    .load(article.urlToImage)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            shimmerHide(ivShimmer, tvShimmerPublishedTime, tvShimmerTitle)
                            ivArticle.setImageResource(R.drawable.error_item_img)
                            return true
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            shimmerHide(ivShimmer, tvShimmerPublishedTime, tvShimmerTitle)
                            ivArticle.setImageDrawable(resource)
                            return true
                        }
                    })
                    .into(ivArticle)
                tvTitle.text = article.title
                tvPublishedTime.text = article.publishedAt
            }
            itemView.setOnClickListener {
                listener.onItemClick(article)
            }

        }
    }

    companion object MyDiffUtil : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun shimmerShow(shm1: ShimmerFrameLayout, shm2: ShimmerFrameLayout, shm3: ShimmerFrameLayout) {
        shm1.startShimmer()
        shm2.startShimmer()
        shm3.startShimmer()
    }

    fun shimmerHide(shm1: ShimmerFrameLayout, shm2: ShimmerFrameLayout, shm3: ShimmerFrameLayout) {
        shm1.hideShimmer()
        shm2.hideShimmer()
        shm3.hideShimmer()
    }
}