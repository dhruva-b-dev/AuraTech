package com.dhruva.auratech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dhruva.auratech.model.BottomNavTab
import com.dhruva.auratech.ui.cart.CartScreen
import com.dhruva.auratech.ui.categories.CategoriesScreen
import com.dhruva.auratech.ui.home.HomeAction
import com.dhruva.auratech.ui.home.HomeScreen
import com.dhruva.auratech.ui.home.HomeViewModel
import com.dhruva.auratech.ui.productdetails.ProductDetailsScreen
import com.dhruva.auratech.ui.profile.ProfileScreen

object AppScreenRoutes {
    const val HOME_SCREEN = "store/home"
    const val PRODUCT_DETAIL_SCREEN = "store/product/{productId}"
    const val CATEGORIES_SCREEN = "store/categories"
    const val CART_SCREEN = "store/cart"
    const val PROFILE_SCREEN = "store/profile"

    fun productDetailScreen(productId: String): String = "store/product/$productId"
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppScreenRoutes.HOME_SCREEN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        //Home Screen
        composable(route = AppScreenRoutes.HOME_SCREEN) {
            val homeViewModel: HomeViewModel = viewModel()
            val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is HomeAction.OnProductClick -> {
                            navController.navigate(AppScreenRoutes.productDetailScreen(action.productId))
                        }

                        HomeAction.OnBannerShopNowClick -> {
                            navController.navigate(
                                AppScreenRoutes.productDetailScreen(
                                    "deal_3"
                                )
                            )
                        }

                        HomeAction.OnBrandBagClick -> {
                            navController.navigate(AppScreenRoutes.CART_SCREEN) {
                                popUpTo(AppScreenRoutes.HOME_SCREEN) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                        HomeAction.OnViewAllCategoriesClick -> {
                            navController.navigate(AppScreenRoutes.CATEGORIES_SCREEN) {
                                popUpTo(AppScreenRoutes.HOME_SCREEN) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                        is HomeAction.OnTabSelected -> {
                            when (action.tab) {
                                BottomNavTab.CATEGORIES -> {
                                    navController.navigate(AppScreenRoutes.CATEGORIES_SCREEN) {
                                        popUpTo(AppScreenRoutes.HOME_SCREEN) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }

                                BottomNavTab.CART -> {
                                    navController.navigate(AppScreenRoutes.CART_SCREEN) {
                                        popUpTo(AppScreenRoutes.HOME_SCREEN) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }

                                BottomNavTab.PROFILE -> {
                                    navController.navigate(AppScreenRoutes.PROFILE_SCREEN) {
                                        popUpTo(AppScreenRoutes.HOME_SCREEN) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }

                                else -> {
                                    homeViewModel.onAction(action)
                                }
                            }
                        }

                        else -> {
                            homeViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        //Product Detail Screen
        composable(AppScreenRoutes.PRODUCT_DETAIL_SCREEN) {
            ProductDetailsScreen()
        }

        //Categories Screen
        composable(AppScreenRoutes.CATEGORIES_SCREEN) {
            CategoriesScreen()
        }

        //Cart Screen
        composable(AppScreenRoutes.CART_SCREEN) {
            CartScreen()
        }

        //Profile Screen
        composable(AppScreenRoutes.PROFILE_SCREEN) {
            ProfileScreen()
        }
    }
}