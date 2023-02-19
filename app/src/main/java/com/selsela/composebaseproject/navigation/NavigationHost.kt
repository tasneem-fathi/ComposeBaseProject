package com.selsela.composebaseproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.selsela.composebaseproject.ui.screens.auth.view.LoginScreen
import com.selsela.composebaseproject.ui.screens.auth.view.VerifyScreen
import com.selsela.composebaseproject.ui.screens.categories.list.CategoriesScreen
import com.selsela.composebaseproject.ui.screens.home.HomeScreen
import com.selsela.composebaseproject.ui.screens.splash.SplashScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screens.Splash.name,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    },
) {
    NavHost(navController = navController, startDestination = startDestination, modifier) {
        composable(Screens.Splash.name) {
            SplashScreen(
                onFinish = navActions::navigateToHome
            )
        }
        composable(Screens.Home.name) {
            HomeScreen(
                onDataClick = navActions::navigateToCategories,
                onAuthClick = navActions::navigateToLogin
            )
        }
        composable(Screens.Login.name) {
            LoginScreen(goToHome = navActions::navigateToHome,
            goToVerify = navActions::navigateToVerify)
        }
        composable(Screens.Categories.name) {
            CategoriesScreen()
        }
        composable(Screens.VerifyCode.name) {
            VerifyScreen(
                goToHome = navActions::navigateToHome
            )
        }
    }
}