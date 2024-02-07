package com.climus.climeet.presentation.ui

import androidx.navigation.NavController
import com.climus.climeet.MainNavDirections

fun NavController.toSelectDateBottomSheetFragment(){
    val action = MainNavDirections.globalActionToSelectDateBottomSheetFragment()
    navigate(action)
}

fun NavController.toShortsFragment(){
    val action = MainNavDirections.globalActionToShortsFragment()
    navigate(action)
}
