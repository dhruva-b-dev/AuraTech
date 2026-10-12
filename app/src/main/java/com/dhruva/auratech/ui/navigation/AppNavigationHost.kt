package com.dhruva.auratech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.dhruva.auratech.ui.addresses.AddressAction
import com.dhruva.auratech.ui.addresses.AddressesScreen
import com.dhruva.auratech.ui.addresses.AddressesViewModel
import com.dhruva.auratech.ui.checkout.CheckoutAction
import com.dhruva.auratech.ui.checkout.CheckoutScreen
import com.dhruva.auratech.ui.checkout.CheckoutViewModel
import com.dhruva.auratech.ui.orders.OrderAction
import com.dhruva.auratech.ui.orders.OrdersScreen
import com.dhruva.auratech.ui.orders.OrdersViewModel
import com.dhruva.auratech.ui.auth.AuthAction
import com.dhruva.auratech.ui.auth.AuthScreen
import com.dhruva.auratech.ui.auth.AuthViewModel
import com.dhruva.auratech.ui.wishlist.WishlistAction
import com.dhruva.auratech.ui.wishlist.WishlistScreen
import com.dhruva.auratech.ui.wishlist.WishlistViewModel

object AppScreenRoutes {
    const val AUTH_SCREEN = "store/auth"
    const val HOME_SCREEN = "store/home"
    const val PRODUCT_DETAIL_SCREEN = "store/product/{productId}"
    const val CATEGORIES_SCREEN = "store/categories"
    const val CART_SCREEN = "store/cart"
    const val PROFILE_SCREEN = "store/profile"
    const val ADDRESSES_SCREEN = "store/addresses"
    const val CHECKOUT_SCREEN = "store/checkout"
    const val ORDERS_SCREEN = "store/orders"
    const val WISHLIST_SCREEN = "store/wishlist"

