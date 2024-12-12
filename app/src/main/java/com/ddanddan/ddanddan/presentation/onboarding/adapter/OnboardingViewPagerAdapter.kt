package com.ddanddan.ddanddan.presentation.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ddanddan.ddanddan.databinding.ItemOnboardingViewpagerBinding
import com.ddanddan.domain.entity.CommonViewPagerEntity

class OnboardingViewPagerAdapter: ListAdapter<CommonViewPagerEntity, OnboardingViewPagerAdapter.ItemViewHolder>(ItemListDiffCallback) {
    private lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(ItemOnboardingViewpagerBinding.inflate(layoutInflater, parent, false))
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
                ivViewpager.layoutParams = if (item.isFullWidth) {
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT, // width
                        ConstraintLayout.LayoutParams.MATCH_PARENT  // height
                    )
                } else {
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, // width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT  // height
                    ).apply {
                        // 추가적으로 Constraint 설정 (예: 중앙 배치)
                        startToStart = tvTitle.id
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                    }
                }

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