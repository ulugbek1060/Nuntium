package com.example.nuntium.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nuntium.R
import com.example.nuntium.databinding.ItemCategoryBinding
import com.example.nuntium.db.entity.Category
import com.example.nuntium.util.ThemeHelper

class CategoryAdapter(val listener: OnClickListener, private val themeChecker: String) :
    ListAdapter<Category, CategoryAdapter.Vh>(DiffCallback) {

    interface OnClickListener {
        fun onItemClickListener(category: Category, position: Int)
    }

    inner class Vh(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(category: Category, position: Int) {
            itemView.setOnClickListener {
                listener.onItemClickListener(category, position)
            }
            binding.tvItemTitle.text = category.type
            checkItem(category.isHave, binding.tvItemTitle)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun checkItem(have: Boolean, tv: TextView) {
        if (have) {
            tv.setBackgroundResource(R.drawable.item_tv_back_on)
            tv.setTextColor(Color.parseColor("#FFFFFF"))
        } else {
            tv.setBackgroundResource(R.drawable.item_tv_back_normal)
            if (themeChecker == ThemeHelper.darkMode) {
                tv.setTextColor(Color.parseColor("#ACAFC3"))
            } else {
                tv.setTextColor(Color.parseColor("#333647"))
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.type == newItem.type
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position), position)
    }
}