package callofproject.dev.shoppinglistapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import callofproject.dev.shoppinglistapp.domain.preferences.IPreferences
import callofproject.dev.shoppinglistapp.presentation.influx_money.InfluxMoneyScreen
import callofproject.dev.shoppinglistapp.presentation.mainpage.MainPageScreen
import callofproject.dev.shoppinglistapp.presentation.shopping_list.ShoppingListScreen
import callofproject.dev.shoppinglistapp.route.Route
import callofproject.dev.shoppinglistapp.ui.theme.ShoppingListAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var prefs: IPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isSelectedMoney = prefs.getInfluxMoney()
        setContent {
            ShoppingListAppTheme {
                val navController = rememberNavController()
                val scaffoldState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = scaffoldState) }) {
                    NavHost(
                        navController = navController,
                        startDestination = if (isSelectedMoney == null) Route.INFLUX_MONEY else Route.MAIN_PAGE
                    ) {

                        composable(Route.INFLUX_MONEY) {
                            InfluxMoneyScreen(onNextClick = { navController.navigate(Route.MAIN_PAGE) })
                        }

                        composable(Route.MAIN_PAGE) {
                            MainPageScreen(scaffoldState = scaffoldState, onNavigateToItems = {
                                navController.navigate("${Route.SHOPPING_LIST_ITEM}/$it")
                            })
                        }

                        composable("${Route.SHOPPING_LIST_ITEM}/{listId}", arguments = listOf(
                            navArgument("listId") {
                                type = NavType.LongType
                            }
                        )) {
                            val id = it.arguments?.getLong("listId")!!
                            ShoppingListScreen(scaffoldState = scaffoldState, shoppingListId = id)
                        }
                    }
                }
            }
        }
    }
}

