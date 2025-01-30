package com.example.spendingtrackerapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.spendingtrackerapp.home.presentation.HomeScreen
import com.example.spendingtrackerapp.inser_spending.presentation.InsertSpendingScreen
import com.example.spendingtrackerapp.my_balance.presentation.MyBalanceScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    navHostController: NavHostController= rememberNavController()
) {
    NavHost(navController = navHostController, startDestination = Screen.Home,
        enterTransition = {slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Right
        )},
        exitTransition = { slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Left
        )}
    ) {
        composable<Screen.Home> {
            HomeScreen(
                onBalanceClick = {
                    navHostController.navigate(Screen.MyBalance)
                },
                onUpdateSpending = { id ->
                    navHostController.navigate(Screen.InsertSpending(spendingId = id))
                },
                onAddSpending = {
                    navHostController.navigate(Screen.InsertSpending(spendingId = null))
                },
            )
        }

        composable<Screen.MyBalance> {
            MyBalanceScreen(
                onBackHome = { navHostController.popBackStack() },
                onSave = {
                    navHostController.popBackStack()
                },
            )
        }

        composable<Screen.InsertSpending> {navBackStackEntry ->  
            val args = navBackStackEntry.toRoute<Screen.InsertSpending>()
            InsertSpendingScreen(
                onSaveSpending = {
                    navHostController.popBackStack()
                },
                id = args.spendingId,
            )
        }
    }

}