package com.dhruva.auratech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dhruva.auratech.model.BottomNavTab
import com.dhruva.auratech.ui.cart.CartAction
import com.dhruva.auratech.ui.cart.CartScreen
import com.dhruva.auratech.ui.cart.CartViewModel
import com.dhruva.auratech.ui.categories.CategoriesAction
import com.dhruva.auratech.ui.categories.CategoriesScreen
import com.dhruva.auratech.ui.categories.CategoriesViewModel
import com.dhruva.auratech.ui.home.HomeAction
import com.dhruva.auratech.ui.home.HomeScreen
import com.dhruva.auratech.ui.home.HomeViewModel
import com.dhruva.auratech.ui.productdetails.ProductDetailAction
import com.dhruva.auratech.ui.productdetails.ProductDetailScreen
import com.dhruva.auratech.ui.productdetails.ProductDetailViewModel
import com.dhruva.auratech.ui.profile.ProfileAction
import com.dhruva.auratech.ui.profile.ProfileScreen
import com.dhruva.auratech.ui.profile.ProfileViewModel

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
        composable(
            AppScreenRoutes.PRODUCT_DETAIL_SCREEN,
            arguments = listOf(navArgument("productId") {
                defaultValue = "deal_3"
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: "deal_3"
            val viewModel: ProductDetailViewModel = viewModel(
                key = productId,
                factory = object : ViewModelProvider.Factory {
                    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                        return ProductDetailViewModel(productId) as T
                    }
                }
            )

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ProductDetailScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is ProductDetailAction.OnBackClick -> {
                            navController.popBackStack()
                        }

                        else -> {
                            viewModel.onAction(action)
                        }
                    }
                }
            )
        }


        //Categories Screen
        composable(AppScreenRoutes.CATEGORIES_SCREEN) {
            val categoriesViewModel: CategoriesViewModel = viewModel()
            val uiState by categoriesViewModel.uiState.collectAsStateWithLifecycle()

            CategoriesScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is CategoriesAction.OnTabSelected -> {
                            when (action.tab) {
                                BottomNavTab.HOME -> {
                                    navController.navigate(AppScreenRoutes.HOME_SCREEN) {
                                        popUpTo(AppScreenRoutes.HOME_SCREEN) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }

                                BottomNavTab.PROFILE -> {
                                    navController.navigate(AppScreenRoutes.PROFILE_SCREEN) {
                                        popUpTo(AppScreenRoutes.PROFILE_SCREEN) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }

                                BottomNavTab.CART -> {
                                    navController.navigate(AppScreenRoutes.CART_SCREEN) {
                                        popUpTo(AppScreenRoutes.CART_SCREEN) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }

                                else -> {
                                    categoriesViewModel.onAction(action)
                                }
                            }
                        }

                        is CategoriesAction.OnProductClick -> {
                            navController.navigate(AppScreenRoutes.productDetailScreen(action.productId))
                        }

                        else -> {
                            categoriesViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        //Cart Screen
        composable(AppScreenRoutes.CART_SCREEN) {
            val cartViewModel: CartViewModel = viewModel()
            val uiState by cartViewModel.uiState.collectAsStateWithLifecycle()


            CartScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is CartAction.OnTabSelected -> {
                            when (action.tab) {
                                BottomNavTab.HOME -> {
                                    navController.navigate(AppScreenRoutes.HOME_SCREEN) {
                                        popUpTo(AppScreenRoutes.HOME_SCREEN) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }

                                BottomNavTab.PROFILE -> {
                                    navController.navigate(AppScreenRoutes.PROFILE_SCREEN) {
                                        popUpTo(AppScreenRoutes.PROFILE_SCREEN) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }

                                BottomNavTab.CATEGORIES -> {
                                    navController.navigate(AppScreenRoutes.CATEGORIES_SCREEN) {
                                        popUpTo(AppScreenRoutes.CATEGORIES_SCREEN) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }

                                else -> {
                                    cartViewModel.onAction(action)
                                }
                            }
                        }

                        is CartAction.OnProductClick -> {
                            navController.navigate(AppScreenRoutes.productDetailScreen(action.productId))
                        }

                        is CartAction.OnStartShoppingClick -> {
                            navController.navigate(AppScreenRoutes.HOME_SCREEN) {
                                popUpTo(AppScreenRoutes.HOME_SCREEN) { inclusive = true }
                                launchSingleTop = true
                            }
                        }

                        else -> {
                            cartViewModel.onAction(action)
                        }
                    }
                })
        }

        //Profile Screen
        composable(AppScreenRoutes.PROFILE_SCREEN) {
            val profileViewModel: ProfileViewModel = viewModel()
            val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

            ProfileScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is ProfileAction.OnTabSelected -> {
                            when (action.tab) {
                                BottomNavTab.HOME -> {
                                    navController.navigate(AppScreenRoutes.HOME_SCREEN) {
                                        popUpTo(AppScreenRoutes.HOME_SCREEN) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }

                                BottomNavTab.CATEGORIES -> {
                                    navController.navigate(AppScreenRoutes.CATEGORIES_SCREEN)
                                    {
                                        popUpTo(AppScreenRoutes.CATEGORIES_SCREEN) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }

                                BottomNavTab.CART -> {
                                    navController.navigate(AppScreenRoutes.CART_SCREEN) {
                                        popUpTo(AppScreenRoutes.CART_SCREEN) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }

                                else -> {
                                    profileViewModel.onAction(action)
                                }
                            }
                        }

                        else -> {
                            profileViewModel.onAction(action)
                        }
                    }
                }
            )
        }
    }
}