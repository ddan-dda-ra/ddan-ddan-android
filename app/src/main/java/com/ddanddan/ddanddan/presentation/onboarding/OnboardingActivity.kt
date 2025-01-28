package com.ddanddan.ddanddan.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.ActivityOnboardingBinding
import com.ddanddan.ddanddan.presentation.MainActivity
import com.ddanddan.ddanddan.presentation.onboarding.adapter.OnboardingViewPagerAdapter
import com.ddanddan.domain.entity.CommonViewPagerEntity
import com.ddanddan.ui.base.BindingActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity
    : BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {

    private val onBoardingViewPagerAdapter = OnboardingViewPagerAdapter()

    private val viewpagerList = ArrayList<CommonViewPagerEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPagerItem()
        initViewPager()
        setUpListener()
    }

    private fun setUpListener() {
        binding.btnStart.setOnClickListener {
            startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
        }
    }

    private fun initViewPagerItem() {
        with(viewpagerList) {
            add(
                CommonViewPagerEntity(
                    "오늘 소비한 칼로리로\n귀여운 펫을 키워보세요",
                    R.drawable.img_onboarding_1,
                    true
                )
            )
            add(
                CommonViewPagerEntity(
                    "펫이 다 자라면\n또 다른 펫을 키울 수 있어요",
                    R.drawable.img_onboarding_2
                )
            )
            add(
                CommonViewPagerEntity(
                    "꾸준히 운동해\n소중한 펫을 지켜주세요!",
                    R.drawable.img_onboarding_3
                )
            )
        }
    }

    private fun initViewPager() {
        onBoardingViewPagerAdapter.submitList(viewpagerList)
        with(binding) {
            vp.adapter = onBoardingViewPagerAdapter
            vp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            tl.clearOnTabSelectedListeners()
        }
        TabLayoutMediator(binding.tl, binding.vp) { tab, _ ->
            tab.view.isClickable = false
        }.attach()
    }

    companion object {}
}