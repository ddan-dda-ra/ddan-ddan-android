package com.ddanddan.ddanddan.presentation.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ddanddan.ddanddan.databinding.ItemOnboardingViewpagerBinding
import com.ddanddan.domain.entity.CommonViewPagerEntity

class OnboardingViewPagerAdapter: ListAdapter<CommonViewPagerEntity, OnboardingViewPagerAdapter.ItemViewHolder>(ItemListDiffCallback) {
    private lateinit var binding: ItemOnboardingViewpagerBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        binding = ItemOnboardingViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewHolder(private val binding: ItemOnboardingViewpagerBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommonViewPagerEntity) {
            with(binding) {
                tvTitle.text = item.title
                item.image?.let { ivViewpager.setImageResource(it) }
            }
        }
    }

    object ItemListDiffCallback : DiffUtil.ItemCallback<CommonViewPagerEntity>() {
        override fun areItemsTheSame(oldItem: CommonViewPagerEntity, newItem: CommonViewPagerEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CommonViewPagerEntity,
            newItem: CommonViewPagerEntity
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }
}