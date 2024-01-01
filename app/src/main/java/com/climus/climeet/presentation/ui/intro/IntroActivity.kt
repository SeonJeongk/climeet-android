package com.climus.climeet.presentation.ui.intro

import android.os.Bundle
import androidx.activity.viewModels
import com.climus.climeet.databinding.ActivityIntroBinding
import com.climus.climeet.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity: BaseActivity<ActivityIntroBinding>(ActivityIntroBinding::inflate) {

    private val viewModel: IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }

}