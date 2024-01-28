package com.climus.climeet.presentation.ui.main.home.bestclimer.viewpager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.climus.climeet.R
import com.climus.climeet.databinding.FragmentCompleteClimbingBinding
import com.climus.climeet.databinding.FragmentCompleteDetailBinding
import com.climus.climeet.presentation.ui.main.home.viewpager.ranking.CompleteClimbingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompleteDetailFragment : Fragment() {

    private lateinit var binding : FragmentCompleteDetailBinding
    private val viewModel: CompleteDetailViewModel by viewModels()

    private lateinit var profileImg1 : ImageView
    private lateinit var profileImg2 : ImageView
    private lateinit var profileImg3 : ImageView
    private lateinit var profileImg4 : ImageView
    private lateinit var profileImg5 : ImageView
    private lateinit var profileImg6 : ImageView
    private lateinit var profileImg7 : ImageView
    private lateinit var profileImg8 : ImageView
    private lateinit var profileImg9 : ImageView
    lateinit var profileImgList: List<ImageView>

    private lateinit var nickname1 : TextView
    private lateinit var nickname2 : TextView
    private lateinit var nickname3 : TextView
    private lateinit var nickname4 : TextView
    private lateinit var nickname5 : TextView
    private lateinit var nickname6 : TextView
    private lateinit var nickname7 : TextView
    private lateinit var nickname8 : TextView
    private lateinit var nickname9 : TextView
    lateinit var nicknameList: List<TextView>

    private lateinit var problems1 : TextView
    private lateinit var problems2 : TextView
    private lateinit var problems3 : TextView
    private lateinit var problems4 : TextView
    private lateinit var problems5 : TextView
    private lateinit var problems6 : TextView
    private lateinit var problems7 : TextView
    private lateinit var problems8 : TextView
    private lateinit var problems9 : TextView
    lateinit var problemsList: List<TextView>

    private lateinit var rank1 : TextView
    private lateinit var rank2 : TextView
    private lateinit var rank3 : TextView
    lateinit var rankList: List<TextView>

    fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_complete_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // api
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.getClimberRankingOrderClearCount()
        initStateObserve()

    }

    private fun initView() {
        profileImg1 = binding.ivCompleteRanking1
        profileImg2 = binding.ivCompleteRanking2
        profileImg3 = binding.ivCompleteRanking3
        profileImg4 = binding.ivCompleteRanking4
        profileImg5 = binding.ivCompleteRanking5
        profileImg6 = binding.ivCompleteRanking6
        profileImg7 = binding.ivCompleteRanking7
        profileImg8 = binding.ivCompleteRanking8
        profileImg9 = binding.ivCompleteRanking9
        profileImgList = listOf(
            profileImg1, profileImg2, profileImg3, profileImg4, profileImg5, profileImg6, profileImg7, profileImg8, profileImg9
        )

        nickname1 = binding.tvCompleteRanking1Nickname
        nickname2 = binding.tvCompleteRanking2Nickname
        nickname3 = binding.tvCompleteRanking3Nickname
        nickname4 = binding.tvCompleteRanking4Nickname
        nickname5 = binding.tvCompleteRanking5Nickname
        nickname6 = binding.tvCompleteRanking6Nickname
        nickname7 = binding.tvCompleteRanking7Nickname
        nickname8 = binding.tvCompleteRanking8Nickname
        nickname9 = binding.tvCompleteRanking9Nickname
        nicknameList = listOf(
            nickname1, nickname2, nickname3, nickname4, nickname5, nickname6, nickname7, nickname8, nickname9
        )

        problems1 = binding.tvCompleteRanking1Problems
        problems2 = binding.tvCompleteRanking2Problems
        problems3 = binding.tvCompleteRanking3Problems
        problems4 = binding.tvCompleteRanking4Problems
        problems5 = binding.tvCompleteRanking5Problems
        problems6 = binding.tvCompleteRanking6Problems
        problems7 = binding.tvCompleteRanking7Problems
        problems8 = binding.tvCompleteRanking8Problems
        problems9 = binding.tvCompleteRanking9Problems
        problemsList = listOf(
            problems1, problems2, problems3, problems4, problems5, problems6, problems7, problems8, problems9
        )

        rank1 = binding.tvCompleteRankingNumber1
        rank2 = binding.tvCompleteRankingNumber2
        rank3 = binding.tvCompleteRankingNumber3
        rankList = listOf(rank1, rank2, rank3)
    }

    private fun initStateObserve() {
        repeatOnStarted {
            viewModel?.let { vm ->
                vm.uiState.collect { uiState ->
                    uiState.rankingList?.let { rankingList ->
                        Log.d("CompleteDetail", rankingList.toString())
                        rankingList.take(3).forEachIndexed { i, bestClearClimberResponse ->
                            rankList[i].text = bestClearClimberResponse.ranking.toString()
                            nicknameList[i].text = bestClearClimberResponse.profileName
                            problemsList[i].text = bestClearClimberResponse.thisWeekClearCount.toString()
                        }
                    }
                }
            }
        }
    }

}