    fun productDetailScreen(productId: String): String = "store/product/$productId"
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = if (com.google.firebase.auth.FirebaseAuth.getInstance().currentUser == null) AppScreenRoutes.AUTH_SCREEN else AppScreenRoutes.HOME_SCREEN
) {
    val navigateToTab: (BottomNavTab) -> Unit = { tab ->
        val route = when (tab) {
            BottomNavTab.HOME -> AppScreenRoutes.HOME_SCREEN
            BottomNavTab.CATEGORIES -> AppScreenRoutes.CATEGORIES_SCREEN
            BottomNavTab.CART -> AppScreenRoutes.CART_SCREEN
            BottomNavTab.PROFILE -> AppScreenRoutes.PROFILE_SCREEN
        }
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        //Home Screen
        composable(route = AppScreenRoutes.HOME_SCREEN) {
            val homeViewModel: HomeViewModel = hiltViewModel()
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
                                    "deal_4"
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

                        HomeAction.OnViewAllCategoriesClick,
                        HomeAction.OnViewAllFlashDealsClick -> {
                            navController.navigate(AppScreenRoutes.CATEGORIES_SCREEN) {
                                popUpTo(AppScreenRoutes.HOME_SCREEN) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                        HomeAction.OnProfileClick -> {
                            navController.navigate(AppScreenRoutes.PROFILE_SCREEN) {
                                popUpTo(AppScreenRoutes.HOME_SCREEN) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                        HomeAction.OnScanBarcodeClick -> {
                            navController.navigate(AppScreenRoutes.productDetailScreen("deal_4"))
                        }

                        is HomeAction.OnTabSelected -> {
                            navigateToTab(action.tab)
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
                defaultValue = "deal_4"
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val viewModel: ProductDetailViewModel = hiltViewModel()

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ProductDetailScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is ProductDetailAction.OnBackClick -> {
                            navController.popBackStack()
                        }

                        is ProductDetailAction.OnCartClick -> {
                            navController.navigate(AppScreenRoutes.CART_SCREEN) {
                                popUpTo(AppScreenRoutes.HOME_SCREEN) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
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
            val categoriesViewModel: CategoriesViewModel = hiltViewModel()
            val uiState by categoriesViewModel.uiState.collectAsStateWithLifecycle()

            CategoriesScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is CategoriesAction.OnTabSelected -> {
                            navigateToTab(action.tab)
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
            val cartViewModel: CartViewModel = hiltViewModel()
            val uiState by cartViewModel.uiState.collectAsStateWithLifecycle()


            CartScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is CartAction.OnTabSelected -> {
                            navigateToTab(action.tab)
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

                        is CartAction.OnProceedToCheckout -> {
                            if (uiState.items.isNotEmpty()) {
                                navController.navigate(AppScreenRoutes.CHECKOUT_SCREEN)
                            } else {
                                cartViewModel.onAction(action)
                            }
                        }

                        else -> {
                            cartViewModel.onAction(action)
                        }
                    }
                })
        }

        //Checkout Screen
        composable(AppScreenRoutes.CHECKOUT_SCREEN) {
            val checkoutViewModel: CheckoutViewModel = hiltViewModel()
            val uiState by checkoutViewModel.uiState.collectAsStateWithLifecycle()

            CheckoutScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is CheckoutAction.OnBackClick -> {
                            navController.popBackStack()
                        }
                        else -> {
                            checkoutViewModel.onAction(action)
                        }
                    }
                },
                onNavigateHome = {
                    navController.navigate(AppScreenRoutes.HOME_SCREEN) {
                        popUpTo(AppScreenRoutes.HOME_SCREEN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        //Profile Screen
        composable(AppScreenRoutes.PROFILE_SCREEN) {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

            ProfileScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is ProfileAction.OnTabSelected -> {
                            navigateToTab(action.tab)
                        }

                        is ProfileAction.OnMenuItemClick -> {
                            when (action.itemId) {
                                "addresses" -> navController.navigate(AppScreenRoutes.ADDRESSES_SCREEN)
                                "orders" -> navController.navigate(AppScreenRoutes.ORDERS_SCREEN)
                                "wishlist" -> navController.navigate(AppScreenRoutes.WISHLIST_SCREEN)
                                else -> profileViewModel.onAction(action)
                            }
                        }

                        else -> {
                            profileViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        //Auth Screen
        composable(AppScreenRoutes.AUTH_SCREEN) {
            val authViewModel: AuthViewModel = hiltViewModel()
            val uiState by authViewModel.uiState.collectAsStateWithLifecycle()

            AuthScreen(
                uiState = uiState,
                onAction = { action ->
                    authViewModel.onAction(action) {
                        navController.navigate(AppScreenRoutes.HOME_SCREEN) {
                            popUpTo(AppScreenRoutes.AUTH_SCREEN) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }

        //Wishlist Screen
        composable(AppScreenRoutes.WISHLIST_SCREEN) {
            val wishlistViewModel: WishlistViewModel = hiltViewModel()
            val uiState by wishlistViewModel.uiState.collectAsStateWithLifecycle()

            WishlistScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is WishlistAction.OnBackClick -> navController.popBackStack()
                        is WishlistAction.OnProductClick -> navController.navigate(AppScreenRoutes.productDetailScreen(action.productId))
                        else -> wishlistViewModel.onAction(action)
                    }
                }
            )
        }

        //Orders Screen
        composable(AppScreenRoutes.ORDERS_SCREEN) {
            val ordersViewModel: OrdersViewModel = hiltViewModel()
            val uiState by ordersViewModel.uiState.collectAsStateWithLifecycle()

            OrdersScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is OrderAction.OnBackClick -> {
                            navController.popBackStack()
                        }
                        else -> {
                            ordersViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        //Addresses Screen
        composable(AppScreenRoutes.ADDRESSES_SCREEN) {
            val addressesViewModel: AddressesViewModel = hiltViewModel()
            val uiState by addressesViewModel.uiState.collectAsStateWithLifecycle()

            AddressesScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is AddressAction.OnBackClick -> {
                            navController.popBackStack()
                        }

                        else -> {
                            addressesViewModel.onAction(action)
                        }
                    }
                }
            )
        }
    }
}