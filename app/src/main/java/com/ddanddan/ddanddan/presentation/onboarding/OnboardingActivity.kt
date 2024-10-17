package com.ddanddan.ddanddan.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.ActivityOnboardingBinding
import com.ddanddan.ddanddan.presentation.onboarding.adapter.OnboardingViewPagerAdapter
import com.ddanddan.ddanddan.presentation.signin.SignInActivity
import com.ddanddan.ddanddan.util.PermissionUtils
import com.ddanddan.ddanddan.util.custom.dialog.CommonDialogConfig
import com.ddanddan.ddanddan.util.custom.dialog.CommonDialogFragment
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

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        startActivity(Intent(this@OnboardingActivity, SignInActivity::class.java))
        finish()
    }

    private fun checkLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                PermissionUtils.ACCESS_FINE_LOCATION, PermissionUtils.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun setUpListener() {
        binding.btnStart.setOnClickListener {
            initDialog()
        }
    }

    private fun initDialog() {
        CommonDialogFragment.newInstance(
            commonDialogConfig = CommonDialogConfig(
                title = getString(R.string.onboarding_tv_access_location_title),
                description = getString(R.string.onboarding_tv_access_location_subtitle),
                positiveButtonText = getString(R.string.onboarding_tv_access_location_allow),
                negativeButtonText = getString(R.string.onboarding_tv_access_location_decline)
            ),
            onPositiveButtonClicked = { checkLocationPermission() },
            onNegativeButtonClicked = { startActivity(Intent(this@OnboardingActivity, SignInActivity::class.java))
            }
        ).show(supportFragmentManager, "CommonDialogFragmentTag")
    }

    private fun initViewPagerItem() {
        with(viewpagerList) {
            add(
                CommonViewPagerEntity(
                    "오늘 소비한 칼로리로\n귀여운 펫을 키워보세요",
                    R.drawable.ic_dumbbell
                )
            )
            add(
                CommonViewPagerEntity(
                    "펫이 다 자라면\n또 다른 펫을 키울 수 있어요",
                    R.drawable.ic_ddanddan
                )
            )
            add(
                CommonViewPagerEntity(
                    "꾸준히 운동해\n소중한 펫을 지켜주세요!",
                    R.drawable.ic_dumbbell
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

    override fun finish() {
        super.finish()
    }

    companion object {}
}