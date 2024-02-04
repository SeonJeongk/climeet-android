package com.climus.climeet.presentation.ui.main.home.viewpager.ranking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.climus.climeet.data.model.BaseState
import com.climus.climeet.data.model.response.BestClearClimberSimpleResponse
import com.climus.climeet.data.model.response.BestLevelCimberSimpleResponse
import com.climus.climeet.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LevelUiState(
    val rankingList: List<BestLevelCimberSimpleResponse> = emptyList()
)

@HiltViewModel
class LevelViewModel @Inject constructor(private val repository: MainRepository): ViewModel() {
    private val _uiState = MutableStateFlow(LevelUiState())
    val uiState: StateFlow<LevelUiState> = _uiState.asStateFlow()

    fun getClimberRankingOrderLevel() {
        viewModelScope.launch {
            repository.findClimberRankingOrderLevel("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyNSttYW5hZ2VyIiwiaWF0IjoxNzA2NzQzMjczLCJleHAiOjE3MDcxMDMyNzN9.6IKq29hpSLSPw06TVHoN-gq3EP24MjtYlDwirrrYr3U").let {
                when(it) {
                    is BaseState.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                rankingList = it.body
                            )
                        }
                    }
                    is BaseState.Error -> {
                        it.msg // 서버 에러 메시지
                        Log.d("API", it.msg)
                    }
                }
            }
        }
    }
}