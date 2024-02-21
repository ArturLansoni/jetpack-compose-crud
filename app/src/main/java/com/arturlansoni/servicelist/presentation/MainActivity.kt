package com.arturlansoni.servicelist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arturlansoni.servicelist.core.infra.auth.AuthClient
import com.arturlansoni.servicelist.core.infra.database.DatabaseClient
import com.arturlansoni.servicelist.presentation.ui.login.LoginScreen
import com.arturlansoni.servicelist.presentation.ui.login.LoginViewModel
import com.arturlansoni.servicelist.presentation.ui.register.RegisterScreen
import com.arturlansoni.servicelist.presentation.navigation.Screens
import com.arturlansoni.servicelist.presentation.ui.form.FormScreen
import com.arturlansoni.servicelist.presentation.ui.form.FormViewModel
import com.arturlansoni.servicelist.presentation.ui.list.ListScreen
import com.arturlansoni.servicelist.presentation.ui.list.ListViewModel
import com.arturlansoni.servicelist.presentation.ui.register.RegisterViewModel
import com.arturlansoni.servicelist.ui.theme.ServiceListTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authClient: AuthClient

    @Inject
    lateinit var databaseClient: DatabaseClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ServiceListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {
                        composable(Screens.Login.route) {
                            val factory = HiltViewModelFactory(LocalContext.current, it)
                            val viewModel = viewModel<LoginViewModel>(factory = factory)

                            LoginScreen(
                                navigator = navController::navigate,
                                viewModel = viewModel,
                            )
                        }
                        composable(Screens.Register.route) {
                            val factory = HiltViewModelFactory(LocalContext.current, it)
                            val viewModel = viewModel<RegisterViewModel>(factory = factory)

                            RegisterScreen(
                                navigator = navController::navigate,
                                goBack = navController::popBackStack,
                                viewModel = viewModel,
                            )
                        }
                        composable(Screens.List.route) {
                            val factory = HiltViewModelFactory(LocalContext.current, it)
                            val viewModel = viewModel<ListViewModel>(factory = factory)
                            ListScreen(
                                navigator = navController::navigate,
                                onLogout = navController::popBackStack,
                                viewModel = viewModel,
                            )
                        }
                        composable(
                            "${Screens.Form.route}?id={id}",
                            arguments = listOf(navArgument("id") {
                                defaultValue = ""
                            })
                        ) { backStackEntry ->
                            val factory = HiltViewModelFactory(LocalContext.current, backStackEntry)
                            val viewModel = viewModel<FormViewModel>(factory = factory)
                            FormScreen(
                                id =  backStackEntry.arguments?.getString("id") ?: "",
                                goBack = navController::popBackStack,
                                viewModel = viewModel,
                            )
                        }
                    }
                }
            }
        }
    }
}